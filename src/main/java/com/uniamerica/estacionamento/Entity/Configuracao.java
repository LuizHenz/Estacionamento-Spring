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
    @Column(name = "valor_hora", nullable = true, unique = true)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_minuto_hora", nullable = true, unique = true)
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicio_expediente", nullable = true, unique = true)
    private LocalTime incioExpediente;
    @Getter @Setter
    @Column(name = "fim_expediente", nullable = true, unique = true)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempo_para_desconto", nullable = true, unique = true)
    private Integer tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempo_de_desconto", nullable = true, unique = true)
    private BigDecimal tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerar_desconto", nullable = true, unique = true)
    private Boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagas_moto", nullable = true, unique = true)
    private Integer vagasMotos;
    @Getter @Setter
    @Column(name = "vagas_vans", nullable = true, unique = true)
    private Integer vagasVans;
    @Getter @Setter
    @Column(name = "vagas_carro", nullable = true, unique = true)
    private Integer vagasCarro;
}
