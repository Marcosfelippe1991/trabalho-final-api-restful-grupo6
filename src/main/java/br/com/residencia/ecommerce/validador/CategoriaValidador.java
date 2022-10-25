package br.com.residencia.ecommerce.validador;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.Categoria;
import br.com.residencia.ecommerce.service.CategoriaService;

@Service
public class CategoriaValidador {
	private CategoriaService categoriaService;

	public CategoriaValidador(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	/**
	 * Método responsável por validar se uma categoria foi encontrada no banco por id.
	 * @param categoriaOptional Optional de Categoria para validar se existe dados.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaCategoriaNaoEncontradaPorId(Optional<Categoria> categoriaOptional) {
		if(categoriaOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar a paginação de categoria.
	 * @param categoriaPage Page de categoria a ser consultado no banco.
	 * @return Retorna um booleano de acordo com o que foi encontrado no banco.
	 */
	public boolean validaPaginacaoDeCategoriaNaoEncontrada(Page categoriaPage) {
		if(categoriaPage.isEmpty()) {
			return false;
		}
		return true;
	}
}