package org.example.tfg.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.Calendario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CalendarioService {

    // Devuelve todos los calendarios
    public List<Calendario> getAllCalendario() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("calendario").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Calendario> calendarios = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Calendario calendario = doc.toObject(Calendario.class);
            calendarios.add(calendario);
        }

        return calendarios;
    }

    // Devuelve la lista de calendarios filtrados por id (puede devolver varios si hay duplicados)
    public List<Calendario> getFechasById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference calendarioRef = dbFirestore.collection("calendario");
        Query query = calendarioRef.whereEqualTo("id", id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Calendario> resultados = new ArrayList<>();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Calendario calendario = document.toObject(Calendario.class);

            // Verificar si fechas es null y loguear para debug
            if (calendario.getFechas() == null) {
                System.out.println("Documento con id " + document.getId() + " tiene fechas null");
                // Opcional: intentar extraer el campo manualmente para debug
                List<String> fechasManuales = (List<String>) document.get("fechas");
                System.out.println("Fechas extraídas manualmente: " + fechasManuales);
                calendario.setFechas(fechasManuales);
            }

            resultados.add(calendario);
        }
        return resultados;
    }

    // Añade un nuevo calendario
    public Calendario addCalendario(Calendario calendario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> future = dbFirestore.collection("calendario")
                .document(calendario.getId())
                .set(calendario);

        future.get();

        return calendario;
    }

    // Actualiza un calendario existente
    public Calendario updateCalendario(Calendario calendario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        if (calendario.getId() == null || calendario.getId().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("calendario")
                .document(calendario.getId())
                .set(calendario);

        WriteResult result = future.get();

        if (result != null && result.getUpdateTime() != null) {
            return calendario;
        }

        return null;
    }

    // Elimina un calendario por id
    public boolean deleteCalendario(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            DocumentReference docRef = dbFirestore.collection("calendario").document(id);
            ApiFuture<DocumentSnapshot> futureDoc = docRef.get();
            DocumentSnapshot document = futureDoc.get();

            if (!document.exists()) {
                return false;
            }

            ApiFuture<WriteResult> deleteFuture = docRef.delete();
            WriteResult result = deleteFuture.get();

            return result != null && result.getUpdateTime() != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Calendario addOrUpdateFechaActual(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("calendario").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        Calendario calendario;
        String fechaActual = java.time.LocalDate.now().toString(); // "YYYY-MM-DD"

        if (document.exists()) {
            calendario = document.toObject(Calendario.class);
            if (calendario.getFechas() == null) {
                calendario.setFechas(new ArrayList<>());
            }
            if (!calendario.getFechas().contains(fechaActual)) {
                calendario.getFechas().add(fechaActual);
            }
        } else {
            calendario = new Calendario();
            calendario.setId(id);
            List<String> fechas = new ArrayList<>();
            fechas.add(fechaActual);
            calendario.setFechas(fechas);
        }

        ApiFuture<WriteResult> writeResult = docRef.set(calendario);
        writeResult.get();

        return calendario;
    }

}
