package br.com.residencia.ecommerce.controller;

import br.com.residencia.ecommerce.dto.ClienteDTO;
import br.com.residencia.ecommerce.entity.Cliente;
import br.com.residencia.ecommerce.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping
	public ResponseEntity<Page<Cliente>> getAllClientes(@PageableDefault(
			page = 0,
			size = 10,
			sort = "nomeCompleto",
			direction = Sort.Direction.ASC) Pageable pageable) {

		return clienteService.getAllClientes(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
		return clienteService.getClienteById(id);
	}

	@PostMapping
	public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteDTO clienteDTO) {
		return clienteService.saveCliente(clienteDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@RequestBody ClienteDTO clienteDTO,
													 @PathVariable Integer id){
		return clienteService.updateCliente(clienteDTO, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCliente(@PathVariable Integer id) {
		return clienteService.deleteCliente(id);
	}
}