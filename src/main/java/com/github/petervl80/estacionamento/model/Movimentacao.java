package com.github.petervl80.estacionamento.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "valor_pago")
    private Double valorPago;

    public Movimentacao(Veiculo veiculo, Vaga vaga, LocalDateTime dataEntrada) {
        this.veiculo    = veiculo;
        this.vaga       = vaga;
        this.dataEntrada = dataEntrada;
    }

    public boolean isAberta() {
        return this.dataSaida == null;
    }
}
