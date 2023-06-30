package Modelo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ClaseJugadorDatos {

	/**
	 * Id del jugador
	 */
	private String idJugador="";
	
	/**
	 * Lista tiros del jugador
	 */
	private ArrayList<ClaseJugadorTiros> listaTiros =new ArrayList<ClaseJugadorTiros>();
	
	
}
