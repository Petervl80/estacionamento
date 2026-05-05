package com.github.petervl80.estacionamento.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("caminhonete")
public class Caminhonete extends Veiculo {

    public Caminhonete(String placa, String modelo, String cor) {
        super(placa, modelo, cor);
    }

    @Override
    public double calcularValor(long horas) {
        return calcularBase(horas) * 1.50;
    }
}
