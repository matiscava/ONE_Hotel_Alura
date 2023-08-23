package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.HuespedDao;
import factory.ConexionBaseDeDatos;
import modelo.Huesped;

public class HuespedController {
	
	private HuespedDao huespedDao;
	
	public HuespedController() {
		Connection con = new ConexionBaseDeDatos().recuperarConexion();
		this.huespedDao = new HuespedDao(con);
	}
	
	public void guardar(Huesped huesped) {
		this.huespedDao.guardar(huesped);
	}
	
	public List<Huesped> listar() {
		return this.huespedDao.listar();
	}
	
	public Huesped buscarPorId(String id) {
		return this.huespedDao.buscarPorID(id);
	}
	
	public void actualizar(
			Integer id,
			String nombre,
			String apellido,
			LocalDate fechaNacimiento,
			String nacionalidad,
			String telefono,
			Integer id_reserva
			) {
		this.huespedDao.actualizar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, id_reserva);
	}
	
	public void eliminar (int id) {
		this.huespedDao.eliminar(id);
	}
}
