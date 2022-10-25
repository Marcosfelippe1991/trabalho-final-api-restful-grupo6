package br.com.residencia.ecommerce.controller;

import br.com.residencia.ecommerce.dto.PedidoDTO;
import br.com.residencia.ecommerce.entity.Pedido;
import br.com.residencia.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<Page<Pedido>> getAllPedidos(@PageableDefault(
			page = 0,
			size = 10,
			sort = "status",
			direction = Sort.Direction.ASC) Pageable pageable) {

		return pedidoService.getAllPedido(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getPedidoById(@PathVariable Integer id) {
		return pedidoService.getPedidoById(id);
	}

	@PostMapping
	public ResponseEntity<Pedido> savePedido(@RequestBody PedidoDTO pedidoDTO) {
		return pedidoService.savePedido(pedidoDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pedido> updatePedido(@RequestBody PedidoDTO pedidoDTO, @PathVariable Integer id){
		return pedidoService.updatePedido(pedidoDTO, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePedido(@PathVariable Integer id) {

		return pedidoService.deletePedido(id);
	}
}