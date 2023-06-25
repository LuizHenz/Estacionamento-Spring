package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Entity.Veiculo;
import com.uniamerica.estacionamento.Respository.ModeloRepository;
import com.uniamerica.estacionamento.Respository.VeiculoRepository;
import com.uniamerica.estacionamento.Service.VeiculoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdParam(@PathVariable("id") final Long id){
        final Veiculo veiculo = veiculoRepository.findById(id).orElse(null);

        return veiculo == null
                ? ResponseEntity.badRequest().body("Nenhum modelo encontrado")
                : ResponseEntity.ok(veiculo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> listaCompletaAtivo(){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){

        try{
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok("Registro realizado com sucesso");
        }catch (DataIntegrityViolationException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        } catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        } catch (Exception erro){
            return ResponseEntity.badRequest().body("Erro" + erro.getMessage());
        }
    }
     @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Veiculo veiculo){
        try{
            final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

            if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculo.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro no banco de dados");
            }

            this.veiculoService.editar(veiculo, id);
            return ResponseEntity.ok("Registro atualizado");
        }catch (DataIntegrityViolationException erro){
            return ResponseEntity.internalServerError().body("Erro" + erro.getCause().getCause().getMessage());
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro" + erro.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") final Long id){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

        try{
            this.veiculoService.deletar(veiculoBanco);
            return ResponseEntity.ok("Registro deletado");
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }
}
