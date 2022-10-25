package br.com.residencia.ecommerce.validador;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.residencia.ecommerce.entity.Endereco;
import br.com.residencia.ecommerce.service.EnderecoService;

public class EnderecoValidador {
	private EnderecoService enderecoService;

	public EnderecoValidador(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	/**
	 * Método responsável por validar se uma endereco foi encontrada no banco por id.
	 * @param enderecoOptional Optional de Endereco para validar se existe dados.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaEnderecoNaoEncontradaPorId(Optional<Endereco> enderecoOptional) {
		if(enderecoOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar a paginação de endereco.
	 * @param enderecoPage Page de endereco a ser consultado no banco.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPaginacaoDeEnderecoNaoEncontrada(Page enderecoPage) {
		if(enderecoPage.isEmpty()) {
			return false;
		}
		return true;
	}
}