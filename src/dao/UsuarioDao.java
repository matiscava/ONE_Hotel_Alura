package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {
	private final Connection con;
	
	public UsuarioDao(Connection con) {
		this.con = con;
	}
	
	public boolean validarUsuario(String nombre, String contrasenha) {

		try {
			final String query = "SELECT * FROM usuarios WHERE nombre=? AND contrasenha =?";
			final PreparedStatement state = con.prepareStatement(query);
			try(state) {
				state.setString(1, nombre);
				state.setString(2, contrasenha);
				ResultSet resultSet = state.executeQuery();
				return resultSet.next();				
			}

				
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} 
	}

}
