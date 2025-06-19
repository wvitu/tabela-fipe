# 📊 Tabela FIPE - Projeto de Consumo de API em Java

Este projeto foi desenvolvido como prática do curso da Alura, com diversas melhorias adicionais para fins de portfólio.

---

## 🌟 Objetivo

Simular uma busca semelhante ao site da Tabela FIPE via terminal, com:

* Consumo de API pública via HTTP.
* Tratamento de exceções.
* Validação de entradas do usuário.
* Exportação de resultados em arquivo.
* Estrutura profissional de projeto Java.

---

## 🚀 Tecnologias Utilizadas

* Java 17
* API pública FIPE (Parallelum)
* HTTP Client (java.net.http)
* Manipulação de arquivos (PrintWriter)
* Padrões de boas práticas (pacotes organizados, configuração externa)

---

## 🔦 Demonstração

O usuário interage via terminal:

1. Escolhe o tipo de veículo (carro, moto, caminhão)
2. Escolhe a marca (com validação)
3. Filtra o modelo por nome
4. Escolhe o modelo (com validação)
5. Lista todas as avaliações por ano disponíveis
6. Pode exportar o resultado final para `resultado.txt`

---

## 📦 Estrutura de Pastas

```bash
src/main/java
 └── br/com/alura/tabelafipe
       ├── model
       ├── service
       └── principal
```

---

## ⚙ Como executar

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repo.git
```

2. Compile e execute o projeto via IDE (IntelliJ, Eclipse...) ou terminal:

```bash
javac -d bin src/main/java/**/*.java
java -cp bin br.com.alura.tabelafipe.principal.Principal
```

---

## 📄 Arquivo de Configuração

As configurações (ex.: URL da API) estão centralizadas em:

```
src/main/resources/application.properties
```

---

## 🏆 Aprendizados

* Consumo de API com HTTP Client
* Conversão de JSON com `record`
* Validação de dados em tempo real
* Tratamento de exceções com mensagens amigáveis
* Organização modular de código
* Geração de arquivos em disco

---

## 📌 Créditos

Projeto baseado no curso **Java: Consumindo APIs, Gravando Arquivos e Lidando com Erros** - Alura.
