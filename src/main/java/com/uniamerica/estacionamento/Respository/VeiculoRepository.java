package com.uniamerica.estacionamento.Respository;

import com.uniamerica.estacionamento.Entity.Modelo;
import com.uniamerica.estacionamento.Entity.Movimentacao;
import com.uniamerica.estacionamento.Entity.Tipo;
import com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("from Movimentacao where Veiculo = :veiculo")
    public List<Movimentacao> findMovimentacaoByVeiculo(@Param("veiculo") final Veiculo veiculo);

    @Query("from Veiculo where id = :modelo")
    public List<Veiculo> findModelo(@RequestParam("modelo") final Modelo modelo);

    @Query("from Veiculo where placa = :placa")
    public List<Veiculo> findPlaca(@RequestParam("placa") final String placa);

    @Query("from Veiculo where tipo = :tipo")
    public List<Veiculo> findTipo(@RequestParam("tipo") final Tipo tipo);
}
