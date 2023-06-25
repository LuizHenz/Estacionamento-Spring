package com.uniamerica.estacionamento.Respository;

import com.uniamerica.estacionamento.Entity.Condutor;
import com.uniamerica.estacionamento.Entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    @Query("from Movimentacao where condutor = :condutor")
    public List<Movimentacao> findMovimentacaoByCondutor(@Param("condutor") final Condutor condutor);

    @Query("from Condutor where nome = :nome")
    public List<Condutor> findNome(@Param("nome") final String nome);

    @Query("from Condutor where cpf = :cpf")
    public List<Condutor> findCpf(@Param("cpf") final String cpf);

    @Query("from Condutor where telefone = :telefone")
    public List<Condutor> findTelefone(@Param("telefone") final String telefone);

    public List<Condutor> findByAtivoTrue();
}
