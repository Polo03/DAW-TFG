package org.example.tfg.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.Alimento;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AlimentoService {

    public List<Alimento> getAllAlimentos() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("alimentos").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Alimento> alimentos = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Alimento alimento = doc.toObject(Alimento.class);
            alimentos.add(alimento);
        }

        return alimentos;
    }

    public Alimento getAlimentoById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("alimentos").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        System.out.println(document);
        Alimento a;
        if(document.exists()){
            a = document.toObject(Alimento.class);
            return a;
        }
        return null;
    }

    public Alimento addAlimento(Alimento alimento) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        alimento.setId(dameUltimoId()); // solo si quieres guardar el ID generado de Firebase en el objeto

        ApiFuture<WriteResult> future = dbFirestore.collection("alimentos")
                .document(alimento.getNombre())
                .set(alimento);

        // Esperamos que se guarde correctamente
        future.get();

        return alimento;
    }

    public Alimento updateAlimento(Alimento alimento) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Asegúrate de que el ID no sea null ni vacío
        if (alimento.getId() == null || alimento.getId().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("alimentos")
                .document(alimento.getId())
                .set(alimento);

        WriteResult result = future.get();

        // Si se actualizó correctamente, devuelve el usuario
        if (result != null && result.getUpdateTime() != null) {
            return alimento;
        }

        return null;
    }

    public boolean deleteAlimento(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            // Verificamos si el documento existe antes de intentar borrarlo
            DocumentReference docRef = dbFirestore.collection("alimentos").document(id);
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
        List<Alimento> alimentos = getAllAlimentos();
        Integer idADevolver=0;
        for (Alimento a : alimentos) {
            try {
                Integer id = Integer.parseInt(a.getId());
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
