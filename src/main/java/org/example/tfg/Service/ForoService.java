package org.example.tfg.Service;

import org.example.tfg.Dto.*;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ForoService {

    public ForoService() {
        initFirebase();
    }

    private void initFirebase() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                ClassPathResource resource = new ClassPathResource("eatfit.json");
                InputStream serviceAccount = resource.getInputStream();

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Aquí sigue todo tu código existente
    public List<Foro> getAllForo() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("foro").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Foro> foros = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Foro foro = doc.toObject(Foro.class);
            foros.add(foro);
        }

        return foros;
    }


    public Foro getForoById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("foro").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Foro f;
        if(document.exists()){
            f = document.toObject(Foro.class);
            return f;
        }
        return null;
    }

    public Foro addForo(Foro foro) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        foro.setId(dameUltimoId()); // solo si quieres guardar el ID generado de Firebase en el objeto

        ApiFuture<WriteResult> future = dbFirestore.collection("foro")
                .document(foro.getId())
                .set(foro);

        // Esperamos que se guarde correctamente
        future.get();

        return foro;
    }

    public Foro updateForo(Foro foro) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Asegúrate de que el ID no sea null ni vacío
        if (foro.getId() == null || foro.getId().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("usuarios")
                .document(foro.getId())
                .set(foro);

        WriteResult result = future.get();

        // Si se actualizó correctamente, devuelve el usuario
        if (result != null && result.getUpdateTime() != null) {
            return foro;
        }

        return null;
    }

    public boolean deleteForo(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            // Verificamos si el documento existe antes de intentar borrarlo
            DocumentReference docRef = dbFirestore.collection("foro").document(id);
            ApiFuture<DocumentSnapshot> futureDoc = docRef.get();
            DocumentSnapshot document = futureDoc.get();

            if (!document.exists()) {
                // No se puede borrar si no existe
                return false;
            }

            // Ahora sí, borrar
            ApiFuture<WriteResult> deleteFuture = docRef.delete();
            WriteResult result = deleteFuture.get();

            return result != null && result.getUpdateTime() != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String dameUltimoId() throws ExecutionException, InterruptedException {
        List<Foro> foro = getAllForo();
        Integer idADevolver=0;
        for (Foro f : foro) {
            try {
                Integer id = Integer.parseInt(f.getId());
                // Aquí sí es numérico
                if(idADevolver<id)
                    idADevolver=id;
            } catch (NumberFormatException e) {
                // No es un número → lo ignoramos y continuamos sin cortar
            }
        }
        return String.valueOf(idADevolver+1);
    }

}
