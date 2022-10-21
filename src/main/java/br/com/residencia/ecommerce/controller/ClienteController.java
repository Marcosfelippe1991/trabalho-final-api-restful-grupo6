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

import br.com.residencia.ecommerce.entity.Cliente;
import br.com.residencia.ecommerce.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<Cliente>> getAllClientes() {
		return new ResponseEntity<>(clienteService.getAllClientes(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
		Cliente cliente = clienteService.getClienteById(id);
		if (null != cliente)
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		else
			return new ResponseEntity<>(cliente, HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
		return new ResponseEntity<>(clienteService.saveCliente(cliente), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {
		return new ResponseEntity<>(clienteService.updateCliente(cliente, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> deleteCliente(@PathVariable Integer id) {
		Cliente cliente = clienteService.getClienteById(id);
		if (null == cliente)
			return new ResponseEntity<>(cliente, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(clienteService.deleteCliente(id), HttpStatus.OK);
	}
}
