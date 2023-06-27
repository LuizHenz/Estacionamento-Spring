package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Recibo;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findByParam(@PathVariable("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("Nenhuma movimentacao encontrada")
                : ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaMovimentacao(){
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }

    @GetMapping("/abertas")
    public ResponseEntity<?> findByAberta(){
        return ResponseEntity.ok(this.movimentacaoRepository.findByAberta());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("Registro realizado.");
        }catch (Exception erro){
            return ResponseEntity.badRequest().body("Erro" + erro.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@PathVariable("id") final Long id, @RequestBody final Movimentacao movimentacao){
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

    @PutMapping("/saida/{id}")
    public ResponseEntity<?> saida(@PathVariable("id") final Long id){
        try{
            Recibo recibo = this.movimentacaoService.saida(id);
            return ResponseEntity.ok(recibo);
        } catch (RuntimeException erro){
            return ResponseEntity.badRequest().body("Erro"+erro.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar (@PathVariable("id") final Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);


        this.movimentacaoService.deletar(movimentacaoBanco);
        return ResponseEntity.ok("Ativo (movimentacao) alterado para false.");

    }
}
