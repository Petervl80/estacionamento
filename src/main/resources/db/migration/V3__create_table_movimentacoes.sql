CREATE TABLE movimentacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    veiculo_id BIGINT NOT NULL,
    vaga_id BIGINT NOT NULL,
    data_entrada DATETIME NOT NULL,
    data_saida DATETIME,
    valor_pago DECIMAL(10, 2),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id),
    FOREIGN KEY (vaga_id) REFERENCES vagas(id)
);