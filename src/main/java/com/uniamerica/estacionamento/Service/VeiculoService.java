package com.uniamerica.estacionamento.Service;

import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Entity.Veiculo;
import com.uniamerica.estacionamento.Respository.ModeloRepository;
import com.uniamerica.estacionamento.Respository.MovimentacaoRepository;
import com.uniamerica.estacionamento.Respository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    MovimentacaoRepository movimentacaoRepository;


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){
        Assert.isTrue(veiculo.getModelo() != null, "Erro, digite um modelo");
        Assert.isTrue(veiculo.getTipo() != null, "Erro, digite um tipo");
        Assert.isTrue(veiculo.getCor() != null, "Erro, digite uma cor");
        Assert.isTrue(veiculo.getPlaca() != null, "Erro, digite uma placa");
        String regexPlaca = "^\\[A-Z]{3}\\-\\d{4}";
        Assert.isTrue(veiculo.getPlaca().matches(regexPlaca), "Erro, formato da placa incorreto");
        Assert.isTrue(this.veiculoRepository.findPlaca(veiculo.getPlaca()).isEmpty(),"Erro, placa já existente.");

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Veiculo veiculo, final Long id){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(veiculo.getId()).orElse(null);

        Assert.isTrue(veiculo.getModelo() != null, "Erro, digite um modelo");
        Assert.isTrue(veiculo.getCor() != null, "Erro, digite uma cor");
        Assert.isTrue(veiculo.getTipo() != null, "Erro, digite um tipo de veiculo");

        Assert.isTrue(veiculo.getPlaca() != null, "Erro, digite uma placa");
        String regexPlaca = "^\\[A-Z]{3}-\\d{4}$";
        Assert.isTrue(veiculo.getPlaca().matches(regexPlaca), "Erro, formato da placa incorreto");
        Assert.isTrue(this.veiculoRepository.findPlaca(veiculo.getPlaca()).isEmpty(),"Erro, placa já existente.");

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(veiculo.getId()).orElse(null);

        List<Movimentacao> veiculoLista = this.movimentacaoRepository.findVeiculo(veiculoBanco);

        if (veiculoLista.isEmpty()){
            this.veiculoRepository.delete(veiculoBanco);
        }else{
            veiculoBanco.setAtivo(false);
            this.veiculoRepository.save(veiculo);
        }
    }
}
