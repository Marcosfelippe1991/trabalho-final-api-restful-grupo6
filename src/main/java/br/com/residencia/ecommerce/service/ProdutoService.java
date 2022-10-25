package br.com.residencia.ecommerce.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import br.com.residencia.ecommerce.dto.ProdutoDTO;
import br.com.residencia.ecommerce.validador.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.Produto;
import br.com.residencia.ecommerce.repository.ProdutoRepository;
import br.com.residencia.ecommerce.validador.ProdutoValidador;

@Service
public class ProdutoService {
	
	private final String MENSAGEM_DATA_HORA_LOCAL = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
	private ProdutoRepository produtoRepository;
	private ProdutoValidador produtoValidador;

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
		this.produtoValidador = new ProdutoValidador(this);
	}

	/**
	 * 	Método responsável por buscar todas produtos no Banco de Dados.
	 * @param pageable : Parâmetro utilizado pelo método Pageable para configurar a interface Web com paginação.
	 * @return Retorna todas as produtos do Banco de Dados.
	 */
	public ResponseEntity<Page<Produto>> getAllProdutos(Pageable pageable){
		Page<Produto> produtoPage = produtoRepository.findAll(pageable);
		try {
			var validacao = produtoValidador.validaPaginacaoDeProdutoNaoEncontrada(produtoPage);

			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não há conteúdo para ser paginado.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
	}
	
	/**
	 * Método responsável por buscar uma produto pelo identificador no Banco de Dados.
	 * @param id : Identificador da produto a ser encontrado no Banco de Dados.
	 * @return Retorna a produto encontrada no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Produto> getProdutoById(Integer id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		try {
			var validacao = produtoValidador.validaProdutoNaoEncontradaPorId(produtoOptional);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " --- Não foi encontrado uma produto com o id: " + id);
			}
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(produtoOptional.get());
	}
	
	/**
	 * Método responsável por salvar uma produto no Banco de Dados.
	 * @param produtoDTO produtoDTO com os dados à serem convertidos em Produto e salvos no banco.
	 * @return Salva uma entidade do tipo Produto no Banco de Dados.
	 */
	public ResponseEntity<Produto> saveProduto(ProdutoDTO produtoDTO) {
		Produto produto = new Produto();

		BeanUtils.copyProperties(produtoDTO, produto);
		try {
			produtoRepository.save(produto);
			Optional<Produto> produtoOptional = produtoRepository.findById(produto.getIdProduto());

			var validation = produtoValidador.validaProdutoNaoEncontradaPorId(produtoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao cadastrar uma nova produto.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.valueOf("Not Created")).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	/**
	 * Método responsável por atualizar uma produto no Banco de Dados.
	 * @param produtoDTO DTO com as informações para atualizar a entidade.
	 * @param id : Identificador da produto a ser atualizado no Banco de Dados.
	 * @return Atualiza uma entidade do tipo Produto no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Produto> updateProduto(ProdutoDTO produtoDTO, Integer id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);

		try {
			var validation = produtoValidador.validaProdutoNaoEncontradaPorId(produtoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não foi encontrado uma produto com o id: " + id);
			}

			BeanUtils.copyProperties(produtoDTO, produtoOptional.get());
			produtoRepository.save(produtoOptional.get());
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(produtoOptional.get());
	}
	
	/**
	 * Método responsável por deletar uma produto no Banco de Dados.
	 * @param id : Identificador da produto a ser deletado no Banco de Dados.
	 * @return Deleta uma entidade do tipo Produto no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Object> deleteProduto(Integer id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		try {
			var validation = produtoValidador.validaProdutoNaoEncontradaPorId(produtoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao tentar deletar uma produto.");
			}

			produtoRepository.deleteById(id);
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
