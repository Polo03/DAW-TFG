package org.example.tfg.Service;

import com.google.api.core.ApiFuture;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.tfg.Dto.LoginRequest;
import org.example.tfg.Dto.Usuario;
import org.springframework.stereotype.Service;

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
        System.out.println(document);
        Usuario u;
        if(document.exists()){
            u = document.toObject(Usuario.class);
            System.out.println(u);
            return u;
        }
        return null;
    }

    public Usuario addUser(Usuario usuario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        usuario.setId(dameUltimoId()); // solo si quieres guardar el ID generado de Firebase en el objeto

        ApiFuture<WriteResult> future = dbFirestore.collection("usuarios")
                .document(usuario.getId())
                .set(usuario);

        // Esperamos que se guarde correctamente
        future.get();

        return usuario;
    }

    public Usuario updateUser(Usuario usuario) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Asegúrate de que el ID no sea null ni vacío
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            return null;
        }

        ApiFuture<WriteResult> future = dbFirestore
                .collection("usuarios")
                .document(usuario.getId())
                .set(usuario);

        WriteResult result = future.get();

        // Si se actualizó correctamente, devuelve el usuario
        if (result != null && result.getUpdateTime() != null) {
            return usuario;
        }

        return null;
    }

    public boolean deleteUser(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();

            // Verificamos si el documento existe antes de intentar borrarlo
            DocumentReference docRef = dbFirestore.collection("usuarios").document(id);
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
        List<Usuario> usuarios = getAllUsers();
        Integer idADevolver=0;
        for (Usuario u : usuarios) {
            try {
                Integer id = Integer.parseInt(u.getId());
                // Aquí sí es numérico
                if(idADevolver<id)
                    idADevolver=id;
            } catch (NumberFormatException e) {
                // No es un número → lo ignoramos y continuamos sin cortar
            }
        }
        return String.valueOf(idADevolver+1);
    }

    public boolean validarLogin(LoginRequest loginRequest) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios){
            if(u.getNickname().equals(loginRequest.getNickname()) && u.getPassword().equals(loginRequest.getPassword()))
                return true;
        }
        return false;
    }

    public boolean existeNickname(String nickname) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios){
            if(u.getNickname().equals(nickname))
                return true;
        }
        return false;
    }

    public boolean existeDNI(String dni) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios){
            if(u.getDni().equals(dni))
                return true;
        }
        return false;
    }

    public boolean existeEmail(String email) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios){
            if(u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public boolean existeTelefono(String telefono) throws ExecutionException, InterruptedException {
        List<Usuario> usuarios = getAllUsers();
        for (Usuario u : usuarios){
            if(u.getTlf().equals(telefono))
                return true;
        }
        return false;
    }
}

