package jdbc.controller;


import jdbc.dao.UsuarioDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Usuario;

public class UsuarioController {
	private UsuarioDAO usuarioDAO;

public UsuarioController() {
	ConnectionFactory factory =  new ConnectionFactory();
	this.usuarioDAO = new UsuarioDAO(factory.recuperaConexion());
}

	public Usuario login(String nombre, String contraseña) {
		return this.usuarioDAO.login(nombre, contraseña);
	}



}
