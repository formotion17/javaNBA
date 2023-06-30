package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import lombok.Data;

/**
 * Clase para identificar el equipo
 * @author hatashi
 *
 */

@Data
public class ClaseEquipo {

	/**
	 * NOMBRE DEL EQUIPO
	 */
	private String nombre = "";
	
	/**
	 * TANTEO DEL EQUIPO
	 */
	private String tanteo = "";
	
	/**
	 * DERROTAS DEL EQUIPO EN EL MOMENTO ACTUAL
	 */
	private String derrotas = "";
	
	/**
	 * VICTORIAS DEL EQUIPO EN EL MOMENTO ACTUAL
	 */
	private String victorias = "";
	
	/**
	 * NOMBRE ABREVIADO DEL EQUIPO
	 */
	private String nombreAbreviado = "";
	
	/**
	 * PUNTOS CONSECUTIVOS REALIZADO POR EL EQUIPO EN EL PARTIDO
	 */
	private String puntosConsecutivos = "";
	
	/**
	 * TIEMPO QUE HA ESTADO EL EQUIPO SIN ANOTAR
	 */
	private Integer sinAnotar = 0;
	
	/**
	 * TIEMPO QUE HA ESTADO LIDER EL EQUIPO EN EL PARTIDO
	 */
	private Integer tiempoLider = 0;
	
	/**
	 * LISTA DE JUGADORES DEL EQUIPO
	 */
	private ArrayList<ClaseJugador> jugadores = new ArrayList<ClaseJugador>();
	
	/**
	 * TODAS LAS ESTADISTICAS DEL EQUIPO
	 */
	private ClaseFullBoxscore fullBoxscore = new ClaseFullBoxscore();
	
	/**
	 * TANTEO POR CUARTOS DEL EQUIPO
	 */
	private ClaseTanteoCuartos tanteoCuartos = new ClaseTanteoCuartos();
	
	/**
	 * ESTADISTICA AVANZADA DEL EQUIPO
	 */
	private ClaseEstadisticaAvanzada estadisticaAvanzada = new ClaseEstadisticaAvanzada();
	
	/**
	 * ESTADISTICA NORMAL DEL EQUIPO
	 */
	private ClaseEstadisticaNormal estadisticaNormal = new ClaseEstadisticaNormal();
	
	

	/**
	 * CALCUAMOS LOS MAXIMOS ANOTADORES, ASISTENTES Y REBOTEADORES DEL EQUIPO
	 */
	private int[] maxAnotadores = new int[3];
	private int[] maxAsistentes = new int[3];
	private int[] maxReboteadores = new int[3];

	/**
	 * Obtenemos los maximos anotadores, asistentes y reboteadores
	 */
	public void obtenerEstrellas() {
		maximaAnotacion();
		maximoAsistente();
		maximoReboteador();
	}

	/**
	 * Ordenamos y obtenemos los maximos anotadores
	 */
	private void maximaAnotacion() {
		ordenarAnotadores();
		maxAnotadores[0] = getJugadores().get(0).getPosicionTabla();
		maxAnotadores[1] = getJugadores().get(1).getPosicionTabla();
		maxAnotadores[2] = getJugadores().get(2).getPosicionTabla();
	}

	/**
	 * Ordenamos y obtenemos los maximos asistentes
	 */
	private void maximoAsistente() {
		ordenarAsistentes();
		maxAsistentes[0] = getJugadores().get(0).getPosicionTabla();
		maxAsistentes[1] = getJugadores().get(1).getPosicionTabla();
		maxAsistentes[2] = getJugadores().get(2).getPosicionTabla();
	}

	/**
	 * Ordenamos y obtenemos los maximos reboteadores
	 */
	private void maximoReboteador() {
		ordenarReboteadores();
		maxReboteadores[0] = getJugadores().get(0).getPosicionTabla();
		maxReboteadores[1] = getJugadores().get(1).getPosicionTabla();
		maxReboteadores[2] = getJugadores().get(2).getPosicionTabla();
	}

	/**
	 * Función de ordenar Anotadores
	 */
	private void ordenarAnotadores() {
		Comparator<ClaseJugador> comparator = new Comparator<ClaseJugador>() {
			@Override
			public int compare(ClaseJugador s1, ClaseJugador s2) {
				return s2.getTotalPartido().getPuntos().compareTo(s1.getTotalPartido().getPuntos());
			}
		};
		Collections.sort(jugadores, comparator);
	}

	/**
	 * Función de ordenar asistentes
	 */
	private void ordenarAsistentes() {
		Comparator<ClaseJugador> comparator = new Comparator<ClaseJugador>() {
			@Override
			public int compare(ClaseJugador s1, ClaseJugador s2) {
				return s2.getTotalPartido().getAsistencias().compareTo(s1.getTotalPartido().getAsistencias());
			}
		};
		Collections.sort(jugadores, comparator);
	}

	/**
	 * Función de ordenar reboteadores
	 */
	private void ordenarReboteadores() {
		Comparator<ClaseJugador> comparator = new Comparator<ClaseJugador>() {
			@Override
			public int compare(ClaseJugador s1, ClaseJugador s2) {
				return s2.getTotalPartido().getTotalRebotes().compareTo(s1.getTotalPartido().getTotalRebotes());
			}
		};
		Collections.sort(jugadores, comparator);
	}

}
