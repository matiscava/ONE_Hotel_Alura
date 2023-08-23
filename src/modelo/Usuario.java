package modelo;

public class Usuario {
	private String nombre;
	private String contrasenha;
	
	public Usuario(String nombre, String contrasenha) {
		this.nombre = nombre;
		this.contrasenha = contrasenha;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasenha() {
		return contrasenha;
	}
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	
	
	
}
