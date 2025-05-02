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

        SpringApplication.run(TfgApplication.class, args);
    }

}
