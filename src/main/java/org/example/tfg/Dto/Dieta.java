package org.example.tfg.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "dietas")
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "objetivo", nullable = false)
    private String objetivo;

    @Size(max = 255)
    @NotNull
    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @Size(max = 255)
    @NotNull
    @Column(name = "desayuno", nullable = false)
    private String desayuno;

    @Size(max = 255)
    @NotNull
    @Column(name = "`media_mañana`", nullable = false)
    private String mediaMañana;

    @Size(max = 255)
    @NotNull
    @Column(name = "comida", nullable = false)
    private String comida;

    @Size(max = 255)
    @NotNull
    @Column(name = "merienda", nullable = false)
    private String merienda;

    @Size(max = 255)
    @NotNull
    @Column(name = "cena", nullable = false)
    private String cena;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String desayuno) {
        this.desayuno = desayuno;
    }

    public String getMediaMañana() {
        return mediaMañana;
    }

    public void setMediaMañana(String mediaMañana) {
        this.mediaMañana = mediaMañana;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }

    public String getMerienda() {
        return merienda;
    }

    public void setMerienda(String merienda) {
        this.merienda = merienda;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

}