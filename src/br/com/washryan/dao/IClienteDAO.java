package br.com.washryan.dao;

import br.com.washryan.domain.Cliente;
import java.util.Collection;

public interface IClienteDAO {

    Boolean cadastrar(Cliente cliente);

    void excluir(Long cpf);

    void alterar(Cliente cliente);

    Cliente consultar(Long cpf);

    Collection<Cliente> buscarTodos();
}