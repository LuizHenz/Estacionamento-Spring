package com.uniamerica.estacionamento.Service;


//import com.uniamerica.estacionamento.component.ValidaCPF;
import com.uniamerica.estacionamento.Entity.Condutor;
import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Respository.CondutorRepository;
import com.uniamerica.estacionamento.Respository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


    @Transactional(rollbackFor =  Exception.class)
    public ResponseEntity<String> cadastrar(final Condutor condutor) {

        //Verifica o NOME
        Assert.isTrue(condutor.getNome() != null , "Error digite um numero");

        //Verificar o CPF
        Assert.isTrue(condutor.getCpf() != null, "CPF, nao informado");
        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Error cpf com mascara errada");
        Assert.isTrue(this.condutorRepository.findCpf(condutor.getCpf()).isEmpty(), "CPF já existente.");

        //Verifica o TELEFONE
        Assert.isTrue(condutor.getTelefone() != null, "Error digite uma telefone");
        String regexTelefone = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";
        Assert.isTrue(condutor.getTelefone().matches(regexTelefone), "Mascara de telefone invalida");
        Assert.isTrue(this.condutorRepository.findTelefone(condutor.getTelefone()).isEmpty(), "Telefone já existente.");


        this.condutorRepository.save(condutor);
        return ResponseEntity.ok("Registro realizado com sucesso");
    }


    @Transactional(rollbackFor =  Exception.class)
    public void editar(final Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);

        //Verifica o TELEFONE
        Assert.isTrue(condutor.getTelefone() != null, "Error digite um telefone");
        String regexTelefone = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";
        Assert.isTrue(!condutor.getTelefone().matches(regexTelefone), "Mascara de telefone invalida");
        Assert.isTrue(this.condutorRepository.findTelefone(condutor.getTelefone()).isEmpty(), "Telefone já existente.");

        //verificar o CPF
        Assert.isTrue(condutorBanco != null || !condutorBanco.getId().equals(condutor.getId()),"Nao foi possivel identificar o registro");
        Assert.isTrue(condutor.getCpf() != null, "CPF, nao informado");
        Assert.isTrue(condutorRepository.findCpf(condutor.getCpf()).isEmpty(), "CPF ja exixte");

        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Error cpf com mascara errada");



        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor =  Exception.class)
    public void deletar (final Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);

        List<Movimentacao> movimentacaoList = this.condutorRepository.findMovimentacaoByCondutor(condutorBanco);

        //System.out.println(movimentacaoLista);

        if(movimentacaoList.isEmpty()) {
            this.condutorRepository.delete(condutorBanco);
        }else{
            condutorBanco.setAtivo(false);
            this.condutorRepository.save(condutor);
        }

    }




}