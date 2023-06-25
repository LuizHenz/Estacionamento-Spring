package com.uniamerica.estacionamento.Controller;

import com.uniamerica.estacionamento.Entity.Condutor;
import com.uniamerica.estacionamento.Entity.Marca;
import com.uniamerica.estacionamento.Entity.Modelo;
import com.uniamerica.estacionamento.Respository.MarcaRepository;
import com.uniamerica.estacionamento.Service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController {

    @Autowired
    public MarcaRepository marcaRepository;

    @Autowired
    public MarcaService marcaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByParam(@PathVariable("id") final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("Condutor nao encontrado")
                : ResponseEntity.ok(marca);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta() {
        return ResponseEntity.ok(this.marcaRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> listaCompletaAtivo() {
        return ResponseEntity.ok(this.marcaRepository.findByAtivoTrue());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try{
            this.marcaService.cadastrar(marca);
            return ResponseEntity.ok("Registro realizado");
        }catch (Exception erro){
            return ResponseEntity.badRequest().body("Erro" + erro.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@PathVariable("id") final Long id, @RequestBody final Marca marca){
        try{
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if(marcaBanco == null || !marcaBanco.getId().equals(marca.getId())){
                throw new RuntimeException("Registro nao identificado");
            }

            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Registro atualizado.");
        }catch (DataIntegrityViolationException erro){
            return ResponseEntity.internalServerError().body("Error"+erro.getCause().getCause().getMessage());
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") final Long id){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        try{
            this.marcaService.deletar(marcaBanco);
            return ResponseEntity.ok("Registro deletado");
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }
}
