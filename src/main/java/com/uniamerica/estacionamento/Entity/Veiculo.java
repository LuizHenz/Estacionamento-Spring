package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;
    @Getter @Setter
    @Column(name = "ano")
    private int ano;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "cor")
    private Cor cor;
}
