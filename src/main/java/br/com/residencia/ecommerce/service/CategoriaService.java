package br.com.residencia.ecommerce.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import br.com.residencia.ecommerce.dto.CategoriaDTO;
import br.com.residencia.ecommerce.validador.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.Categoria;
import br.com.residencia.ecommerce.repository.CategoriaRepository;
import br.com.residencia.ecommerce.validador.CategoriaValidador;

@Service
public class CategoriaService {
	
	private final String MENSAGEM_DATA_HORA_LOCAL = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
	private CategoriaRepository categoriaRepository;
	private CategoriaValidador categoriaValidador;

	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
		this.categoriaValidador = new CategoriaValidador(this);
	}

	/**
	 * 	Método responsável por buscar todas categorias no Banco de Dados.
	 * @param pageable : Parâmetro utilizado pelo método Pageable para configurar a interface Web com paginação.
	 * @return Retorna todas as categorias do Banco de Dados.
	 */
	public ResponseEntity<Page<Categoria>> getAllCategorias(Pageable pageable){
		Page<Categoria> categoriaPage = categoriaRepository.findAll(pageable);
		try {
			var validacao = categoriaValidador.validaPaginacaoDeCategoriaNaoEncontrada(categoriaPage);

			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não há conteúdo para ser paginado.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoriaPage);
	}
	
	/**
	 * Método responsável por buscar uma categoria pelo identificador no Banco de Dados.
	 * @param id : Identificador da categoria a ser encontrado no Banco de Dados.
	 * @return Retorna a categoria encontrada no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Categoria> getCategoriaById(Integer id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		try {
			var validacao = categoriaValidador.validaCategoriaNaoEncontradaPorId(categoriaOptional);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " --- Não foi encontrado uma categoria com o id: " + id);
			}
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.get());
	}
	
	/**
	 * Método responsável por salvar uma categoria no Banco de Dados.
	 * @param categoriaDTO categoriaDTO com os dados à serem convertidos em Categoria e salvos no banco.
	 * @return Salva uma entidade do tipo Categoria no Banco de Dados.
	 */
	public ResponseEntity<Categoria> saveCategoria(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();

		BeanUtils.copyProperties(categoriaDTO, categoria);
		try {
			categoriaRepository.save(categoria);
			Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoria.getIdCategoria());

			var validation = categoriaValidador.validaCategoriaNaoEncontradaPorId(categoriaOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao cadastrar uma nova categoria.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.valueOf("Not Created")).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
	}
	
	/**
	 * Método responsável por atualizar uma categoria no Banco de Dados.
	 * @param categoriaDTO DTO com as informações para atualizar a entidade.
	 * @param id : Identificador da categoria a ser atualizado no Banco de Dados.
	 * @return Atualiza uma entidade do tipo Categoria no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Categoria> updateCategoria(CategoriaDTO categoriaDTO, Integer id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

		try {
			var validation = categoriaValidador.validaCategoriaNaoEncontradaPorId(categoriaOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não foi encontrado uma categoria com o id: " + id);
			}

			BeanUtils.copyProperties(categoriaDTO, categoriaOptional.get());
			categoriaRepository.save(categoriaOptional.get());
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.get());
	}
	
	/**
	 * Método responsável por deletar uma categoria no Banco de Dados.
	 * @param id : Identificador da categoria a ser deletado no Banco de Dados.
	 * @return Deleta uma entidade do tipo Categoria no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Object> deleteCategoria(Integer id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		try {
			var validation = categoriaValidador.validaCategoriaNaoEncontradaPorId(categoriaOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao tentar deletar uma categoria.");
			}

			categoriaRepository.deleteById(id);
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
