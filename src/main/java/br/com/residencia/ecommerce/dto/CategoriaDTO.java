package br.com.residencia.ecommerce.dto;

import java.util.Set;

import br.com.residencia.ecommerce.entity.Produto;

import javax.validation.constraints.NotEmpty;

public class CategoriaDTO {

	private String nome;
	private String descricao;
	private Set<Produto> produtos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<Produto> produtos) {
		this.produtos = produtos;
	}



}
