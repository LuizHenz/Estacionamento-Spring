package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Condutor;
import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Respository.CondutorRepository;
import com.uniamerica.estacionamento.Respository.MovimentacaoRepository;
import com.uniamerica.estacionamento.Service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    public CondutorRepository condutorRepository;

    @Autowired
    public CondutorService condutorService;

    //Endpoint de busca pelo condutor com base no parametro ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdParam(@PathVariable("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null
                ? ResponseEntity.badRequest().body("Condutor nao encontrado")
                : ResponseEntity.ok(condutor);
    }

    //Endpoint que retorna a uma lista completa de todos os condutores armazenados no banco de dados
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.condutorRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> listaCompletaAtivo(){
        return ResponseEntity.ok(this.condutorRepository.findByAtivoTrue());
    }


    //Endpoint com o objetivo de cadastrar um novo condutor
    //Recebe o objeto condutor fornecido pelo corpo HTTP pois é anotado pelo RequestBody
    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Condutor condutor) {
        //Try executa o método da service e caso ocorra algum erro é tratado pelo blocos catchs
        try{
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Regitro realizado com sucesso.");
        } catch (DataIntegrityViolationException erro){ //erro de violação de integridade de dados
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        } catch (RuntimeException erro){ //erro de varais exceções de tempo de execução
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        } catch (Exception erro){ // Se ocorrer outra exceção nao capturada pelos blocos anteriores
            return ResponseEntity.badRequest().body("Erro"+erro.getMessage());
        }
    }


    //Endpoint que realiza a edição de um condutor já existente com base no ID
    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@PathVariable("id") final Long id, @RequestBody final Condutor condutor){
        try{
            final Condutor condutorData = this.condutorRepository.findById(id).orElse(null);
            //Verifica se o ID de condutorData é nullo ou não é igual ao ID fornecido pelo corpo da requisição
            if(condutorData == null || !condutorData.getId().equals(condutor.getId())){
                throw new RuntimeException("Registro nao identificado");
            }
            this.condutorService.editar(condutorData);
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Registro atualizado");
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.internalServerError().body("Error"+ex.getCause().getMessage());
        }catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body("Erro"+ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") final Long id){
        final Condutor condutorData = this.condutorRepository.findById(id).orElse(null);

        try{
            this.condutorService.deletar(condutorData);
            return ResponseEntity.ok("Registro deletado");
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }
}
