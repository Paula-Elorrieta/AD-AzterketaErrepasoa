package firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class main {

	static Firestore db = null;

	public static void main(String[] args) throws IOException {

		db = Conexion.getFirestoreInstance();

		ArrayList<Usuario> usuarios = leerUsuarios();
		for (Usuario usuario : usuarios) {
			System.out.println(usuario.getNombreString() + " " + usuario.getEdad());
			if (usuario.getActividades() != null) {
				for (Actividad actividad : usuario.getActividades()) {
					System.out.println(actividad.getNombre());
				}
			}
		}

		ArrayList<Actividad> actividades = leerTodasLasActividades();
		for (Actividad actividad : actividades) {
			System.out.println(actividad.getNombre());
		}

		agregarUsuario("Paula", 20);
		System.out.println("Usuario añadido");

		actualizarUsuario("Paula", 21, "Paula2");
		System.out.println("Usuario actualizado");

		eliminarUsuario("Paula");
		System.out.println("Usuario eliminado");

		añadirActividad("Maria", "Correr");
		System.out.println("Actividad añadida");

	}

	public static ArrayList<Usuario> leerUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<>();

		try {
			// Obtener todos los documentos de la colección 'usuarios'
			Iterable<QueryDocumentSnapshot> documents = db.collection("usuarios").get().get().getDocuments();

			// Iterar sobre los documentos y convertirlos en objetos Usuario
			for (QueryDocumentSnapshot document : documents) {
				String name = document.getString("nombre");
				int edad = document.getDouble("edad").intValue();
				ArrayList<Actividad> actividades = leerActividadesPorNombreUsuario(document.getString("nombre"));
				Usuario usuario = new Usuario(name, edad, actividades);
				usuarios.add(usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuarios;
	}

	public static ArrayList<Actividad> leerActividadesPorNombreUsuario(String nombreUsuario) {
		ArrayList<Actividad> actividades = new ArrayList<>();

		try {
			Iterable<QueryDocumentSnapshot> usuarioDocuments = db.collection("usuarios")
					.whereEqualTo("nombre", nombreUsuario).get().get().getDocuments();

			for (QueryDocumentSnapshot usuarioDocument : usuarioDocuments) {
				String usuarioId = usuarioDocument.getId();

				Iterable<QueryDocumentSnapshot> actividadDocuments = db.collection("usuarios").document(usuarioId)
						.collection("actividades").get().get().getDocuments();

				for (QueryDocumentSnapshot actividadDocument : actividadDocuments) {
					String nombreActividad = actividadDocument.getString("nombre");
					Actividad actividad = new Actividad(nombreActividad);
					actividades.add(actividad);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return actividades;
	}

	public static ArrayList<Actividad> leerTodasLasActividades() {
		ArrayList<Actividad> actividades = new ArrayList<>();

		try {
			// Obtener todos los documentos de la colección 'usuarios'
			Iterable<QueryDocumentSnapshot> usuarioDocuments = db.collection("usuarios").get().get().getDocuments();

			// Iterar sobre cada documento de usuario
			for (QueryDocumentSnapshot usuarioDocument : usuarioDocuments) {
				String usuarioId = usuarioDocument.getId(); // Obtener el ID del usuario

				// Obtener todos los documentos de la subcolección 'actividades' de este usuario
				Iterable<QueryDocumentSnapshot> actividadDocuments = db.collection("usuarios").document(usuarioId)
						.collection("actividades").get().get().getDocuments();

				// Iterar sobre los documentos de la subcolección 'actividades'
				for (QueryDocumentSnapshot actividadDocument : actividadDocuments) {
					String nombreActividad = actividadDocument.getString("nombre"); // Extraer el nombre de la actividad
					Actividad actividad = new Actividad(nombreActividad);
					actividades.add(actividad);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return actividades;
	}

	public static void agregarUsuario(String nombre, int edad) {
		try {
			// Crear un nuevo documento con un ID específico, si lo dejo vacío Firestore lo
			// generará automáticamente
			DocumentReference docRef = db.collection("usuarios").document();

			// Crear un mapa con los datos del usuario
			Map<String, Object> data = new HashMap<>();
			data.put("nombre", nombre);
			data.put("edad", edad);

			// Añadir el documento a la colección 'usuarios'
			docRef.set(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void actualizarUsuario(String nombre, int edadNueva, String nombreNuevo) {
		try {
			Iterable<QueryDocumentSnapshot> documents = db.collection("usuarios").whereEqualTo("nombre", nombre).get()
					.get().getDocuments();
			String usuarioId = "";
			for (QueryDocumentSnapshot document : documents) {
				usuarioId = document.getId();
			}

			Map<String, Object> data = new HashMap<>();
			data.put("nombre", nombreNuevo);
			data.put("edad", edadNueva);

			db.collection("usuarios").document(usuarioId).update(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void eliminarUsuario(String nombre) {
		try {
			Iterable<QueryDocumentSnapshot> documents = db.collection("usuarios").whereEqualTo("nombre", nombre).get()
					.get().getDocuments();
			String usuarioId = "";
			for (QueryDocumentSnapshot document : documents) {
				usuarioId = document.getId();
				// db.collection("usuarios").document(usuarioId).delete(); // EN CASO DE QUE
				// QUERAMOS BORRAR TODOS LOS USUARIOS Y NO SOLO UNO
			}
			db.collection("usuarios").document(usuarioId).delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void añadirActividad(String nombreUsuario, String nombreActividad) {
		try {
			Iterable<QueryDocumentSnapshot> usuarioDocuments = db.collection("usuarios")
					.whereEqualTo("nombre", nombreUsuario).get().get().getDocuments();

			for (QueryDocumentSnapshot usuarioDocument : usuarioDocuments) {
				String usuarioId = usuarioDocument.getId();
				System.out.println(usuarioId);
				
				Iterable<QueryDocumentSnapshot> actividadDocuments = db.collection("usuarios").document(usuarioId)
						.collection("actividades").get().get().getDocuments();

				for (QueryDocumentSnapshot actividadDocument : actividadDocuments) {
					String nombreActividadActual = actividadDocument.getString("nombre");
					if (nombreActividadActual.equals(nombreActividad)) {
						return;
					} else {
						DocumentReference docRef = db.collection("usuarios").document(usuarioId)
								.collection("actividades").document();
						Map<String, Object> data = new HashMap<>();
						data.put("nombre", nombreActividad);
						docRef.set(data);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
