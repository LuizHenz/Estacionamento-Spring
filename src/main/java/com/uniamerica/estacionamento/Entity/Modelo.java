package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_modelos", schema = "public")
public class Modelo extends Abstract{
    @Getter
    @Setter
    //@Size(min = 2, max = 50, message = "O nome deve ter entre 2 a 50 caracteres.")
    @Column(name = "nome", nullable = true, unique = true)
    private String nome;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;
}
