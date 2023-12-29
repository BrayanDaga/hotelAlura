package jdbc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	private Connection con;
	private HuespedesDAO huespedesDAO;
	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Reserva nuevaReserva) {

		try {
			PreparedStatement statement;
			statement = con.prepareStatement("INSERT INTO reservas "
					+ "(fechaEntrada, fechaSalida, valor, formaPago,estado)" + " VALUES (?, ?, ?, ?,1)",
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

	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fechaEntrada , fechaSalida, valor, formaPago FROM reservas WHERE estado = 1";
			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();
				transformarResultado(reservas, pstm);
			}
			return reservas;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> buscar(int idABuscar) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fechaEntrada , fechaSalida, valor, formaPago FROM reservas WHERE estado = 1 AND id=?";
			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.setInt(1, idABuscar);
				pstm.execute();
				transformarResultado(reservas, pstm);
			}
			return reservas;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void transformarResultado(List<Reserva> reservas, PreparedStatement pstm) {
		try (ResultSet resultSet = pstm.getResultSet()) {
			while (resultSet.next()) {
				Reserva reserva = new Reserva();
				reserva.setId(resultSet.getInt("id"));
				reserva.setFechaEntrada(resultSet.getDate("fechaEntrada"));
				reserva.setFechaSalida(resultSet.getDate("fechaSalida"));
				reserva.setValor(resultSet.getBigDecimal("valor"));
				reserva.setFormaPago(resultSet.getString("formaPago"));
				reservas.add(reserva);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public int editar(Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago, Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE id = ?");
			try (statement) {
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setBigDecimal(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminarReservaConHuespedes(Integer idReserva) {
		try {
			con.setAutoCommit(false); // Desactiva el modo de autocommit para iniciar la transacción
			huespedesDAO = new HuespedesDAO(con);
			// Elimina los huéspedes relacionados a la reserva
			huespedesDAO.eliminarPorReserva(idReserva);

			// Luego elimina la reserva
			final PreparedStatement statement = con.prepareStatement("UPDATE reservas SET estado = 0 WHERE id = ?");

			try (statement) {
				statement.setInt(1, idReserva);
				statement.execute();
				int updateCount = statement.getUpdateCount();

				if (updateCount > 0) {
					con.commit(); // Confirma la transacción si la eliminación tuvo éxito
				} else {
					con.rollback(); // Revierte la transacción si la eliminación falla
				}

				return updateCount;
			}
		} catch (Exception e) {
			try {
				con.rollback(); // Revierte la transacción en caso de excepción
			} catch (SQLException rollbackException) {
				throw new RuntimeException("Error al revertir la transacción", rollbackException);
			}
			throw new RuntimeException("Error al eliminar la reserva con huéspedes", e);
		} finally {
			try {
				con.setAutoCommit(true); // Restaura el modo de autocommit al final
			} catch (SQLException e) {
				throw new RuntimeException("Error al restaurar el modo de autocommit", e);
			}
		}
	}

	public int modificar(Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago, Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE id = ?");
			try (statement) {
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setBigDecimal(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
