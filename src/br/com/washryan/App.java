package br.com.washryan;

import br.com.washryan.dao.ClienteMapDAO;
import br.com.washryan.dao.IClienteDAO;
import br.com.washryan.domain.Cliente;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static IClienteDAO clienteDAO = new ClienteMapDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🚀 Bem-vindo ao Sistema de Cadastro de Clientes!");
        System.out.println("📋 Desenvolvido para o Módulo 14 - Backend EBAC");
        
        int opcao = 0;
        do {
            try {
                opcao = menu();
                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> consultarCliente();
                    case 3 -> excluirCliente();
                    case 4 -> alterarCliente();
                    case 5 -> listarClientes();
                    case 0 -> {
                        System.out.println("👋 Encerrando aplicação...");
                        System.out.println("✅ Obrigado por usar nosso sistema!");
                    }
                    default -> System.out.println("❌ Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Por favor, digite apenas números!");
                scanner.nextLine(); // Limpa o buffer
            } catch (Exception e) {
                System.out.println("❌ Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static int menu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏢 SISTEMA DE CADASTRO DE CLIENTES");
        System.out.println("=".repeat(50));
        System.out.println("1️⃣  - Cadastrar cliente");
        System.out.println("2️⃣  - Consultar cliente");
        System.out.println("3️⃣  - Excluir cliente");
        System.out.println("4️⃣  - Alterar cliente");
        System.out.println("5️⃣  - Listar todos os clientes");
        System.out.println("0️⃣  - Sair do sistema");
        System.out.println("=".repeat(50));
        System.out.print("👉 Digite sua opção: ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Retorna valor inválido para tratar no switch
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("📝 CADASTRO DE NOVO CLIENTE");
        System.out.println("─".repeat(40));
        
        try {
            System.out.print("👤 Nome completo: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("❌ Nome não pode estar vazio!");
                return;
            }

            System.out.print("🆔 CPF (apenas números ou com pontuação): ");
            String cpf = scanner.nextLine().trim();
            if (cpf.isEmpty()) {
                System.out.println("❌ CPF não pode estar vazio!");
                return;
            }

            System.out.print("📞 Telefone: ");
            String telefone = scanner.nextLine().trim();

            System.out.print("🏠 Endereço: ");
            String endereco = scanner.nextLine().trim();

            System.out.print("🔢 Número: ");
            String numero = scanner.nextLine().trim();

            System.out.print("🏙️ Cidade: ");
            String cidade = scanner.nextLine().trim();

            System.out.print("🗺️ Estado: ");
            String estado = scanner.nextLine().trim();

            Cliente cliente = new Cliente(nome, cpf, telefone, endereco, numero, cidade, estado);

            if (clienteDAO.cadastrar(cliente)) {
                System.out.println("✅ Cliente cadastrado com sucesso!");
                System.out.println("📋 Dados salvos:");
                System.out.println(cliente);
            } else {
                System.out.println("❌ Já existe um cliente com este CPF!");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro nos dados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro inesperado ao cadastrar: " + e.getMessage());
        }
    }

    private static void consultarCliente() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("🔍 CONSULTAR CLIENTE");
        System.out.println("─".repeat(40));
        
        try {
            System.out.print("🆔 Digite o CPF: ");
            String cpfStr = scanner.nextLine().trim();
            
            if (cpfStr.isEmpty()) {
                System.out.println("❌ CPF não pode estar vazio!");
                return;
            }
            
            // Remove formatação do CPF
            String cpfLimpo = cpfStr.replaceAll("[^0-9]", "");
            Long cpf = Long.valueOf(cpfLimpo);
            Cliente cliente = clienteDAO.consultar(cpf);
            
            if (cliente != null) {
                System.out.println("✅ Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.out.println("❌ Cliente não encontrado!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ CPF inválido! Digite apenas números.");
        }
    }

    private static void excluirCliente() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("🗑️ EXCLUIR CLIENTE");
        System.out.println("─".repeat(40));
        
        try {
            System.out.print("🆔 Digite o CPF do cliente a excluir: ");
            String cpfStr = scanner.nextLine().trim();
            
            if (cpfStr.isEmpty()) {
                System.out.println("❌ CPF não pode estar vazio!");
                return;
            }
            
            String cpfLimpo = cpfStr.replaceAll("[^0-9]", "");
            Long cpf = Long.valueOf(cpfLimpo);
            Cliente cliente = clienteDAO.consultar(cpf);
            
            if (cliente != null) {
                System.out.println("📋 Cliente encontrado:");
                System.out.println(cliente);
                System.out.print("⚠️ Tem certeza que deseja excluir? (S/N): ");
                String confirmacao = scanner.nextLine().trim().toUpperCase();
                
                if ("S".equals(confirmacao) || "SIM".equals(confirmacao)) {
                    clienteDAO.excluir(cpf);
                    System.out.println("✅ Cliente excluído com sucesso!");
                } else {
                    System.out.println("❌ Operação cancelada.");
                }
            } else {
                System.out.println("❌ Cliente não encontrado!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ CPF inválido! Digite apenas números.");
        }
    }

    private static void alterarCliente() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("✏️ ALTERAR DADOS DO CLIENTE");
        System.out.println("─".repeat(40));
        
        try {
            System.out.print("🆔 Digite o CPF do cliente: ");
            String cpfStr = scanner.nextLine().trim();
            
            if (cpfStr.isEmpty()) {
                System.out.println("❌ CPF não pode estar vazio!");
                return;
            }
            
            String cpfLimpo = cpfStr.replaceAll("[^0-9]", "");
            Long cpf = Long.valueOf(cpfLimpo);
            Cliente cliente = clienteDAO.consultar(cpf);

            if (cliente != null) {
                System.out.println("📋 Dados atuais:");
                System.out.println(cliente);
                System.out.println("\n💡 Digite os novos dados (ENTER para manter o atual):");

                System.out.print("👤 Nome [" + cliente.getNome() + "]: ");
                String nome = scanner.nextLine().trim();
                if (!nome.isEmpty()) cliente.setNome(nome);

                System.out.print("📞 Telefone [" + cliente.getTelefone() + "]: ");
                String telefone = scanner.nextLine().trim();
                if (!telefone.isEmpty()) cliente.setTelefone(telefone);

                System.out.print("🏠 Endereço [" + cliente.getEndereco() + "]: ");
                String endereco = scanner.nextLine().trim();
                if (!endereco.isEmpty()) cliente.setEndereco(endereco);

                System.out.print("🔢 Número [" + cliente.getNumero() + "]: ");
                String numero = scanner.nextLine().trim();
                if (!numero.isEmpty()) {
                    try {
                        cliente.setNumero(Integer.valueOf(numero));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Número inválido, mantendo o anterior.");
                    }
                }

                System.out.print("🏙️ Cidade [" + cliente.getCidade() + "]: ");
                String cidade = scanner.nextLine().trim();
                if (!cidade.isEmpty()) cliente.setCidade(cidade);

                System.out.print("🗺️ Estado [" + cliente.getEstado() + "]: ");
                String estado = scanner.nextLine().trim();
                if (!estado.isEmpty()) cliente.setEstado(estado);

                clienteDAO.alterar(cliente);
                System.out.println("✅ Cliente alterado com sucesso!");
                System.out.println("📋 Novos dados:");
                System.out.println(cliente);
            } else {
                System.out.println("❌ Cliente não encontrado!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ CPF inválido! Digite apenas números.");
        }
    }

    private static void listarClientes() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("📋 LISTA DE TODOS OS CLIENTES");
        System.out.println("─".repeat(50));
        
        Collection<Cliente> clientes = clienteDAO.buscarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("📭 Nenhum cliente cadastrado ainda.");
            System.out.println("💡 Use a opção 1 para cadastrar o primeiro cliente!");
        } else {
            System.out.println("📊 Total de clientes: " + clientes.size());
            System.out.println();
            
            int contador = 1;
            for (Cliente cliente : clientes) {
                System.out.println("👤 Cliente #" + contador++);
                System.out.println(cliente);
                System.out.println();
            }
        }
    }
}
