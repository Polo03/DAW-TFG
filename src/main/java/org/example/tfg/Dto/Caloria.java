package org.example.tfg.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "calorias")
public class Caloria {
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
    @Column(name = "consumidas", nullable = false)
    private Double consumidas;

    @NotNull
    @Column(name = "deseadas", nullable = false)
    private Double deseadas;

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

    public Double getConsumidas() {
        return consumidas;
    }

    public void setConsumidas(Double consumidas) {
        this.consumidas = consumidas;
    }

    public Double getDeseadas() {
        return deseadas;
    }

    public void setDeseadas(Double deseadas) {
        this.deseadas = deseadas;
    }

}