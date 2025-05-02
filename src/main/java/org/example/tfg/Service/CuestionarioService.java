package org.example.tfg.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Cuestionario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CuestionarioService {

    public List<Cuestionario> getAllCuestionarios() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("cuestionario").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Cuestionario> cuestionarios = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Cuestionario cuestionario = doc.toObject(Cuestionario.class);
            cuestionarios.add(cuestionario);
        }

        return cuestionarios;
    }

    public Cuestionario getCuestionarioById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("cuestionario").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Cuestionario c;
        if(document.exists()){
            c = document.toObject(Cuestionario.class);
            return c;
        }
        return null;
    }

    public Cuestionario addCuestionario(Cuestionario cuestionario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        cuestionario.setId(dameUltimoId()); // solo si quieres guardar el ID generado de Firebase en el objeto

        ApiFuture<WriteResult> future = dbFirestore.collection("cuestionario")
                .document(cuestionario.getId())
                .set(cuestionario);

        // Esperamos que se guarde correctamente
        future.get();

        return cuestionario;
    }

    public Cuestionario updateCuestionario(Cuestionario cuestionario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Asegúrate de que el ID no sea null ni vacío
        if (cuestionario.getId() == null || cuestionario.getId().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("cuestionario")
                .document(cuestionario.getId())
                .set(cuestionario);

        WriteResult result = future.get();

        // Si se actualizó correctamente, devuelve el usuario
        if (result != null && result.getUpdateTime() != null) {
            return cuestionario;
        }

        return null;
    }

    public boolean deleteCuestionario(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            // Verificamos si el documento existe antes de intentar borrarlo
            DocumentReference docRef = dbFirestore.collection("cuestionario").document(id);
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
        List<Cuestionario> cuestionarios = getAllCuestionarios();
        Integer idADevolver=0;
        for (Cuestionario c : cuestionarios) {
            try {
                Integer id = Integer.parseInt(c.getId());
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
