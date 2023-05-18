package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "tb_condutores", schema = "public")
        /**
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cpf"}, name = "uq_condutor_cpf"),
        @UniqueConstraint(columnNames = {"telefone"}, name = "uq_condutor_telefone")
        })*/
public class Condutor extends Abstract{
    @Getter @Setter
    @Column(name = "nome", nullable = false)
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;
    @Getter @Setter
    @Column(name = "tempo_pago")
    private LocalTime tempoPago;
    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;
}
