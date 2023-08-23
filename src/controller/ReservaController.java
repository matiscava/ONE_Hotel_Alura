package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.ReservaDao;
import factory.ConexionBaseDeDatos;
import modelo.Reserva;

public class ReservaController {
	private ReservaDao reservaDao;
	public ReservaController() {
		Connection factory = new ConexionBaseDeDatos().recuperarConexion();
		this.reservaDao = new ReservaDao(factory);
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDao.guardar(reserva);
	}
	
	public List<Reserva> listar() {
		return this.reservaDao.listar();
	}
	
	public Reserva buscarPorId(String id) {
		return this.reservaDao.buscarPorID(id);
	}
	public void actualizar( Integer id, LocalDate fechaIngreso, LocalDate fechaEgreso, String valor, String formaPago ) {
		this.reservaDao.actualizar(id, fechaIngreso, fechaEgreso, valor, formaPago);
	}
	
	public void eliminar (int id) {
		this.reservaDao.eliminar(id);
	}
}
