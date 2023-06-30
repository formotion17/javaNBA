package Modelo;

import java.util.ArrayList;
import lombok.Data;

@Data
public class ClaseJugadorTiros {
	
	/**
	 * Temporada en la que son los tiros
	 */
	private String season="";
	
	/**
	 * Si el partido es de PlayOff
	 */
	private boolean playoff=false;
	
	/**
	 * Si juega en casa o fuera
	 */
	private boolean casa=false;
	
	/**
	 * Equi con el que juega
	 */
	private String equipoCon="";
	
	/**
	 * Equipo contra el que juega
	 */
	private String equipoContra="";
	
	/**
	 * Dia del partido
	 */
	private String dia="";
	
	/**
	 * Mes del partido
	 */
	private String mes="";
	
	/**
	 * Año del partido
	 */
	private String year="";
	
	/**
	 * Lista de tiros del partido
	 */
	private ArrayList<ClaseTiros> listaTiros = new ArrayList<ClaseTiros>();
	

}
