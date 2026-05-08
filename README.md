# 🚗 Sistema de Estacionamento 🏍️

> Um sistema robusto para gestão de pátio de estacionamento, permitindo controlo de entradas e saídas, cálculo automático de tarifas por tipo de veículo e persistência de dados.

---

# 🚀 Tecnologias Utilizadas

| Tecnologia | Descrição |
|---|---|
| ☕ **Java 21** | Utilização das funcionalidades mais recentes da linguagem |
| 🌱 **Spring Boot 4.0.6** | Framework principal da aplicação |
| 🗄️ **Spring Data JPA** | Abstração da camada de persistência |
| 🐬 **MySQL 8.0** | Base de dados relacional |
| 🛫 **Flyway** | Controle de versões e migrations da base de dados |
| ⚡ **Lombok** | Redução de código boilerplate |
| 🐳 **Docker** | Orquestração da base de dados |

---

# 📋 Funcionalidades

## ✅ Cadastro de Veículos
O sistema suporta diferentes tipos de veículos:

- 🚗 Carros
- 🏍️ Motos
- 🚙 Caminhonetes

---

## ✅ Registro de Entrada
Ao entrar no estacionamento:

- O veículo é associado a uma vaga disponível
- O horário de entrada é registado automaticamente
- A vaga passa a ficar ocupada

---

## ✅ Registro de Saída
Ao sair do estacionamento:

- A vaga é libertada
- O tempo de permanência é calculado
- O valor total é gerado automaticamente

---

## ✅ Listagem de Veículos Estacionados
Visualização em tempo real dos veículos atualmente no pátio.

---

## ✅ Histórico de Movimentações
Consulta completa de:

- Entradas e saídas realizadas
- Valores arrecadados
- Tempo de permanência dos veículos

---

# 💰 Regras de Tarifação

## 🕐 Valor Base

| Tempo | Valor |
|---|---|
| Primeira hora | **R$ 5,00** |
| Hora adicional | **R$ 3,00** |

---

## 🚘 Multiplicador por Tipo de Veículo

| Tipo de Veículo | Percentual Aplicado |
|---|---|
| 🚗 Carro | 100% |
| 🏍️ Moto | 50% |
| 🚙 Caminhonete | 150% |

---

## 🧮 Exemplo de Cálculo

### 🚗 Carro — 3 horas
- Primeira hora: R$ 5,00
- 2 horas adicionais: R$ 6,00
- **Total: R$ 11,00**

### 🏍️ Moto — 3 horas
- Valor base: R$ 11,00
- Aplicando 50%
- **Total: R$ 5,50**

### 🚙 Caminhonete — 3 horas
- Valor base: R$ 11,00
- Aplicando 150%
- **Total: R$ 16,50**

---

# 🛠️ Como Executar

## 📌 Pré-requisitos

Antes de iniciar, certifique-se de possuir:

- ✅ Docker instalado
- ✅ Docker Compose instalado
- ✅ Java 21 instalado

---

# 🐳 Configuração da Base de Dados

Inicie o contentor MySQL utilizando o Docker Compose:

```bash
docker-compose up -d
```

📍 O MySQL ficará disponível na porta:

```bash
3309
```

---

# ▶️ Executando a Aplicação

Utilize o Maven Wrapper incluído no projeto.

## Linux/macOS

```bash
./mvnw spring-boot:run
```

## Windows

```bash
mvnw.cmd spring-boot:run
```

---

# 🗄️ Estrutura da Base de Dados

O esquema é gerado automaticamente pelo Flyway através das migrations.

---

## 📄 Tabela `veiculos`

Responsável por armazenar:

- Dados técnicos do veículo
- Tipo do veículo
- Informações de identificação

---

## 📄 Tabela `vagas`

Responsável por:

- Controlar vagas disponíveis
- Indicar vagas ocupadas
- Gerenciar numeração das vagas

📌 O sistema inicia automaticamente com **5 vagas numeradas**.

---

## 📄 Tabela `movimentacoes`

Responsável por armazenar:

- Horário de entrada
- Horário de saída
- Tempo de permanência
- Valor cobrado

---

# 📦 Estrutura Geral do Projeto

```bash
src
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┗ 📂 com.seuprojeto.estacionamento
 ┃ ┃    ┣ 📂 controller
 ┃ ┃    ┣ 📂 service
 ┃ ┃    ┣ 📂 repository
 ┃ ┃    ┣ 📂 entity
 ┃ ┃    ┗ 📂 dto
 ┃ ┗ 📂 resources
 ┃    ┣ 📂 db/migration
 ┃    ┗ application.yml
 ┗ 📂 test
```

---

# 🎯 Objetivos do Projeto

Este projeto foi desenvolvido com foco em:

- ✅ Boas práticas com Spring Boot
- ✅ Arquitetura em camadas
- ✅ Persistência de dados com JPA
- ✅ Controle de migrations com Flyway
- ✅ Utilização de Docker em ambiente de desenvolvimento
- ✅ Organização e escalabilidade do código

---

# 👨‍💻 Autor

Projeto desenvolvido para fins de estudo e prática de desenvolvimento backend com Java + Spring Boot.