package org.example.tfg;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties
public class TfgApplication {

    public static void main(String[] args) throws IOException {

        // Cargar el archivo de credenciales de Firebase desde resources
        ClassPathResource resource = new ClassPathResource("eatfit-firebase.json");

        try (InputStream serviceAccount = resource.getInputStream()) {

            // Configurar Firebase con las credenciales
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://eatfit-137a8-default-rtdb.firebaseio.com")
                    .build();

            // Inicializar Firebase solo si no está inicializado
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }

        // Arrancar la aplicación de Spring Boot
        SpringApplication.run(TfgApplication.class, args);
    }

}
