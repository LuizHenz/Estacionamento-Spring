package com.uniamerica.estacionamento.Service;

import com.uniamerica.estacionamento.Entity.Condutor;
import com.uniamerica.estacionamento.Entity.Configuracao;
import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Respository.CondutorRepository;
import com.uniamerica.estacionamento.Respository.ConfiguracaoRepository;
import com.uniamerica.estacionamento.Respository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

    @Transactional
    public void cadastrar(Movimentacao movimentacao){

        Assert.isTrue(movimentacao.getVeiculo() != null, "Veiculo nao informado");
        Assert.isTrue(movimentacao.getCondutor() != null, "Condutor nao informada");
        Assert.isTrue(movimentacao.getEntrada() != null, "Entradada nao informado");
        Assert.isTrue(movimentacao.getSaida() != null, "Saida nao informada");

        this.movimentacaoRepository.save(movimentacao);

    }

    @Transactional
    public void editar(final Long id, Movimentacao movimentacao){

        Assert.isTrue(movimentacao.getVeiculo() != null, "Veiculo nao informado");
        Assert.isTrue(movimentacao.getCondutor() != null, "Condutor nao informada");
        Assert.isTrue(movimentacao.getEntrada() != null, "Entradada nao informado");
        Assert.isTrue(movimentacao.getSaida() != null, "Saida nao informada");

        final Movimentacao moviBanco = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.isTrue(moviBanco != null, "nao foi possivel encontrar o registro");


        this.movimentacaoRepository.save(movimentacao);
    }
    @Transactional(rollbackOn = Exception.class)
    public void saida (final Long id){

        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        final Configuracao configuracao = this.configuracaoRepository.findById(1L).orElse(null);

        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        //Verifica se a configuração foi encontrada
        Assert.isTrue(configuracao != null, "Configuração não encontrada.");
        //Verifica se a movimentação foi encontrada
        Assert.isTrue(movimentacao != null, "Movimentação não encontrada.");

        //Definida a hora e data atual ao atributo saida
        movimentacao.setSaida(LocalDateTime.now());

        //É criada uma variavel saida com a hora e data atual
        final LocalDateTime saida = LocalDateTime.now();
        //Calculo que subtrai data e hora de entrada da data e hora atual de saida
        Duration duracao = Duration.between(movimentacao.getEntrada(), saida);

        //O valor de duração de horas é convertido em BigDecimal e atribuido a horas
        final BigDecimal horas = BigDecimal.valueOf(duracao.toHoursPart());
        //A duração de minutos é convertida em BigDecimal e dividida por 60 para obter minutos em decimal, valor arredondado com 2 casas decimais usando half even
        final BigDecimal minutos = BigDecimal.valueOf(duracao.toMinutesPart()).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_EVEN);
        //O preço é calculado multiplicando o valor de hora pelo numero de horas e adicionando o valor hora multiplicado pelos minutos
        BigDecimal preco = configuracao.getValorHora().multiply(horas).add(configuracao.getValorHora().multiply(minutos));

        movimentacao.setHora(duracao.toHoursPart());//O numero de horas da duração é atribuido ao atributo hora
        movimentacao.setMinutos(duracao.toMinutesPart());//O numero de minutos de duração é atribuido ao atributo minutos

        BigDecimal valor = configuracao.getValorHora().multiply(horas).add(configuracao.getValorHora().multiply(minutos));

        movimentacao.setValorTotal(valor);



//        Relatorio relatorio = new Relatorio(moviBanco.getEntrada(), moviBanco.getSaida(), moviBanco.getCondutor(),
//                moviBanco.getVeiculo(), moviBanco.getTempoHora(), condutor.getTempoDesconto(),
//                precos.subtract(valor_desconto).setScale(2,RoundingMode.HALF_EVEN),
//                moviBanco.getValorDeconto());


        this.condutorRepository.save(condutor);

        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void deletar(final Movimentacao movimentacao){
        final Movimentacao moviBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);

        moviBanco.setAtivo(Boolean.FALSE);
        this.movimentacaoRepository.save(movimentacao);
    }
}
