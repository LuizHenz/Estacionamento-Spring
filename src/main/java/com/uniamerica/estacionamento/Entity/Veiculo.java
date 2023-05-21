package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_veiculos", schema = "public")
public class Veiculo extends Abstract{
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "modelo")
    private Modelo modelo;
    @Getter
    //@Size(min = 8, max = 10, message = "Deve ter entre 8 a 10 caracteres.")
    @Column(name = "placa", nullable = true, unique = true)
    private String placa;
    @Getter @Setter
    @Column(name = "ano", nullable = true)
    private int ano;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = true)
    private Tipo tipo;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "cor", nullable = true)
    private Cor cor;
}
