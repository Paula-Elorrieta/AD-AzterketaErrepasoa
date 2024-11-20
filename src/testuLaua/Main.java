package testuLaua;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
        String ruta = "perros.txt";

        ArrayList<Perro> perros = leerArchivo(ruta);

        System.out.println("Datos actuales en el archivo:");
        for (Perro perro : perros) {
            System.out.println(perro);
        }

        agregarPerro(perros, ruta);

        System.out.println("\nDatos actualizados en el archivo:");
        for (Perro perro : perros) {
            System.out.println(perro);
        }
    }

	// Funcion de lectura
    public static ArrayList<Perro> leerArchivo(String ruta) {
        ArrayList<Perro> perros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            String color = "", raza = "";
            boolean enfermo = false;
            int edad = 0;

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("Color:")) {
                    color = linea.split(": ")[1];
                } else if (linea.startsWith("Raza:")) {
                    raza = linea.split(": ")[1];
                } else if (linea.startsWith("Enfermo:")) {
                    enfermo = linea.split(": ")[1].equalsIgnoreCase("Sí");
                } else if (linea.startsWith("Edad:")) {
                    edad = Integer.parseInt(linea.split(": ")[1].split(" ")[0]);
                } else if (linea.startsWith("*****")) {
                    perros.add(new Perro(color, raza, enfermo, edad));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return perros;
    }

    // FUncion donde agrego un nuevo perro y escribo en el archivo
    public static void agregarPerro(ArrayList<Perro> perros, String ruta) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nIntroduce los datos del nuevo perro:");
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Raza: ");
        String raza = scanner.nextLine();
        System.out.print("¿Está enfermo? (Sí/No): ");
        boolean enfermo = scanner.nextLine().equalsIgnoreCase("Sí");
        System.out.print("Edad (en años): ");
        int edad = scanner.nextInt();

        Perro nuevoPerro = new Perro(color, raza, enfermo, edad);
        perros.add(nuevoPerro);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) { // true para añadir al final
            writer.write("Perro " + perros.size() + ":\n");
            writer.write(nuevoPerro.toString());
            writer.write("\n");
            System.out.println("El nuevo perro ha sido añadido al archivo.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
        
        scanner.close();
    }
}