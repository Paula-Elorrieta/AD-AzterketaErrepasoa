package firebase;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class Conexion {
	
    private static Firestore firestoreInstance;

    private Conexion() {}

    public static Firestore getFirestoreInstance() {
        if (firestoreInstance == null) {
            try {
                FileInputStream serviceAccount = new FileInputStream("paula-examen.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }

                firestoreInstance = FirestoreClient.getFirestore();
            } catch (IOException e) {
                System.err.println("Error al inicializar Firestore: " + e.getMessage());
            }
        }
        return firestoreInstance;
    }
}
