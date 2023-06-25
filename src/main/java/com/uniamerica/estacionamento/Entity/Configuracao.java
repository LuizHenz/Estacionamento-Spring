package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "tb_configuracoes", schema = "public")
public class Configuracao extends Abstract{
    @Getter
    @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_minuto_hora")
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicio_expediente")
    private LocalTime incioExpediente;
    @Getter @Setter
    @Column(name = "fim_expediente")
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempo_para_desconto")
    private Integer tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempo_de_desconto")
    private BigDecimal tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerar_desconto")
    private Boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagas_moto")
    private Integer vagasMotos;
    @Getter @Setter
    @Column(name = "vagas_vans")
    private Integer vagasVans;
    @Getter @Setter
    @Column(name = "vagas_carro")
    private Integer vagasCarro;
}
