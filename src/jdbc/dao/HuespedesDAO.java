package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Huesped;

public class HuespedesDAO {
	private Connection con;

	public HuespedesDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huesped huesped) {

		try {
			PreparedStatement statement;
			statement = con.prepareStatement("INSERT INTO huespedes "
					+ "(nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, reserva_id)"
					+ " VALUES (?, ?, ?, ?, ? , ?  )", Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setDate(3, huesped.getFechaDeNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getReserva_id());
				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));
						System.out.println(String.format("Fue insertado el huesped: %s", huesped));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huesped> buscar() {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			String sql = "SELECT id, nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, reserva_id FROM huespedes";
			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();
				transformarResultado(huespedes, pstm);
			}
			return huespedes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void transformarResultado(List<Huesped> huespedes, PreparedStatement pstm) {
		try (ResultSet resultSet = pstm.getResultSet()) {
			while (resultSet.next()) {
				Huesped huesped = new Huesped();
				huesped.setId(resultSet.getInt("id"));
				huesped.setNombre(resultSet.getString("nombre"));
				huesped.setFechaDeNacimiento(resultSet.getDate("fechaDeNacimiento"));
				huesped.setApellido(resultSet.getString("apellido"));
				huesped.setNacionalidad(resultSet.getString("nacionalidad"));
				huesped.setTelefono(resultSet.getString("telefono"));
				huesped.setReserva_id(resultSet.getInt("reserva_id"));
				huespedes.add(huesped);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public int eliminar(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huesped> buscar(String textoABuscar) {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			String sql = "SELECT id, nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, reserva_id FROM huespedes WHERE apellido LIKE ?";
			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.setString(1, textoABuscar+"%");
				pstm.execute();
				transformarResultado(huespedes, pstm);
			}
			return huespedes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
