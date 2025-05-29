package org.example.tfg.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor  // importante para Firestore
public class Calendario {
    private String id;
    private List<String> fechas;
}
