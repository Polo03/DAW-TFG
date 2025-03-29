package org.example.tfg.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "objetivos")
public class Objetivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @Column(name = "objetivo1", nullable = false)
    private Integer objetivo1;

    @NotNull
    @Column(name = "objetivo2", nullable = false)
    private Integer objetivo2;

    @NotNull
    @Column(name = "objetivo3", nullable = false)
    private Integer objetivo3;

    @NotNull
    @Column(name = "objetivo4", nullable = false)
    private Integer objetivo4;

    @NotNull
    @Column(name = "objetivo5", nullable = false)
    private Integer objetivo5;

    @NotNull
    @Column(name = "objetivo6", nullable = false)
    private Integer objetivo6;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getObjetivo1() {
        return objetivo1;
    }

    public void setObjetivo1(Integer objetivo1) {
        this.objetivo1 = objetivo1;
    }

    public Integer getObjetivo2() {
        return objetivo2;
    }

    public void setObjetivo2(Integer objetivo2) {
        this.objetivo2 = objetivo2;
    }

    public Integer getObjetivo3() {
        return objetivo3;
    }

    public void setObjetivo3(Integer objetivo3) {
        this.objetivo3 = objetivo3;
    }

    public Integer getObjetivo4() {
        return objetivo4;
    }

    public void setObjetivo4(Integer objetivo4) {
        this.objetivo4 = objetivo4;
    }

    public Integer getObjetivo5() {
        return objetivo5;
    }

    public void setObjetivo5(Integer objetivo5) {
        this.objetivo5 = objetivo5;
    }

    public Integer getObjetivo6() {
        return objetivo6;
    }

    public void setObjetivo6(Integer objetivo6) {
        this.objetivo6 = objetivo6;
    }

}