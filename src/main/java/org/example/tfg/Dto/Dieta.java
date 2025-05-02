package org.example.tfg.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class Dieta {

    private String tipo;

    private String dia;

    private String desayuno;

    private String media_mañana;

    private String comida;

    private String merienda;

    private String cena;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String desayuno) {
        this.desayuno = desayuno;
    }

    public String getMedia_mañana() {
        return media_mañana;
    }

    public void setMedia_mañana(String media_merienda) {
        this.media_mañana = media_merienda;
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
