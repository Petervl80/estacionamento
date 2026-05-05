package com.github.petervl80.estacionamento.service;

import com.github.petervl80.estacionamento.model.Movimentacao;
import com.github.petervl80.estacionamento.model.Vaga;
import com.github.petervl80.estacionamento.model.Veiculo;
import com.github.petervl80.estacionamento.repository.MovimentacaoRepository;
import com.github.petervl80.estacionamento.repository.VagaRepository;
import com.github.petervl80.estacionamento.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Estacionamento {

    private final VeiculoRepository veiculoRepository;
    private final VagaRepository vagaRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new IllegalArgumentException("Placa já cadastrada: " + veiculo.getPlaca());
        }
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Movimentacao registrarEntrada(String placa, Integer numeroVaga) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado: " + placa));

        boolean jaEstacionado = movimentacaoRepository
                .existsByVeiculoAndDataSaidaIsNull(veiculo);

        if (jaEstacionado) {
            throw new IllegalStateException("Veículo já está estacionado: " + placa);
        }

        Vaga vaga = vagaRepository.findByNumero(numeroVaga)
                .orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada: " + numeroVaga));

        if (vaga.isOcupada()) {
            throw new IllegalStateException("Vaga já está ocupada: " + numeroVaga);
        }

        vaga.setOcupada(true);
        vagaRepository.save(vaga);

        Movimentacao mov = new Movimentacao(veiculo, vaga, LocalDateTime.now());
        return movimentacaoRepository.save(mov);
    }

    @Transactional
    public Movimentacao registrarSaida(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado: " + placa));

        Movimentacao mov = movimentacaoRepository
                .findByVeiculoAndDataSaidaIsNull(veiculo)
                .orElseThrow(() -> new IllegalStateException("Veículo não está estacionado: " + placa));

        LocalDateTime saida = LocalDateTime.now();
        long horas = ChronoUnit.HOURS.between(mov.getDataEntrada(), saida);

        if (ChronoUnit.MINUTES.between(mov.getDataEntrada(), saida) % 60 > 0) horas++;
        if (horas == 0) horas = 1;

        double valor = veiculo.calcularValor(horas);

        mov.setDataSaida(saida);
        mov.setValorPago(BigDecimal.valueOf(valor));
        movimentacaoRepository.save(mov);

        Vaga vaga = mov.getVaga();
        vaga.setOcupada(false);
        vagaRepository.save(vaga);

        return mov;
    }

    public List<Movimentacao> listarEstacionados() {
        return movimentacaoRepository.findByDataSaidaIsNull();
    }

    public List<Movimentacao> historico() {
        return movimentacaoRepository.findByDataSaidaIsNotNull();
    }
}
