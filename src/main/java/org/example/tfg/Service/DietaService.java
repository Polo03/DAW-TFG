package org.example.tfg.Service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.Dieta;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DietaService {

    // Aquí sigue todo tu código existente
    public List<Dieta> getAllDietas() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("dietas").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Dieta> dietas = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Dieta dieta = doc.toObject(Dieta.class);
            dietas.add(dieta);
        }

        return dietas;
    }


    public Dieta getDietaById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("dietas").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Dieta d;
        if(document.exists()){
            d = document.toObject(Dieta.class);
            return d;
        }
        return null;
    }

    public Dieta addDieta(Dieta dieta) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> future = dbFirestore.collection("dietas")
                .document(dieta.getTipo()+"_"+dieta.getDia())
                .set(dieta);

        // Esperamos que se guarde correctamente
        future.get();

        return dieta;
    }

    public Dieta updateDieta(Dieta dieta) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Asegúrate de que el ID no sea null ni vacío
        if (dieta.getTipo() == null || dieta.getTipo().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("usuarios")
                .document(dieta.getTipo()+"_"+dieta.getDia())
                .set(dieta);

        WriteResult result = future.get();

        // Si se actualizó correctamente, devuelve el usuario
        if (result != null && result.getUpdateTime() != null) {
            return dieta;
        }

        return null;
    }

    public boolean deleteDieta(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            // Verificamos si el documento existe antes de intentar borrarlo
            DocumentReference docRef = dbFirestore.collection("dietas").document(id);
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
}
