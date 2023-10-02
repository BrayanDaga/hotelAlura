package jdbc.controller;

import java.sql.Connection;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {

	private ReservaDAO reservaDAO;
	public ReservasController() {
        ConnectionFactory factory = new ConnectionFactory();
        this.reservaDAO =  new ReservaDAO(factory.recuperaConexion());
        }

	public void guardar(Reserva nuevaReserva) {
		reservaDAO.guardar(nuevaReserva);
	}

}
