package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Respository.ModeloRepository;
import com.uniamerica.estacionamento.Respository.MovimentacaoRepository;
import com.uniamerica.estacionamento.Service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<?> findByParam(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("Nenhuma movimentacao encontrada")
                : ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaMovimentacao(){
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Registro realizado.");
        }catch (Exception erro){
            return ResponseEntity.badRequest().body("Erro" + erro.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

            if (movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Registro nao identificado");
            }

            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Registro atualizado");
        }catch (DataIntegrityViolationException erro){
            return ResponseEntity.internalServerError().body("Erro" + erro.getCause().getCause().getMessage());
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro" + erro.getMessage());
        }
    }

    @PutMapping("/saida")
    public ResponseEntity<?> saida(@RequestParam("id") final Long id){
        try{
            this.movimentacaoService.saida(id);
            return ResponseEntity.ok("Registro realizado.");
        } catch (RuntimeException erro){
            return ResponseEntity.badRequest().body("Erro"+erro.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam("id") final Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);


        this.movimentacaoService.deletar(movimentacaoBanco);
        return ResponseEntity.ok("Ativo (movimentacao) alterado para false.");

    }
}
