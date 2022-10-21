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

import br.com.residencia.ecommerce.entity.Categoria;
import br.com.residencia.ecommerce.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAllCategorias(){
		return new ResponseEntity<>(categoriaService.getAllCategorias(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getCategoriaById(@PathVariable Integer id) {
		Categoria categoria = categoriaService.getCategoriaById(id);
		if(null != categoria)
			return new ResponseEntity<>(categoria,
					HttpStatus.OK);
		else
			return new ResponseEntity<>(categoria,
					HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
		return new ResponseEntity<>(categoriaService.saveCategoria(categoria),
				HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> updateCategoria(@RequestBody Categoria categoria, 
			@PathVariable Integer id){
		return new ResponseEntity<>(categoriaService.updateCategoria(categoria, id),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> deleteCategoria(@PathVariable Integer id) {
		Categoria categoria = categoriaService.getCategoriaById(id);
		if(null == categoria)
			return new ResponseEntity<>(categoria,
					HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(categoriaService.deleteCategoria(id),
					HttpStatus.OK);
	}
}
