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

import br.com.residencia.ecommerce.dto.ProdutoDTO;
import br.com.residencia.ecommerce.entity.Produto;
import br.com.residencia.ecommerce.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<Page<Produto>> getAllProdutos(@PageableDefault(
			page = 0, 
			size = 10, 
			sort = "nome", 
			direction = Sort.Direction.ASC)
			Pageable pageable) {

		return produtoService.getAllProdutos(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
		return produtoService.getProdutoById(id);
	}
	
	@PostMapping
	public ResponseEntity<Produto> saveProduto(@RequestBody ProdutoDTO produtoDTO) {
		return produtoService.saveProduto(produtoDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> updateProduto(@RequestBody ProdutoDTO produtoDTO,
			@PathVariable Integer id){
		return produtoService.updateProduto(produtoDTO, id);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduto(@PathVariable Integer id) {
		return produtoService.deleteProduto(id);
	}
}