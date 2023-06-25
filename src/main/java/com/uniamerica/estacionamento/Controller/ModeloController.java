package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Modelo;
import com.uniamerica.estacionamento.Entity.Veiculo;
import com.uniamerica.estacionamento.Respository.ModeloRepository;
import com.uniamerica.estacionamento.Service.ModeloService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller //é uma anotação que indica que essa é uma classe controladora, onde o Spring pode gerenciar adequadamente
@RequestMapping(value = "/api/modelo")//Mapea as solicitações em HTTP para métodos especificos da controller
public class ModeloController {
    /**
     * Formas de gerar injecoes
     * 1 - Usar @AutoWired no repository
     * 2- Criar um constructor que recebe o repository
     */
    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ModeloService modeloService;


    /**
     * http://localhost:8080/api/modelo?id-1
     */
    //Metodo que mapeia e captura um valor de caminho da URL e usa esse valor para buscar recursos no database
    /**@GetMapping("/{id}")//Usado para mapear uma solicitacao HTTP GET, o endpoint definido como id, ou seja, a variavel "id" e capturada a partir do caminho da URL
    public ResponseEntity<?> findByIdPath (@PathVariable("id") final Long id){//@PathVariable e usado para vincular o valor da variavel id a variavel id do tipo Long
        return ResponseEntity.ok(new Modelo());//permite que o metodo retorne uma resposta HTTP personalizada
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdParam(@PathVariable("id") final Long id){//O request e usado para vincular o valor de id com o id do tipo Long, permite buscar recurso correspondente no database
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);//id e procurado dentro de Modelo do database usando findById do repository

        return modelo == null//se modelo for igual a nulo
                ? ResponseEntity.badRequest().body("Nenhum modelo encontrado.")//nulo = badrequest, mensagem
                : ResponseEntity.ok(modelo);//retornou com status 200 OK
    }

    @GetMapping("/lista")//mapeia a solicitacao GET com endpoint definido como lista, ou seja, busca a variavel correpondente e capturada a partir do endereco de URL
    public ResponseEntity<?> listCompleta(){//Encapsula a resposta HTTP em listCompleta
        return ResponseEntity.ok(this.modeloRepository.findAll());//vai encapsular a resposta 200 e o conteudo da lista usando o metodo findall do repository, que retorna a todos o objetos de Modelo do database
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> listaCompletaAtivo() {
        return ResponseEntity.ok(this.modeloRepository.findByAtivoTrue());
    }


    @PostMapping//anotacao para mapear solicitaçao POST para um endpoint
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){

        try{
            this.modeloService.cadastro(modelo);
            return ResponseEntity.ok("Registro realizado com sucesso");
        }catch (DataIntegrityViolationException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }catch (Exception exception) {
            return ResponseEntity.badRequest().body("Error" + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Modelo modelo){
        try{
            final Modelo modeloData = this.modeloRepository.findById(id).orElse(null);

            if(modeloData == null || !modeloData.getId().equals(modelo.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro no banco de dados");
            }

            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Registro atualizado");
        } catch(DataIntegrityViolationException erro){
            return ResponseEntity.internalServerError().body("Error" + erro.getCause().getCause().getMessage());
        } catch(RuntimeException erro){
            return ResponseEntity.internalServerError().body("Error" + erro.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") final Long id){
        final Modelo modeloData = this.modeloRepository.findById(id).orElse(null);

        try{
            this.modeloService.deletar(modeloData);
            return ResponseEntity.ok("Registro deletado.");
        } catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }
}
