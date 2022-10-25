package br.com.residencia.ecommerce.service;

import br.com.residencia.ecommerce.dto.PedidoDTO;
import br.com.residencia.ecommerce.entity.Pedido;
import br.com.residencia.ecommerce.repository.PedidoRepository;
import br.com.residencia.ecommerce.validador.PedidoValidador;
import br.com.residencia.ecommerce.validador.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PedidoService {

	private final String MENSAGEM_DATA_HORA_LOCAL = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
	private PedidoRepository pedidoRepository;
	private PedidoValidador pedidoValidador;

	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoValidador = new PedidoValidador(this);
	}

	/**
	 * 	Método responsável por buscar todas Clientes no Banco de Dados.
	 * @param pageable : Parâmetro utilizado pelo método Pageable para configurar a interface Web com paginação.
	 * @return Retorna todas as Clientes do Banco de Dados.
	 */
	public ResponseEntity<Page<Pedido>> getAllPedido(Pageable pageable){
		Page<Pedido> pedidoPage = pedidoRepository.findAll(pageable);
		try {
			var validacao = pedidoValidador.validaPaginacaoDePedidoNaoEncontrada(pedidoPage);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não há conteúdo para ser paginado.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(pedidoPage);
	}

	/**
	 * Método responsável por buscar uma Cliente pelo identificador no Banco de Dados.
	 * @param id : Identificador da Cliente a ser encontrado no Banco de Dados.
	 * @return Retorna a Cliente encontrada no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Pedido> getPedidoById(Integer id) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
		try {
			var validacao = pedidoValidador.validaPedidoNaoEncontradaPorId(pedidoOptional);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " --- Não foi encontrado um Cliente com o id: " + id);
			}
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(pedidoOptional.get());
	}

	/**
	 * Método responsável por salvar uma Cliente no Banco de Dados.
	 * @param pedidoDTO ClienteDTO com os dados à serem convertidos em Cliente e salvos no banco.
	 * @return Salva uma entidade do tipo Cliente no Banco de Dados.
	 */
	public ResponseEntity<Pedido> savePedido(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();

		BeanUtils.copyProperties(pedidoDTO, pedido);
		try {
			pedidoRepository.save(pedido);
			Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedido.getIdPedido());

			var validation = pedidoValidador.validaPedidoNaoEncontradaPorId(pedidoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao cadastrar uma nova Cliente.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.valueOf("Not Created")).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
	}

	/**
	 * Método responsável por atualizar uma Cliente no Banco de Dados.
	 * @param pedidoDTO DTO com as informações para atualizar a entidade.
	 * @param id : Identificador da Cliente a ser atualizado no Banco de Dados.
	 * @return Atualiza uma entidade do tipo Cliente no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Pedido> updatePedido(PedidoDTO pedidoDTO, Integer id) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

		try {
			var validation = pedidoValidador.validaPedidoNaoEncontradaPorId(pedidoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não foi encontrado uma Cliente com o id: " + id);
			}

			BeanUtils.copyProperties(pedidoDTO, pedidoOptional.get());
			pedidoRepository.save(pedidoOptional.get());
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(pedidoOptional.get());
	}

	/**
	 * Método responsável por deletar uma Cliente no Banco de Dados.
	 * @param id : Identificador da Cliente a ser deletado no Banco de Dados.
	 * @return Deleta uma entidade do tipo Cliente no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Object> deletePedido(Integer id) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
		try {
			var validation = pedidoValidador.validaPedidoNaoEncontradaPorId(pedidoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao tentar deletar uma Cliente.");
			}

			pedidoRepository.deleteById(id);
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
