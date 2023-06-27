package com.uniamerica.estacionamento.Service;

import com.uniamerica.estacionamento.Entity.Modelo;
import com.uniamerica.estacionamento.Entity.Veiculo;
import com.uniamerica.estacionamento.Respository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastro(final Modelo modelo){

        Assert.isTrue(modelo.getMarca() != null, "Erro, sem marca.");
        Assert.isTrue(!modelo.getNome().isBlank(), "Erro, nome vazio.");

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(modelo.getId()).orElse(null);

        Assert.isTrue(modelo.getMarca() != null, "Erro, sem marca.");
        Assert.isTrue(modelo.getNome() != null, "Erro, digite um nome.");

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(modelo.getId()).orElse(null);

        List<Veiculo> modeloLista = this.modeloRepository.findVeiculoByModelo(modeloBanco);

        if(modeloLista.isEmpty()){
            this.modeloRepository.delete(modeloBanco);
        }else {
            modeloBanco.setAtivo(Boolean.FALSE);
            this.modeloRepository.save(modelo);
        }
    }
}
