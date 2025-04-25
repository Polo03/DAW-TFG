package org.example.tfg.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;


@Getter
@Setter
public class Usuario {

    private String id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(min = 2, max = 100)
    private String apellidos;

    @NotBlank(message = "El nickname no puede estar vacío")
    @Size(min = 3, max = 100)
    private String nickname;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{12,}$",
            message = "La contraseña debe tener al menos 12 caracteres, una mayúscula y un número."
    )
    private String password;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "\\d{8}[A-Za-z]", message = "DNI inválido (formato correcto: 12345678A)")
    private String dni;

    @NotNull(message = "El peso no puede ser nulo")
    @DecimalMin(value = "30.0", message = "Peso mínimo permitido: 30 kg")
    @DecimalMax(value = "300.0", message = "Peso máximo permitido: 300 kg")
    private BigDecimal peso;

    @NotNull(message = "La altura no puede ser nula")
    @DecimalMin(value = "100.0", message = "Altura mínima permitida: 100 cm")
    @DecimalMax(value = "250.0", message = "Altura máxima permitida: 250 cm")
    private BigDecimal altura;

    @NotBlank(message = "La fecha de nacimiento no puede estar vacía")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Formato de fecha: YYYY-MM-DD")
    private String fecha_nacimiento;

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Pattern(regexp = "\\d{9}", message = "Teléfono inválido")
    private String tlf;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Email inválido")
    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String direccion;

    @Column(nullable = false)
    private Boolean premium = false;

    @Pattern(regexp = "^(administrador|normal)$", message = "El tipo de usuario debe ser 'administrador' o 'normal'")
    @Column(nullable = false, length = 20)
    private String tipo_usuario = "normal";
}
