package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConexionBaseDeDatos {
	public DataSource dataSource;
	
	public ConexionBaseDeDatos() {
		ComboPooledDataSource  comboPool = new ComboPooledDataSource();
		comboPool.setJdbcUrl("jdbc:mysql://localhost:3306/hotel_alura_db?useTimeZone=true&serverTimeZone=UTC");
		comboPool.setUser("root");
		comboPool.setPassword("");
		comboPool.setMaxPoolSize(10);
				
		this.dataSource = comboPool;
	}
	
	public Connection recuperarConexion(){
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
