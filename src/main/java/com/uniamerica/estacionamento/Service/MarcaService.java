package com.uniamerica.estacionamento.Service;

import com.uniamerica.estacionamento.Entity.Marca;
import com.uniamerica.estacionamento.Entity.Modelo;
import com.uniamerica.estacionamento.Respository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(rollbackFor = Exception.class)
        public void cadastrar(final Marca marca){
        Assert.isTrue(marca.getNome() != null,"Error nome vazio, bruh");

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);

        Assert.isTrue(marca.getNome() != null, "Erro, digite um nome.");

        Assert.isTrue(marcaBanco != null || !marcaBanco.getId().equals(marca.getId()),"Marca já existente.");

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);

        List<Modelo> modeloLista = this.marcaRepository.findModeloByMarca(marcaBanco);

        if (modeloLista.isEmpty()){
            this.marcaRepository.delete(marcaBanco);
        }else{
            this.marcaRepository.save(marca);
        }
        if (marcaBanco != null){
            marcaBanco.setAtivo(false);
        }
    }
}
