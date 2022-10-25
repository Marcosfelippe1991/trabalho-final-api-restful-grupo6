package br.com.residencia.ecommerce.validador;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.residencia.ecommerce.entity.Produto;
import br.com.residencia.ecommerce.service.ProdutoService;

public class ProdutoValidador {
	private ProdutoService produtoService;

	public ProdutoValidador(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	/**
	 * Método responsável por validar se uma produto foi encontrada no banco por id.
	 * @param produtoOptional Optional de Produto para validar se existe dados.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaProdutoNaoEncontradaPorId(Optional<Produto> produtoOptional) {
		if(produtoOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar a paginação de produto.
	 * @param produtoPage Page de produto a ser consultado no banco.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPaginacaoDeProdutoNaoEncontrada(Page produtoPage) {
		if(produtoPage.isEmpty()) {
			return false;
		}
		return true;
	}
}