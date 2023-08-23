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

import modelo.Huesped;

public class HuespedDao {
	private final Connection con;
	
	public HuespedDao(Connection con) {
		this.con = con;
	}
	
	public void guardar(Huesped huesped) {
		try {
			String query = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva) "
					+ "VALUES (?,?,?,?,?,?)";
			final PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			try(statement){
				ejecutarRegistro(huesped, statement);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public List<Huesped> listar() {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			final String query = "SELECT id, nombre, apellido,fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";
			final PreparedStatement statement = con.prepareStatement(query);
			try(statement) {
				statement.execute();
				transformarResultado(statement, huespedes);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Huesped buscarPorID(String id) {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			final String query = "SELECT id, nombre, apellido,fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?";
			final PreparedStatement statement = con.prepareStatement(query);
			try(statement) {
				statement.setString(1, id);
				statement.execute();
				transformarResultado(statement, huespedes);
			}
			if(huespedes.size() == 0) {
				return null;
			}
			return huespedes.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
		final String query = "UPDATE huespedes SET "
					+ "nombre=?, apellido=?,fecha_nacimiento=?, nacionalidad=?, telefono=?, id_reserva=?"
					+ " WHERE id=?";
		try(PreparedStatement statement = con.prepareStatement(query)) {
				statement.setObject(1, nombre);
				statement.setObject(2, apellido);
				statement.setObject(3, Date.valueOf(fechaNacimiento));
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, id_reserva);
				statement.setInt(7, id);
				
				statement.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public void eliminar (int id) {
		try{
			final String query = "DELETE FROM huespedes WHERE id = ?";
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

	private void ejecutarRegistro(Huesped huesped, PreparedStatement statement) {
		try {
			statement.setString(1, huesped.getNombre());
			statement.setString(2, huesped.getApellido());
			statement.setObject(3, huesped.getFechaNacimineto());
			statement.setString(4, huesped.getNacionalidad());
			statement.setString(5, huesped.getTelefono());
			statement.setInt(6, huesped.getIdReserva());
			
			statement.execute();
			
			final ResultSet resultSet = statement.getGeneratedKeys();
			try(resultSet) {
				while (resultSet.next()) {
					huesped.setId(resultSet.getInt(1));
					System.out.println( String.format("Se creo el Huesped: %s", huesped) );
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}	
	}

	private void transformarResultado(PreparedStatement statement, List<Huesped> huespedes) {
		try(ResultSet resultSet = statement.getResultSet()) {
			while(resultSet.next()) {
				LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate();
				
				Huesped huesped = new Huesped(
						resultSet.getInt("id"),
						resultSet.getString("nombre"),
						resultSet.getString("apellido"),
						fechaNacimiento,
						resultSet.getString("nacionalidad"),
						resultSet.getString("telefono"),
						resultSet.getInt("id_reserva")
						);
				huespedes.add(huesped);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}

}
