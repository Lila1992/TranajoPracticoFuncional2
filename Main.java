import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Clase Producto (nombre, categoria, precio, stock)
class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    @Override
    public String toString() {
        return "Producto [Nombre: " + nombre + ", Categoría: " + categoria + 
               ", Precio: $" + precio + ", Stock: " + stock + "]";
    }
}

public class Main {
    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Notebook Dell", "Electrónica", 1200.0, 15));
        productos.add(new Producto("Mouse Logi", "Electrónica", 45.0, 50));
        productos.add(new Producto("Teclado Mecánico", "Electrónica", 130.0, 30));
        productos.add(new Producto("Silla Gamer", "Muebles", 350.0, 10));
        productos.add(new Producto("Escritorio L", "Muebles", 280.0, 8));
        productos.add(new Producto("Cafetera Expresso", "Electrodomésticos", 220.0, 12));
        productos.add(new Producto("Taza Cerámica", "Hogar", 15.0, 100));

        System.out.println("=== PROGRAMACIÓN FUNCIONAL - TP STREAMS 2 ===\n");

        // 1. Listar los productos con precio mayor a 100, ordenados por precio descendente
        List<Producto> precioMayor100 = productos.stream()
                .filter(p -> p.getPrecio() > 100.0)
                .sorted(Comparator.comparingDouble(Producto::getPrecio).reversed())
                .collect(Collectors.toList());

        System.out.println("1. Productos con precio > $100 (Orden descendente de precio):");
        precioMayor100.forEach(p -> System.out.println(" - " + p.getNombre() + ": $" + p.getPrecio()));

        // 2. Agrupar productos por categoría y calcular el stock total por categoría
        Map<String, Integer> stockPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.summingInt(Producto::getStock)
                ));

        System.out.println("\n2. Stock total agrupado por categoría:");
        stockPorCategoria.forEach((cat, stock) -> 
            System.out.println(" Categoría [" + cat + "] -> Stock Total: " + stock + " unidades"));

        // 3. Generar un String separando con ";" cada producto (nombre y precio) usando map + collect(joining)
        String resumenProductos = productos.stream()
                .map(p -> p.getNombre() + " ($" + p.getPrecio() + ")")
                .collect(Collectors.joining("; "));

        System.out.println("\n3. Resumen formateado (nombre y precio unidos por ';'):");
        System.out.println(" " + resumenProductos);

        // 4. Calcular el precio promedio general y por categoría
        double promedioGeneral = productos.stream()
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0.0);

        Map<String, Double> promedioPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.averagingDouble(Producto::getPrecio)
                ));

        System.out.println("\n4. Estadísticas de precio promedio:");
        System.out.printf(" Promedio General de Precios: $%.2f\n", promedioGeneral);
        System.out.println(" Promedio de precio por categoría:");
        promedioPorCategoria.forEach((cat, prom) -> 
            System.out.printf("   * %-16s: $%.2f\n", cat, prom));
    }
}