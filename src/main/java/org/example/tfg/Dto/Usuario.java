package org.example.tfg.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
