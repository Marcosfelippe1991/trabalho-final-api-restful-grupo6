package br.com.residencia.ecommerce.validador;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.residencia.ecommerce.entity.ItemPedido;
import br.com.residencia.ecommerce.service.ItemPedidoService;

public class ItemPedidoValidador {
	private ItemPedidoService itemPedidoService;

	public ItemPedidoValidador(ItemPedidoService itemPedidoService) {
		this.itemPedidoService = itemPedidoService;
	}

	/**
	 * Método responsável por validar se uma itemPedido foi encontrada no banco por id.
	 * @param itemPedidoOptional Optional de ItemPedido para validar se existe dados.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaItemPedidoNaoEncontradaPorId(Optional<ItemPedido> itemPedidoOptional) {
		if(itemPedidoOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar a paginação de itemPedido.
	 * @param itemPedidoPage Page de itemPedido a ser consultado no banco.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPaginacaoDeItemPedidoNaoEncontrada(Page itemPedidoPage) {
		if(itemPedidoPage.isEmpty()) {
			return false;
		}
		return true;
	}
}
