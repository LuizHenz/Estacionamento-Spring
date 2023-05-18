package com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Abstract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;
    @Getter @Setter
    private LocalDateTime cadastro;
    @Getter
    private LocalDateTime atualizacao;
    @Getter @Setter
    private boolean ativo;


    @PrePersist
    public void prePersiste(){
        this.cadastro=LocalDateTime.now();
        this.ativo=true;
    }

    @PreUpdate
    public void preUpdate(){
        this.atualizacao=LocalDateTime.now();
    }

}
