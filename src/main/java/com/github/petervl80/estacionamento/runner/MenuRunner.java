package com.github.petervl80.estacionamento.runner;

import com.github.petervl80.estacionamento.model.Caminhonete;
import com.github.petervl80.estacionamento.model.Carro;
import com.github.petervl80.estacionamento.model.Moto;
import com.github.petervl80.estacionamento.model.Veiculo;
import com.github.petervl80.estacionamento.service.Estacionamento;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MenuRunner implements CommandLineRunner {

    private final Estacionamento estacionamento;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        boolean rodando = true;
        while (rodando) {
            System.out.println("""
                    \n╔══════════════════════════════════╗
                    ║     SISTEMA DE ESTACIONAMENTO    ║
                    ╠══════════════════════════════════╣
                    ║ 1. Cadastrar veículo             ║
                    ║ 2. Registrar entrada             ║
                    ║ 3. Registrar saída               ║
                    ║ 4. Veículos estacionados         ║
                    ║ 5. Histórico de movimentações    ║
                    ║ 0. Sair                          ║
                    ╚══════════════════════════════════╝
                    Opção: """);

            String opcao = scanner.nextLine().trim();
            System.out.println();

            try {
                switch (opcao) {
                    case "1" -> cadastrarVeiculo();
                    case "2" -> registrarEntrada();
                    case "3" -> registrarSaida();
                    case "4" -> listarEstacionados();
                    case "5" -> historico();
                    case "0" -> rodando = false;
                    default  -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("⚠ Erro: " + e.getMessage());
            }
        }
        System.out.println("Encerrando sistema. Até logo!");
    }

    private void cadastrarVeiculo() {

        String tipo = null;

        while (tipo == null || !(tipo.equals("carro") || tipo.equals("moto") || tipo.equals("caminhonete"))) {
            System.out.print("Tipo (carro / moto / caminhonete): ");
            tipo = scanner.nextLine().trim().toLowerCase();
        }

        System.out.print("Placa: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();

        System.out.print("Cor: ");
        String cor = scanner.nextLine().trim();

        Veiculo v = switch (tipo) {
            case "carro"       -> new Carro(placa, modelo, cor);
            case "moto"        -> new Moto(placa, modelo, cor);
            case "caminhonete" -> new Caminhonete(placa, modelo, cor);
            default -> throw new IllegalArgumentException("Tipo inválido: " + tipo);
        };

        estacionamento.cadastrarVeiculo(v);
        System.out.println("✔ Veículo cadastrado com sucesso!");
    }

    private void registrarEntrada() {
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        System.out.print("Número da vaga: ");
        int vaga = Integer.parseInt(scanner.nextLine().trim());

        var mov = estacionamento.registrarEntrada(placa, vaga);
        System.out.printf("✔ Entrada registrada! Vaga %d | %s%n",
                mov.getVaga().getNumero(), mov.getDataEntrada());
    }

    private void registrarSaida() {
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        var mov = estacionamento.registrarSaida(placa);
        System.out.printf("✔ Saída registrada!%n" +
                        "  Entrada : %s%n" +
                        "  Saída   : %s%n" +
                        "  Valor   : R$ %.2f%n",
                mov.getDataEntrada(), mov.getDataSaida(), mov.getValorPago());
    }

    private void listarEstacionados() {
        var lista = estacionamento.listarEstacionados();
        if (lista.isEmpty()) {
            System.out.println("Nenhum veículo estacionado no momento.");
            return;
        }
        System.out.println("── Veículos no estacionamento ──");
        lista.forEach(m -> System.out.printf(
                "  Placa: %-10s | Modelo: %-15s | Vaga: %d | Entrada: %s%n",
                m.getVeiculo().getPlaca(),
                m.getVeiculo().getModelo(),
                m.getVaga().getNumero(),
                m.getDataEntrada()));
    }

    private void historico() {
        var lista = estacionamento.historico();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
            return;
        }
        System.out.println("── Histórico de movimentações ──");
        lista.forEach(m -> System.out.printf(
                "  Placa: %-10s | Entrada: %s | Saída: %s | Valor: R$ %.2f%n",
                m.getVeiculo().getPlaca(),
                m.getDataEntrada(),
                m.getDataSaida(),
                m.getValorPago()));
    }
}
