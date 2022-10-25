package br.com.residencia.ecommerce.validador;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.residencia.ecommerce.entity.Cliente;
import br.com.residencia.ecommerce.service.ClienteService;

public class ClienteValidador {
	private ClienteService clienteService;

	public ClienteValidador(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	/**
	 * Método responsável por validar se uma cliente foi encontrada no banco por id.
	 * @param clienteOptional Optional de Cliente para validar se existe dados.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaClienteNaoEncontradaPorId(Optional<Cliente> clienteOptional) {
		if(clienteOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar a paginação de cliente.
	 * @param clientePage Page de cliente a ser consultado no banco.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPaginacaoDeClienteNaoEncontrado(Page clientePage) {
		if(clientePage.isEmpty()) {
			return false;
		}
		return true;
	}
}