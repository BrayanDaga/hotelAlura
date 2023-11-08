package jdbc.controller;

import java.util.List;

import jdbc.dao.HuespedesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;

public class HuespedesController {
	private HuespedesDAO huespedesDAO;
	
	public HuespedesController() {
        ConnectionFactory factory = new ConnectionFactory();
        this.huespedesDAO =  new HuespedesDAO(factory.recuperaConexion());
        }

	public void guardar(Huesped huesped) {
		huespedesDAO.guardar(huesped);
	}
	
	public List<Huesped> buscar(){
		return huespedesDAO.buscar();
	}

}
