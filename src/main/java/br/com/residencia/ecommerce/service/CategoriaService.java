package br.com.residencia.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.Categoria;
import br.com.residencia.ecommerce.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
public List<Categoria> getAllCategorias(){
		
		return categoriaRepository.findAll();
	}
	
	public Categoria getCategoriaById(Integer id) {
		return categoriaRepository.findById(id).get();
	}
	
	public Categoria saveCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria updateCategoria(Categoria categoria, Integer id) {

		Categoria categoriaExistenteNoBanco = getCategoriaById(id);

		categoriaExistenteNoBanco.setNome(categoria.getNome());
		categoriaExistenteNoBanco.setDescricao(categoria.getDescricao());

		return categoriaRepository.save(categoriaExistenteNoBanco);

	}
	
	public Categoria deleteCategoria(Integer id) {
		categoriaRepository.deleteById(id);
		return getCategoriaById(id);
	}
}
