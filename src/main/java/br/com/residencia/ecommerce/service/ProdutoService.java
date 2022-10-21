package br.com.residencia.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.residencia.ecommerce.entity.Produto;
import br.com.residencia.ecommerce.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	public List<Produto> getAllProdutos() {

		return produtoRepository.findAll();
	}

	public Produto getProdutoById(Integer id) {
		return produtoRepository.findById(id).get();
	}

	public Produto saveProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto updateProduto(Produto produto, Integer id) {

		Produto produtoExistenteNoBanco = getProdutoById(id);

		produtoExistenteNoBanco.setNome(produto.getNome());
		produtoExistenteNoBanco.setDescricao(produto.getDescricao());
		produtoExistenteNoBanco.setQtdEstoque(produto.getQtdEstoque());
		produtoExistenteNoBanco.setDataCadastro(produto.getDataCadastro());
		produtoExistenteNoBanco.setValorUnitario(produto.getValorUnitario());
		produtoExistenteNoBanco.setImagem(produto.getImagem());

		return produtoRepository.save(produtoExistenteNoBanco);

	}

	public Produto deleteProduto(Integer id) {
		produtoRepository.deleteById(id);
		return getProdutoById(id);
	}
}
