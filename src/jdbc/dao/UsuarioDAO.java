package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Usuario;

public class UsuarioDAO {

    private Connection con;

    public UsuarioDAO(Connection con) {
        this.con = con;
    }

    public Usuario login(String nombre, String contraseña) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contraseña = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, contraseña);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Usuario usuario = new Usuario(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("contraseña")
                );
                return usuario;
            } else {
                return null; // Manejar el caso en el que no se encuentra un usuario con el ID dado
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el usuario por ID", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                // No cierres la conexión aquí, ya que no la abriste en este método
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
