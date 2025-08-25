# 📝 Projeto 1 - Cadastro de Cliente (CRUD em Memória)

Este projeto foi desenvolvido como parte do curso **Desenvolvimento Full Stack Java – EBAC**.  
O objetivo é implementar um **sistema simples de cadastro de clientes** utilizando **orientação a objetos** e o padrão **DAO** (Data Access Object), armazenando os dados **em memória**.

---

## 🚀 Tecnologias utilizadas
- **Java 17+**
- **Paradigma Orientado a Objetos (POO)**
- **Collections API (Map)**
- **DAO Pattern**
- **Scanner (entrada via console)**

---

## 📂 Estrutura do projeto
```

src/
└── br/com/washryan/
├── dao/
│    ├── IClienteDAO.java        # Interface do DAO
│    └── ClienteMapDAO.java      # Implementação em memória usando HashMap
├── domain/
│    └── Cliente.java            # Classe de domínio (modelo Cliente)
└── App.java                     # Classe principal com menu interativo

````

---

## 🛠️ Funcionalidades
✔️ Cadastrar cliente  
✔️ Consultar cliente por CPF  
✔️ Alterar dados do cliente  
✔️ Excluir cliente  
✔️ Listar todos os clientes  

---

## 💻 Como executar

1. Clone o repositório e entre no diretório do projeto:
   ```bash
   git clone https://github.com/washryan/cadastro-cliente.git
   cd cadastro-cliente/src
````

2. Compile o projeto:

   ```bash
   javac br/com/washryan/**/*.java
   ```

3. Execute a aplicação:

   ```bash
   java br.com.washryan.App
   ```

---

## ✅ Exemplo de uso

```
=== CADASTRO DE CLIENTES ===
1 - Cadastrar cliente
2 - Consultar cliente
3 - Excluir cliente
4 - Alterar cliente
5 - Listar clientes
0 - Sair
Opção: 1

--- Cadastro de Cliente ---
Nome:
CPF:
Telefone:
Endereço:
Número:
Cidade:
Estado:
Cliente cadastrado com sucesso!
```

---

## 📌 Próximos passos

* Implementar persistência em **banco de dados** (ex.: MySQL ou Postgres)
* Criar camada de **serviço** separada da camada DAO
* Evoluir para **API REST** usando **Spring Boot**

---
