package br.com.residencia.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import br.com.residencia.ecommerce.dto.ConsultaCepDTO;
import br.com.residencia.ecommerce.dto.EnderecoDTO;
import br.com.residencia.ecommerce.entity.Endereco;
import br.com.residencia.ecommerce.service.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	EnderecoService enderecoService;
	
	@GetMapping
	public ResponseEntity<Page<Endereco>> getAllEnderecos(@PageableDefault(
			page = 0, 
			size = 10, 
			sort = "cep", 
			direction = Sort.Direction.ASC)
			Pageable pageable) {

		return enderecoService.getAllEnderecos(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Endereco> getEnderecoById(@PathVariable Integer id) {
		return enderecoService.getEnderecoById(id);
	}
	
	@PostMapping
	public ResponseEntity<Endereco> saveEndereco(@RequestBody EnderecoDTO enderecoDTO) {
		return enderecoService.saveEndereco(enderecoDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Endereco> updateEndereco(@RequestBody EnderecoDTO enderecoDTO,
			@PathVariable Integer id){
		return enderecoService.updateEndereco(enderecoDTO, id);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteEndereco(@PathVariable Integer id) {
		return enderecoService.deleteEndereco(id);
	}
	
	@GetMapping("/consulta-cep/{cep}")
	public ResponseEntity<ConsultaCepDTO> saveEnderecoFromApi(@PathVariable String cep){
		ConsultaCepDTO consultaCepDTO = enderecoService.consultaCepApiExterna(cep);
		
		
			return new ResponseEntity<>(consultaCepDTO, HttpStatus.OK);
	
		
	}
}