package br.com.residencia.ecommerce.service;

import br.com.residencia.ecommerce.dto.ClienteDTO;
import br.com.residencia.ecommerce.repository.ClienteRepository;
import br.com.residencia.ecommerce.entity.Cliente;
import br.com.residencia.ecommerce.validador.ClienteValidador;
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
public class ClienteService {

	private final String MENSAGEM_DATA_HORA_LOCAL = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
	private ClienteRepository clienteRepository;
	private ClienteValidador clienteValidador;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
		this.clienteValidador = new ClienteValidador(this);
	}

	/**
	 * 	Método responsável por buscar todas Clientes no Banco de Dados.
	 * @param pageable : Parâmetro utilizado pelo método Pageable para configurar a interface Web com paginação.
	 * @return Retorna todas as Clientes do Banco de Dados.
	 */
	public ResponseEntity<Page<Cliente>> getAllClientes(Pageable pageable){
		Page<Cliente> clientePage = clienteRepository.findAll(pageable);
		try {
			var validacao = clienteValidador.validaPaginacaoDeClienteNaoEncontrado(clientePage);

			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não há conteúdo para ser paginado.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientePage);
	}

	/**
	 * Método responsável por buscar uma Cliente pelo identificador no Banco de Dados.
	 * @param id : Identificador da Cliente a ser encontrado no Banco de Dados.
	 * @return Retorna a Cliente encontrada no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Cliente> getClienteById(Integer id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		try {
			var validacao = clienteValidador.validaClienteNaoEncontradaPorId(clienteOptional);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " --- Não foi encontrado um Cliente com o id: " + id);
			}
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
	}

	/**
	 * Método responsável por salvar uma Cliente no Banco de Dados.
	 * @param clienteDTO ClienteDTO com os dados à serem convertidos em Cliente e salvos no banco.
	 * @return Salva uma entidade do tipo Cliente no Banco de Dados.
	 */
	public ResponseEntity<Cliente> saveCliente(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();

		BeanUtils.copyProperties(clienteDTO, cliente);
		try {
			clienteRepository.save(cliente);
			Optional<Cliente> clienteOptional = clienteRepository.findById(cliente.getIdCliente());

			var validation = clienteValidador.validaClienteNaoEncontradaPorId(clienteOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao cadastrar uma nova Cliente.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.valueOf("Not Created")).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
	}

	/**
	 * Método responsável por atualizar uma Cliente no Banco de Dados.
	 * @param clienteDTO DTO com as informações para atualizar a entidade.
	 * @param id : Identificador da Cliente a ser atualizado no Banco de Dados.
	 * @return Atualiza uma entidade do tipo Cliente no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Cliente> updateCliente(ClienteDTO clienteDTO, Integer id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);

		try {
			var validation = clienteValidador.validaClienteNaoEncontradaPorId(clienteOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não foi encontrado uma Cliente com o id: " + id);
			}

			BeanUtils.copyProperties(clienteDTO, clienteOptional.get());
			clienteRepository.save(clienteOptional.get());
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
	}

	/**
	 * Método responsável por deletar uma Cliente no Banco de Dados.
	 * @param id : Identificador da Cliente a ser deletado no Banco de Dados.
	 * @return Deleta uma entidade do tipo Cliente no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Object> deleteCliente(Integer id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		try {
			var validation = clienteValidador.validaClienteNaoEncontradaPorId(clienteOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao tentar deletar uma Cliente.");
			}

			clienteRepository.deleteById(id);
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
