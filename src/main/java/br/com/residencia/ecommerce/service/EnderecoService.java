package br.com.residencia.ecommerce.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.residencia.ecommerce.dto.ConsultaCepDTO;
import br.com.residencia.ecommerce.dto.EnderecoDTO;
import br.com.residencia.ecommerce.entity.Endereco;
import br.com.residencia.ecommerce.repository.EnderecoRepository;
import br.com.residencia.ecommerce.validador.EnderecoValidador;
import br.com.residencia.ecommerce.validador.ValidationException;

@Service
public class EnderecoService {
	
	private final String MENSAGEM_DATA_HORA_LOCAL = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
	private EnderecoRepository enderecoRepository;
	private EnderecoValidador enderecoValidador;

	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
		this.enderecoValidador = new EnderecoValidador(this);
	}

	/**
	 * 	Método responsável por buscar todas enderecos no Banco de Dados.
	 * @param pageable : Parâmetro utilizado pelo método Pageable para configurar a interface Web com paginação.
	 * @return Retorna todas as enderecos do Banco de Dados.
	 */
	public ResponseEntity<Page<Endereco>> getAllEnderecos(Pageable pageable){
		Page<Endereco> enderecoPage = enderecoRepository.findAll(pageable);
		try {
			var validacao = enderecoValidador.validaPaginacaoDeEnderecoNaoEncontrada(enderecoPage);

			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não há conteúdo para ser paginado.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(enderecoPage);
	}
	
	/**
	 * Método responsável por buscar uma endereco pelo identificador no Banco de Dados.
	 * @param id : Identificador da endereco a ser encontrado no Banco de Dados.
	 * @return Retorna a endereco encontrada no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Endereco> getEnderecoById(Integer id) {
		Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
		try {
			var validacao = enderecoValidador.validaEnderecoNaoEncontradaPorId(enderecoOptional);
			if(!validacao) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " --- Não foi encontrado uma endereco com o id: " + id);
			}
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(enderecoOptional.get());
	}
	
	/**
	 * Método responsável por salvar uma endereco no Banco de Dados.
	 * @param enderecoDTO enderecoDTO com os dados à serem convertidos em Endereco e salvos no banco.
	 * @return Salva uma entidade do tipo Endereco no Banco de Dados.
	 */
	public ResponseEntity<Endereco> saveEndereco(EnderecoDTO enderecoDTO) {
		Endereco endereco = new Endereco();

		BeanUtils.copyProperties(enderecoDTO, endereco);
		try {
			enderecoRepository.save(endereco);
			Optional<Endereco> enderecoOptional = enderecoRepository.findById(endereco.getIdEndereco());

			var validation = enderecoValidador.validaEnderecoNaoEncontradaPorId(enderecoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao cadastrar uma nova endereco.");
			}

		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.valueOf("Not Created")).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
	}
	
	/**
	 * Método responsável por atualizar uma endereco no Banco de Dados.
	 * @param enderecoDTO DTO com as informações para atualizar a entidade.
	 * @param id : Identificador da endereco a ser atualizado no Banco de Dados.
	 * @return Atualiza uma entidade do tipo Endereco no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Endereco> updateEndereco(EnderecoDTO enderecoDTO, Integer id) {
		Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);

		try {
			var validation = enderecoValidador.validaEnderecoNaoEncontradaPorId(enderecoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Não foi encontrado uma endereco com o id: " + id);
			}

			BeanUtils.copyProperties(enderecoDTO, enderecoOptional.get());
			enderecoRepository.save(enderecoOptional.get());
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(enderecoOptional.get());
	}
	
	/**
	 * Método responsável por deletar uma endereco no Banco de Dados.
	 * @param id : Identificador da endereco a ser deletado no Banco de Dados.
	 * @return Deleta uma entidade do tipo Endereco no Banco de Dados pelo id informado.
	 */
	public ResponseEntity<Object> deleteEndereco(Integer id) {
		Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
		try {
			var validation = enderecoValidador.validaEnderecoNaoEncontradaPorId(enderecoOptional);
			if(!validation) {
				throw new ValidationException(MENSAGEM_DATA_HORA_LOCAL + " Erro ao tentar deletar uma endereco.");
			}

			enderecoRepository.deleteById(id);
		}catch (ValidationException validation) {
			System.out.println(validation.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Método responsável por consultar um CEP através de uma API externa.
	 * @param cep : Identificador do CEP a ser encontrado por uma API externa.
	 * @return Retorna um endereço completo através do CEP informado.
	 */
	public ConsultaCepDTO consultaCepApiExterna(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://cdn.apicep.com/file/apicep/{cep}.json";
		Map<String,String> params = new HashMap<String,String>();
		params.put("cep", cep);
		
		ConsultaCepDTO consultaCepDTO = restTemplate.getForObject(uri, ConsultaCepDTO.class, params);
		
		return consultaCepDTO;
	}
}
