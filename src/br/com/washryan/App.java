package br.com.washryan;

import br.com.washryan.dao.ClienteMapDAO;
import br.com.washryan.dao.IClienteDAO;
import br.com.washryan.domain.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;

public class App extends JFrame {
    
    private static IClienteDAO clienteDAO = new ClienteMapDAO();
    
    // Componentes da interface
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco, txtNumero, txtCidade, txtEstado;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrar, btnConsultar, btnAlterar, btnExcluir, btnLimpar;
    
    public App() {
        initComponents();
        atualizarTabela();
    }
    
    private void initComponents() {
        setTitle("Sistema de Cadastro de Clientes - EBAC Módulo 14");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        
        // Painel de formulário
        JPanel painelFormulario = criarPainelFormulario();
        
        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        
        // Painel da tabela
        JPanel painelTabela = criarPainelTabela();
        
        // Adicionando componentes
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        // Configurações da janela
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNome = new JTextField(20);
        painel.add(txtNome, gbc);
        
        // CPF
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCpf = new JTextField(15);
        painel.add(txtCpf, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTelefone = new JTextField(20);
        painel.add(txtTelefone, gbc);
        
        // Endereço
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEndereco = new JTextField(15);
        painel.add(txtEndereco, gbc);
        
        // Número
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNumero = new JTextField(20);
        painel.add(txtNumero, gbc);
        
        // Cidade
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCidade = new JTextField(15);
        painel.add(txtCidade, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEstado = new JTextField(20);
        painel.add(txtEstado, gbc);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout());
        
        btnCadastrar = new JButton("Cadastrar");
        btnConsultar = new JButton("Consultar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        
        // Adicionando listeners
        btnCadastrar.addActionListener(e -> cadastrarCliente());
        btnConsultar.addActionListener(e -> consultarCliente());
        btnAlterar.addActionListener(e -> alterarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnLimpar.addActionListener(e -> limparCampos());
        
        painel.add(btnCadastrar);
        painel.add(btnConsultar);
        painel.add(btnAlterar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Clientes Cadastrados"));
        
        String[] colunas = {"Nome", "CPF", "Telefone", "Endereço", "Número", "Cidade", "Estado"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    preencherCamposComSelecao();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private void cadastrarCliente() {
        try {
            if (validarCampos()) {
                String cpfLimpo = txtCpf.getText().replaceAll("[^0-9]", "");
                
                Cliente cliente = new Cliente(
                    txtNome.getText().trim(),
                    cpfLimpo,
                    txtTelefone.getText().trim(),
                    txtEndereco.getText().trim(),
                    txtNumero.getText().trim(),
                    txtCidade.getText().trim(),
                    txtEstado.getText().trim()
                );
                
                if (clienteDAO.cadastrar(cliente)) {
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Já existe um cliente com este CPF!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarCliente() {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CPF para consultar!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            Cliente cliente = clienteDAO.consultar(Long.valueOf(cpfLimpo));
            
            if (cliente != null) {
                preencherCampos(cliente);
                JOptionPane.showMessageDialog(this, "Cliente encontrado!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "CPF inválido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void alterarCliente() {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CPF do cliente a alterar!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            Cliente cliente = clienteDAO.consultar(Long.valueOf(cpfLimpo));
            
            if (cliente != null) {
                if (validarCampos()) {
                    cliente.setNome(txtNome.getText().trim());
                    cliente.setTelefone(txtTelefone.getText().trim());
                    cliente.setEndereco(txtEndereco.getText().trim());
                    cliente.setNumero(Integer.valueOf(txtNumero.getText().trim()));
                    cliente.setCidade(txtCidade.getText().trim());
                    cliente.setEstado(txtEstado.getText().trim());
                    
                    clienteDAO.alterar(cliente);
                    JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "CPF ou número inválido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirCliente() {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CPF do cliente a excluir!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            Cliente cliente = clienteDAO.consultar(Long.valueOf(cpfLimpo));
            
            if (cliente != null) {
                int confirmacao = JOptionPane.showConfirmDialog(this, 
                    "Tem certeza que deseja excluir o cliente " + cliente.getNome() + "?", 
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    clienteDAO.excluir(Long.valueOf(cpfLimpo));
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    atualizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "CPF inválido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtNumero.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
    }
    
    private void preencherCampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf().toString());
        txtTelefone.setText(cliente.getTelefone());
        txtEndereco.setText(cliente.getEndereco());
        txtNumero.setText(cliente.getNumero().toString());
        txtCidade.setText(cliente.getCidade());
        txtEstado.setText(cliente.getEstado());
    }
    
    private void preencherCamposComSelecao() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            txtNome.setText((String) modeloTabela.getValueAt(linhaSelecionada, 0));
            txtCpf.setText((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            txtTelefone.setText((String) modeloTabela.getValueAt(linhaSelecionada, 2));
            txtEndereco.setText((String) modeloTabela.getValueAt(linhaSelecionada, 3));
            txtNumero.setText((String) modeloTabela.getValueAt(linhaSelecionada, 4));
            txtCidade.setText((String) modeloTabela.getValueAt(linhaSelecionada, 5));
            txtEstado.setText((String) modeloTabela.getValueAt(linhaSelecionada, 6));
        }
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!", 
                "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtNome.requestFocus();
            return false;
        }
        
        if (txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF é obrigatório!", 
                "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtCpf.requestFocus();
            return false;
        }
        
        try {
            if (!txtNumero.getText().trim().isEmpty()) {
                Integer.valueOf(txtNumero.getText().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número deve ser um valor numérico!", 
                "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtNumero.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        Collection<Cliente> clientes = clienteDAO.buscarTodos();
        
        for (Cliente cliente : clientes) {
            Object[] linha = {
                cliente.getNome(),
                formatarCpf(cliente.getCpf().toString()),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getNumero(),
                cliente.getCidade(),
                cliente.getEstado()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private String formatarCpf(String cpf) {
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
                   cpf.substring(6, 9) + "-" + cpf.substring(9);
        }
        return cpf;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new App().setVisible(true);
        });
    }
}
