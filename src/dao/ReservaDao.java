package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;

public class ReservaDao {
	private Connection con;

	public ReservaDao(Connection con) {
		this.con = con;
	}
	
	public void guardar(Reserva reserva) {
		try {
			String query = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_de_pago) "
					+ " VALUES (?,?,?,?)";
			final PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			try(statement){
				ejecutarRegistro(reserva, statement);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void ejecutarRegistro(Reserva reserva, PreparedStatement statement) {
		try {
			statement.setObject(1, reserva.getFechaIngreso());
			statement.setObject(2, reserva.getFechaEgreso());
			statement.setString(3, reserva.getValor());
			statement.setString(4, reserva.getFormaPago());
			
			statement.execute();
			
			final ResultSet resultSet = statement.getGeneratedKeys();
			
			try(resultSet) {
				while(resultSet.next()) {
					reserva.setId( resultSet.getInt(1));
					System.out.println(String.format("Fue insertado el producto: %s", reserva));
				}	
			}	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> listar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			final String query = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas";
			final PreparedStatement statement = con.prepareStatement(query);
			try(statement) {
				statement.execute();
				transformarResultado(statement, reservas);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Reserva buscarPorID(String id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			final String query = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas WHERE id=?";
			final PreparedStatement statement = con.prepareStatement(query);
			try(statement) {
				statement.setString(1, id);
				statement.execute();
				transformarResultado(statement, reservas);
			}
			if(reservas.size() == 0) {
				return null;
			}
			return reservas.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void actualizar(
			Integer id,
			LocalDate fechaIngreso,
			LocalDate fechaEgreso,
			String valor,
			String formaPago
			) {
		final String query = "UPDATE reservas SET "
					+ "fecha_entrada=?, fecha_salida=?, valor=?, forma_de_pago=? WHERE id=?";
		try(PreparedStatement statement = con.prepareStatement(query)) {
				statement.setObject(1,Date.valueOf(fechaIngreso));
				statement.setObject(2, Date.valueOf(fechaEgreso));
				statement.setString(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				
				statement.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public void eliminar (Integer id) {
		try{
			final String query = "DELETE reservas WHERE id = ?";
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS=0");
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, id);
			statement.execute();
			state.execute("SET FOREIGN_KEY_CHECKS=1");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void transformarResultado(PreparedStatement statement, List<Reserva> reservas) {
		try(ResultSet resultSet = statement.getResultSet()) {
			while(resultSet.next()) {
				LocalDate fechaIngreso = resultSet.getDate("fecha_entrada").toLocalDate().plusDays(1);
				LocalDate fechaEgreso = resultSet.getDate("fecha_salida").toLocalDate().plusDays(1);
				Reserva reserva = new Reserva(
						resultSet.getInt("id"),
						fechaIngreso,
						fechaEgreso,
						resultSet.getString("valor"),
						resultSet.getString("forma_de_pago")
						);
				reservas.add(reserva);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
}
