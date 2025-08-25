package br.com.washryan.dao;

import br.com.washryan.domain.Cliente;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClienteMapDAO implements IClienteDAO {

    private Map<Long, Cliente> map = new HashMap<>();

    @Override
    public Boolean cadastrar(Cliente cliente) {
        if (map.containsKey(cliente.getCpf())) {
            return false; // já existe
        }
        map.put(cliente.getCpf(), cliente);
        return true;
    }

    @Override
    public void excluir(Long cpf) {
        map.remove(cpf);
    }

    @Override
    public void alterar(Cliente cliente) {
        map.put(cliente.getCpf(), cliente);
    }

    @Override
    public Cliente consultar(Long cpf) {
        return map.get(cpf);
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return map.values();
    }
}