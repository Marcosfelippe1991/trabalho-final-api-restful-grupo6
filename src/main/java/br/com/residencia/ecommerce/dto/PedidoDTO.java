package br.com.residencia.ecommerce.dto;

import java.time.Instant;
import java.util.Set;

import br.com.residencia.ecommerce.entity.Cliente;
import br.com.residencia.ecommerce.entity.ItemPedido;

import javax.validation.constraints.NotBlank;

public class PedidoDTO {

	private Instant dataPedido;
	private Instant dataEntrega;
	private Instant dataEnvio;
	private String status;
	private Double valorTotal;
	private Set<ItemPedido> itemPedidos;
	private Cliente cliente;

	public Instant getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Instant dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Instant getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Instant dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Instant getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Instant dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}



	public Set<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(Set<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
