package com.uniamerica.estacionamento.Respository;

import com.uniamerica.estacionamento.Entity.Configuracao;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
}
