package br.com.residencia.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.residencia.ecommerce.dto.ItemPedidoDTO;
import br.com.residencia.ecommerce.entity.ItemPedido;
import br.com.residencia.ecommerce.service.ItemPedidoService;

@RestController
@RequestMapping("/itemPedidos")
public class ItemPedidoController {

	@Autowired
	ItemPedidoService itemPedidoService;
	
	@GetMapping
	public ResponseEntity<Page<ItemPedido>> getAllItemPedidos(@PageableDefault(
			page = 0, 
			size = 10, 
			sort = "quantidade", 
			direction = Sort.Direction.ASC)
			Pageable pageable) {

		return itemPedidoService.getAllItemPedidos(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ItemPedido> getItemPedidoById(@PathVariable Integer id) {
		return itemPedidoService.getItemPedidoById(id);
	}
	
	@PostMapping
	public ResponseEntity<ItemPedido> saveItemPedido(@RequestBody ItemPedidoDTO itemPedidoDTO) {
		return itemPedidoService.saveItemPedido(itemPedidoDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ItemPedido> updateItemPedido(@RequestBody ItemPedidoDTO itemPedidoDTO,
			@PathVariable Integer id){
		return itemPedidoService.updateItemPedido(itemPedidoDTO, id);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteItemPedido(@PathVariable Integer id) {
		return itemPedidoService.deleteItemPedido(id);
	}
}