package bitarra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
        String ruta = "gatos.dat";

        ArrayList<Gato> gatos = crearDatosIniciales();

        escribirArchivo(gatos, ruta);
        System.out.println("Datos iniciales escritos en el archivo.");

        ArrayList<Gato> gatosLeidos = leerArchivo(ruta);
        System.out.println("\nDatos leídos del archivo:");
        for (Gato gato : gatosLeidos) {
            System.out.println(gato);
        }
    }

	// Este método crea un ArrayList de gatos con datos iniciales si quisieramos agregar más gatos puedo meter un scanner y pedir los datos
    public static ArrayList<Gato> crearDatosIniciales() {
        ArrayList<Gato> gatos = new ArrayList<>();
        gatos.add(new Gato("Blanco", "Persa", false, 3));
        gatos.add(new Gato("Negro", "Siamés", true, 5));
        gatos.add(new Gato("Gris", "Azul Ruso", false, 2));
        return gatos;
    }

    // Esto coge el array y lo va escribiendo en el archivo
    public static void escribirArchivo(ArrayList<Gato> gatos, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(gatos);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Y este metodo lee el fichero y lo castea en un array de gatos. Dependiendo lo que queramos recupera podemos castearlo.
    public static ArrayList<Gato> leerArchivo(String ruta) {
        ArrayList<Gato> gatos = new ArrayList<>();
        File archivo = new File(ruta);

        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
                gatos = (ArrayList<Gato>) ois.readObject();      
                
             // Leer un único objeto Gato
             //gato = (Gato) ois.readObject();
             // Para leer sin mas se puede usar:
             // ois.readDouble();
                
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo no existe.");
        }

        return gatos;
    }
}