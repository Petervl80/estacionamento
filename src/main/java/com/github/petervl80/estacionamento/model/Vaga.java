package com.github.petervl80.estacionamento.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private boolean ocupada = false;

    public Vaga(Integer numero) {
        this.numero  = numero;
        this.ocupada = false;
    }
}
