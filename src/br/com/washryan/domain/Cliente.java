package br.com.washryan.domain;

import java.util.Objects;

public class Cliente {

    private String nome;
    private Long cpf;
    private String telefone;
    private String endereco;
    private Integer numero; 
    private String cidade;
    private String estado;

    public Cliente() {}

    public Cliente(String nome, String cpf, String telefone, String endereco, String numero, String cidade, String estado) {
        this.nome = nome != null ? nome.trim() : "";
        
        try {
            if (cpf == null || cpf.trim().isEmpty()) {
                throw new IllegalArgumentException("CPF não pode estar vazio");
            }
            // Remove caracteres não numéricos do CPF
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() < 10 || cpfLimpo.length() > 11) {
                throw new IllegalArgumentException("CPF deve ter 10 ou 11 dígitos");
            }
            this.cpf = Long.valueOf(cpfLimpo);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }
        
        this.telefone = telefone != null ? telefone.trim() : "";
        this.endereco = endereco != null ? endereco.trim() : "";
        
        // Tratamento seguro do número
        try {
            this.numero = numero != null && !numero.trim().isEmpty() ? Integer.valueOf(numero.trim()) : 0;
        } catch (NumberFormatException e) {
            this.numero = 0;
        }
        
        this.cidade = cidade != null ? cidade.trim() : "";
        this.estado = estado != null ? estado.trim() : "";
    }

    public boolean isCpfValido() {
        if (cpf == null) return false;
        String cpfStr = cpf.toString();
        return cpfStr.length() >= 10 && cpfStr.length() <= 11;
    }

    public String getCpfFormatado() {
        if (cpf == null) return "N/A";
        String cpfStr = String.format("%011d", cpf);
        return cpfStr.substring(0, 3) + "." + 
               cpfStr.substring(3, 6) + "." + 
               cpfStr.substring(6, 9) + "-" + 
               cpfStr.substring(9, 11);
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { 
        this.nome = nome != null ? nome.trim() : ""; 
    }

    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { 
        this.telefone = telefone != null ? telefone.trim() : ""; 
    }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { 
        this.endereco = endereco != null ? endereco.trim() : ""; 
    }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { 
        this.numero = numero != null ? numero : 0; 
    }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { 
        this.cidade = cidade != null ? cidade.trim() : ""; 
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { 
        this.estado = estado != null ? estado.trim() : ""; 
    }

    @Override
    public String toString() {
        return String.format("""
            ┌─────────────────────────────────────────┐
            │ DADOS DO CLIENTE                        │
            ├─────────────────────────────────────────┤
            │ Nome: %-33s │
            │ CPF: %-34s │
            │ Telefone: %-28s │
            │ Endereço: %-28s │
            │ Número: %-30s │
            │ Cidade: %-30s │
            │ Estado: %-30s │
            └─────────────────────────────────────────┘
            """, 
            nome, getCpfFormatado(), telefone, endereco, numero, cidade, estado);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
