package br.com.residencia.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.residencia.ecommerce.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
