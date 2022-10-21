package br.com.residencia.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.residencia.ecommerce.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
