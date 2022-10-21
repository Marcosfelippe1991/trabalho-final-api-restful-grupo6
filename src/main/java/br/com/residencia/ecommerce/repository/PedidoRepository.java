package br.com.residencia.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.residencia.ecommerce.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
