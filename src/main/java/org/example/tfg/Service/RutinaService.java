package org.example.tfg.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Rutina;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RutinaService {

    public List<Rutina> getAllRutinas() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("rutinas").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Rutina> rutinas = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Rutina rutina = doc.toObject(Rutina.class);
            rutinas.add(rutina);
        }

        return rutinas;
    }

    public Rutina getRutinaById(String nombre) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("rutinas").document(nombre);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        System.out.println(document);
        Rutina r;
        if(document.exists()){
            r = document.toObject(Rutina.class);
            return r;
        }
        return null;
    }

    public Rutina addRutina(Rutina rutina) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> future = dbFirestore.collection("rutinas")
                .document(rutina.getNombre()+"_"+rutina.getTipo())
                .set(rutina);

        // Esperamos que se guarde correctamente
        future.get();

        return rutina;
    }

    public Rutina updateRutina(Rutina rutina) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Asegúrate de que el ID no sea null ni vacío
        if (rutina.getNombre() == null || rutina.getNombre().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("rutinas")
                .document(rutina.getNombre())
                .set(rutina);

        WriteResult result = future.get();

        // Si se actualizó correctamente, devuelve el usuario
        if (result != null && result.getUpdateTime() != null) {
            return rutina;
        }

        return null;
    }

    public boolean deleteRutina(String nombre) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            // Verificamos si el documento existe antes de intentar borrarlo
            DocumentReference docRef = dbFirestore.collection("rutinas").document(nombre);
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
