package jdbc.controller;

import java.sql.Date;
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
	
	public int eliminar(Integer id) {
		return this.huespedesDAO.eliminar(id);
	}

	public List<Huesped> buscar(String textoABuscar) {
		return huespedesDAO.buscar(textoABuscar);

	}

	public int modificar(Integer id, String nombre, String apellido, Date fechaDeNacimiento, String nacionalidad,
			String telefono) {
		
		return huespedesDAO.modificar(id,nombre,apellido,fechaDeNacimiento,nacionalidad,telefono);
	}

}
