package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Configuracao;
import com.uniamerica.estacionamento.Respository.ConfiguracaoRepository;
import com.uniamerica.estacionamento.Service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);

        return configuracao == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(configuracao);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoService.cadastrar(configuracao);
            return ResponseEntity.ok("Registro realizado com sucesso");
        } catch (RuntimeException erro){
            return ResponseEntity.badRequest().body("Erro"+erro.getMessage());
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.configuracaoRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@PathVariable("id") final Long id, @RequestBody Configuracao configuracao){
        try{
            this.configuracaoService.editar(id,configuracao);

            return ResponseEntity.ok().body("Registro salvo com sucesso");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") final Long id){
        final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);

        if (configuracaoBanco == null){
            return ResponseEntity.ok("Nenhum registro com esse ID encontrado.");
        }else{
            configuracaoBanco.setAtivo(false);
            this.configuracaoRepository.delete(configuracaoBanco);
            return ResponseEntity.ok("Ativo (configuracao) alterado para false e deletado");
        }
    }
}
