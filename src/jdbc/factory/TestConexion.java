package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;



public class TestConexion {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		System.out.println("Cerrando la conexión");
	}
}
