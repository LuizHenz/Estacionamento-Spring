package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_modelos", schema = "public")
public class Modelo extends Abstract{
    @Getter
    @Column(name = "nome", nullable = true, unique = true)
    private String nome;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;
}
