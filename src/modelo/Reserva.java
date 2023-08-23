package modelo;

import java.time.LocalDate;

public class Reserva {
	private Integer id;
	private LocalDate fechaIngreso;
	private LocalDate fechaEgreso;
	private String valor;
	private String formaPago;
	
	public Reserva(LocalDate fechaIngreso, LocalDate fechaEgreso, String valor, String formaPago) {
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = fechaEgreso;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Reserva(int id, LocalDate fechaInreso, LocalDate fechaEgreso, String valor, String formaPago) {
		this.id = id;
		this.fechaIngreso = fechaInreso;
		this.fechaEgreso = fechaEgreso;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDate getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(LocalDate fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public void setId(int id) {
		this.id = id;		
	}
	
	
	
	
}
