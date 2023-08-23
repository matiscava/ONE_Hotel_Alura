package test;

import java.sql.Connection;
import java.sql.SQLException;

import factory.ConexionBaseDeDatos;


public class TextConexion {


    public static void main(String[] args) throws SQLException {
    	Connection con = new ConexionBaseDeDatos().recuperarConexion();
    	
        System.out.println("Se conecto correctamenta a la base de datos");

        con.close();
        System.out.println("Se cerró la conexión de la base de datos");

    }
    
}
