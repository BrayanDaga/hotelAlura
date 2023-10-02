package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Reserva nuevaReserva) {
		
		try {
			PreparedStatement statement;
			statement = con.prepareStatement(
					"INSERT INTO reservas " + "(fechaEntrada, fechaSalida, valor, formaPago)" + " VALUES (?, ?, ?, ? )",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				statement.setDate(1, nuevaReserva.getFechaEntrada());
				statement.setDate(2, nuevaReserva.getFechaSalida());
				statement.setBigDecimal(3, nuevaReserva.getValor());
				statement.setString(4, nuevaReserva.getFormaPago());
				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {
						nuevaReserva.setId(resultSet.getInt(1));
						System.out.println(String.format("Fue insertado la reserva: %s", nuevaReserva));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
