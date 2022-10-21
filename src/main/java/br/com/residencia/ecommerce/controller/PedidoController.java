package br.com.residencia.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.residencia.ecommerce.entity.Pedido;
import br.com.residencia.ecommerce.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<Pedido>> getAllPedidos() {
		return new ResponseEntity<>(pedidoService.getAllPedidos(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getPedidoById(@PathVariable Integer id) {
		Pedido pedido = pedidoService.getPedidoById(id);
		if (null != pedido)
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		else
			return new ResponseEntity<>(pedido, HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<Pedido> savePedido(@RequestBody Pedido pedido) {
		return new ResponseEntity<>(pedidoService.savePedido(pedido), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido, @PathVariable Integer id) {
		return new ResponseEntity<>(pedidoService.updatePedido(pedido, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Pedido> deletePedido(@PathVariable Integer id) {
		Pedido pedido = pedidoService.getPedidoById(id);
		if (null == pedido)
			return new ResponseEntity<>(pedido, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(pedidoService.deletePedido(id), HttpStatus.OK);
	}
}
