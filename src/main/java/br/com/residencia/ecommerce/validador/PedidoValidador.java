package br.com.residencia.ecommerce.validador;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.residencia.ecommerce.entity.Pedido;
import br.com.residencia.ecommerce.service.PedidoService;

public class PedidoValidador {
	private PedidoService pedidoService;

	public PedidoValidador(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	/**
	 * Método responsável por validar se uma pedido foi encontrada no banco por id.
	 * @param pedidoOptional Optional de Pedido para validar se existe dados.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPedidoNaoEncontradaPorId(Optional<Pedido> pedidoOptional) {
		if(pedidoOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar a paginação de pedido.
	 * @param pedidoPage Page de pedido a ser consultado no banco.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPaginacaoDePedidoNaoEncontrada(Page pedidoPage) {
		if(pedidoPage.isEmpty()) {
			return false;
		}
		return true;
	}
}