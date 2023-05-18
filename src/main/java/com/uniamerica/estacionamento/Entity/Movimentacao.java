package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tb_movimentacoes", schema = "public")
public class Movimentacao extends Abstract{
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "veiculo", nullable = true)
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "condutor", nullable = true)
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "entrada")
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "saida")
    private LocalDateTime saida;
    @Getter @Setter
    @Column(name = "tempo")
    private Long tempo;
    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempo_multa")
    private LocalTime tempoMulta;
    @Getter @Setter
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_hora_multa")
    private BigDecimal valorHoraMulta;
}
