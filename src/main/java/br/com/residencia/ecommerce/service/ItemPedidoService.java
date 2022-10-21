package br.com.residencia.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.ItemPedido;
import br.com.residencia.ecommerce.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public List<ItemPedido> getAllItemPedidos() {

		return itemPedidoRepository.findAll();
	}

	public ItemPedido getItemPedidoById(Integer id) {
		return itemPedidoRepository.findById(id).get();
	}

	public ItemPedido saveItemPedido(ItemPedido itemPedido) {
		return itemPedidoRepository.save(itemPedido);
	}

	public ItemPedido updateItemPedido(ItemPedido itemPedido, Integer id) {

		ItemPedido itemPedidoExistenteNoBanco = getItemPedidoById(id);

		itemPedidoExistenteNoBanco.setQuantidade(itemPedido.getQuantidade());
		itemPedidoExistenteNoBanco.setPrecoVenda(itemPedido.getPrecoVenda());
		itemPedidoExistenteNoBanco.setPercentualDesconto(itemPedido.getPercentualDesconto());
		itemPedidoExistenteNoBanco.setValorBruto(itemPedido.getValorBruto());
		itemPedidoExistenteNoBanco.setValorLiquido(itemPedido.getValorLiquido());

		return itemPedidoRepository.save(itemPedidoExistenteNoBanco);
	}

	public ItemPedido deleteItemPedido(Integer id) {
		itemPedidoRepository.deleteById(id);
		return getItemPedidoById(id);
	}
}
