package br.com.residencia.ecommerce.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import br.com.residencia.ecommerce.dto.ItemPedidoDTO;
import br.com.residencia.ecommerce.validador.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.ItemPedido;
import br.com.residencia.ecommerce.repository.ItemPedidoRepository;
import br.com.residencia.ecommerce.validador.ItemPedidoValidador;

@Service
public class ItemPedidoService {
	
	private final String MENSAGEM_DATA_HORA_LOCAL = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
	private ItemPedidoRepository itemPedidoRepository;
	private ItemPedidoValidador itemPedidoValidador;

	public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
		this.itemPedidoRepository = itemPedidoRepository;
		this.itemPedidoValidador = new ItemPedidoValidador(this);
	}

	/**
	 * 	Método responsável por buscar todas itemPedidos no Banco de Dados.
	 * @param pageable : Parâmetro utilizado pelo método Pageable para configurar a interface Web com paginação.
	 * @return Retorna todas as itemPedidos do Banco de Dados.
	 */
	public ResponseEntity<Page<ItemPedido>> getAllItemPedidos(Pageable pageable){
		Page<ItemPedido> itemPedidoPage = itemPedidoRepository.findAll(pageable);
		try {
			var validacao = itemPedidoValidador.validaPaginacaoDeItemPedidoNaoEncontrada(itemPedidoPage);

			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não há conteúdo para ser paginado.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(itemPedidoPage);
	}
	
	/**
	 * Método responsável por buscar uma itemPedido pelo identificador no Banco de Dados.
	 * @param id : Identificador da itemPedido a ser encontrado no Banco de Dados.
	 * @return Retorna a itemPedido encontrada no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<ItemPedido> getItemPedidoById(Integer id) {
		Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findById(id);
		try {
			var validacao = itemPedidoValidador.validaItemPedidoNaoEncontradaPorId(itemPedidoOptional);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " --- Não foi encontrado uma itemPedido com o id: " + id);
			}
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(itemPedidoOptional.get());
	}
	
	/**
	 * Método responsável por salvar uma itemPedido no Banco de Dados.
	 * @param itemPedidoDTO itemPedidoDTO com os dados à serem convertidos em ItemPedido e salvos no banco.
	 * @return Salva uma entidade do tipo ItemPedido no Banco de Dados.
	 */
	public ResponseEntity<ItemPedido> saveItemPedido(ItemPedidoDTO itemPedidoDTO) {
		ItemPedido itemPedido = new ItemPedido();

		BeanUtils.copyProperties(itemPedidoDTO, itemPedido);
		try {
			itemPedidoRepository.save(itemPedido);
			Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findById(itemPedido.getIdItemPedido());

			var validation = itemPedidoValidador.validaItemPedidoNaoEncontradaPorId(itemPedidoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao cadastrar uma nova itemPedido.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.valueOf("Not Created")).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(itemPedido);
	}
	
	/**
	 * Método responsável por atualizar uma itemPedido no Banco de Dados.
	 * @param itemPedidoDTO DTO com as informações para atualizar a entidade.
	 * @param id : Identificador da itemPedido a ser atualizado no Banco de Dados.
	 * @return Atualiza uma entidade do tipo ItemPedido no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<ItemPedido> updateItemPedido(ItemPedidoDTO itemPedidoDTO, Integer id) {
		Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findById(id);

		try {
			var validation = itemPedidoValidador.validaItemPedidoNaoEncontradaPorId(itemPedidoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não foi encontrado uma itemPedido com o id: " + id);
			}

			BeanUtils.copyProperties(itemPedidoDTO, itemPedidoOptional.get());
			itemPedidoRepository.save(itemPedidoOptional.get());
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(itemPedidoOptional.get());
	}
	
	/**
	 * Método responsável por deletar uma itemPedido no Banco de Dados.
	 * @param id : Identificador da itemPedido a ser deletado no Banco de Dados.
	 * @return Deleta uma entidade do tipo ItemPedido no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Object> deleteItemPedido(Integer id) {
		Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findById(id);
		try {
			var validation = itemPedidoValidador.validaItemPedidoNaoEncontradaPorId(itemPedidoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao tentar deletar uma itemPedido.");
			}

			itemPedidoRepository.deleteById(id);
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
