package com.github.petervl80.estacionamento.repository;

import com.github.petervl80.estacionamento.model.Movimentacao;
import com.github.petervl80.estacionamento.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    boolean existsByVeiculoAndDataSaidaIsNull(Veiculo veiculo);

    Optional<Movimentacao> findByVeiculoAndDataSaidaIsNull(Veiculo veiculo);

    List<Movimentacao> findByDataSaidaIsNull();

    List<Movimentacao> findByDataSaidaIsNotNull();
}
