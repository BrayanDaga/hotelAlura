package jdbc.controller;

import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {

	private ReservaDAO reservaDAO;

	public ReservasController() {
		ConnectionFactory factory = new ConnectionFactory();
		this.reservaDAO = new ReservaDAO(factory.recuperaConexion());
	}

	public void guardar(Reserva nuevaReserva) {
		reservaDAO.guardar(nuevaReserva);
	}

	public int eliminar(Integer id) {
		return this.reservaDAO.eliminarReservaConHuespedes(id);
	}

	public int modificar(String fechaEntrada, String fechaSalida, Integer valor, String formaPago, Integer id) {
		return this.reservaDAO.modificar(fechaEntrada, fechaSalida, valor, formaPago, id);
	}
	
	public List<Reserva> buscar() {
		return this.reservaDAO.buscar();
	}

	public List<Reserva> buscar(String textoABuscar) {
		int idABuscar = Integer.parseInt(textoABuscar);
		return this.reservaDAO.buscar(idABuscar);
	}
}
