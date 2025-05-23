package org.example.tfg.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private Resource firebaseConfig;

    @Value("${firebase.database.url}")
    private String firebaseDatabaseUrl;

    @PostConstruct
    public void initialize() {
        try (InputStream serviceAccount = getClass().getClassLoader()
                .getResourceAsStream("eatfit-137a8-firebase-adminsdk-fbsvc-38cca39cf7.json")) {
            if (serviceAccount == null) {
                throw new FileNotFoundException("Archivo eatfit-137a8-firebase-adminsdk-fbsvc-38cca39cf7.json no encontrado");
            }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

