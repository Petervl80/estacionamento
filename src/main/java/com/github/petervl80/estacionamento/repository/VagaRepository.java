package com.github.petervl80.estacionamento.repository;

import com.github.petervl80.estacionamento.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    Optional<Vaga> findByNumero(Integer numero);
}
