package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JOptionPane;

import dao.UsuarioDao;
import factory.ConexionBaseDeDatos;
import views.Login;
import views.MenuUsuario;

public class UsuarioController implements ActionListener {
	private UsuarioDao usuarioDao;
	private Login vista;
	
	public UsuarioController(Login vista) {
		Connection factory = new ConexionBaseDeDatos().recuperarConexion();
		this.usuarioDao = new UsuarioDao(factory);
		this.vista = vista;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombre = vista.getNombre();
		String contrasena = vista.getContrasenha();
		
		if(usuarioDao.validarUsuario(nombre, contrasena)) {
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
			vista.dispose();
		} else {
			JOptionPane.showMessageDialog(vista, "Usuario o contraseña no validos");
		}
	}
}
