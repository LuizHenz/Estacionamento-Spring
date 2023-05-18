package com.uniamerica.estacionamento.Respository;

import com.uniamerica.estacionamento.Entity.Marca;
import com.uniamerica.estacionamento.Entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query("from Modelo where marca = :marca")
    public List<Modelo> findModeloByMarca(@Param("marca") final Marca marca);
    @Query("from Marca where nome =:nome")
    public List<Marca> findNome(@Param("nome") final String nome);
}
