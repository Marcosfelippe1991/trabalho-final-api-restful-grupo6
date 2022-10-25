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

import br.com.residencia.ecommerce.dto.CategoriaDTO;
import br.com.residencia.ecommerce.entity.Categoria;
import br.com.residencia.ecommerce.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<Page<Categoria>> getAllCategorias(@PageableDefault(
			page = 0, 
			size = 10, 
			sort = "nome", 
			direction = Sort.Direction.ASC)
			Pageable pageable) {

		return categoriaService.getAllCategorias(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getCategoriaById(@PathVariable Integer id) {
		return categoriaService.getCategoriaById(id);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> saveCategoria(@RequestBody CategoriaDTO categoriaDTO) {
		return categoriaService.saveCategoria(categoriaDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> updateCategoria(@RequestBody CategoriaDTO categoriaDTO,
			@PathVariable Integer id){
		return categoriaService.updateCategoria(categoriaDTO, id);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCategoria(@PathVariable Integer id) {
		return categoriaService.deleteCategoria(id);
	}
}