package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_marcas", schema = "public")
public class Marca extends Abstract{
    @Getter
    @Column(name = "nome", nullable = true, unique = true)
    private String nome;
}
