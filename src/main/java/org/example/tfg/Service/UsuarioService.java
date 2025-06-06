package org.example.tfg.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.LoginRequest;
import org.example.tfg.Dto.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioService {

    public List<Usuario> getAllUsers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("usuarios").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Usuario> usuarios = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            Usuario usuario = doc.toObject(Usuario.class);
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public Usuario getUserById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("usuarios").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(Usuario.class);
        }

        return null;
    }

    public Usuario addUser(Usuario usuario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        usuario.setId(dameUltimoId()); // Genera ID incremental

        ApiFuture<WriteResult> future = dbFirestore.collection("usuarios")
                .document(usuario.getId())
                .set(usuario);

        future.get(); // Espera a que se guarde correctamente

        return usuario;
    }

    public Usuario updateUser(Usuario usuario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("usuarios")
                .document(usuario.getId())
                .set(usuario);

        WriteResult result = future.get();
        if (result != null && result.getUpdateTime() != null) {
            return usuario;
        }

        return null;
    }

    public boolean deleteUser(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            DocumentReference docRef = dbFirestore.collection("usuarios").document(id);
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

    public String dameUltimoId() throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        int maxId = 0;
        for (Usuario u : usuarios) {
            try {
                int id = Integer.parseInt(u.getId());
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ignored) {}
        }
        return String.valueOf(maxId + 1);
    }

    public Usuario validarLogin(LoginRequest loginRequest) throws ExecutionException, InterruptedException {
        if (loginRequest.getNickname() == null || loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("El nickname y la contraseña no pueden ser nulos.");
        }
        List<Usuario> usuarios = getAllUsers();

        for (Usuario u : usuarios) {
            if (u.getNickname() != null && u.getPassword() != null &&
                    u.getNickname().equals(loginRequest.getNickname()) &&
                    u.getPassword().equals(loginRequest.getPassword())) {
                return u;
            }
        }

        return null;
    }

    public boolean existeNickname(String nickname) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios) {
            if (u.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeDNI(String dni) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios) {
            if (u.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeEmail(String email) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeTelefono(String telefono) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios) {
            if (u.getTlf().equals(telefono)) {
                return true;
            }
        }
        return false;
    }
    public Usuario actualizarPlanPremium(String usuarioId, String nuevoPlan) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        DocumentReference usuarioDoc = dbFirestore.collection("usuarios").document(usuarioId);

        // Actualizamos solo el campo "plan" (o el campo que uses para el tipo de plan)
        ApiFuture<WriteResult> future = usuarioDoc.update("premium", nuevoPlan);

        future.get(); // Espera a que se complete la actualización

        // Opcional: recuperar el usuario actualizado para devolverlo
        ApiFuture<DocumentSnapshot> snapshotFuture = usuarioDoc.get();
        DocumentSnapshot snapshot = snapshotFuture.get();

        if (snapshot.exists()) {
            Usuario usuarioActualizado = snapshot.toObject(Usuario.class);
            return usuarioActualizado;
        } else {
            return null; // o lanza excepción si no existe
        }
    }

}
