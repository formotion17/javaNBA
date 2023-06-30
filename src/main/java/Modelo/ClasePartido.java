package Modelo;

import lombok.Data;

@Data
public class ClasePartido {

	/**
	 * Equipo local del partido
	 */
	private ClaseEquipo equipoLocal = new ClaseEquipo();
	
	/**
	 * Equipo visitante del partido
	 */
	private ClaseEquipo equipoVisitante = new ClaseEquipo();

	/**
	 * Asistencia del partido
	 */
	private Integer asistencia = 0;
	
	/**
	 * Tiempo empatados los equipos
	 */
	private Integer tiempoEmpate = 0;
	
	/**
	 * Tiempo que el equipo local esta sin anotar
	 */
	private Integer localSinAnotar = 0;
	
	/**
	 * Tiempo quel equipo visitante esta sin anotar
	 */
	private Integer visitanteSinAnotar = 0;
	
	/**
	 * Tiempo que el equipo local esta ganando
	 */
	private Integer tiempoLocalGanando = 0;
	
	/**
	 * Tiempo que el equipo visitante esta ganando
	 */
	private Integer tiempoVisitanteGanando = 0;

	/**
	 * Dia del partido
	 */
	private String dia = "";
	
	/**
	 * Mes del partido
	 */
	private String mes = "";
	
	/**
	 * Hora del partido
	 */
	private String hora = "";
	
	/**
	 * A�o del partido
	 */
	private String year = "";
	
	/**
	 * Numero de empates en el partido
	 */
	private String empates = "";
	
	/**
	 * Estadio en el que se juega el partido
	 */
	private String estadio = "";
	
	/**
	 * Ubicacion del partido
	 */
	private String ubicacion = "";
	
	/**
	 * N�mero de cambios de lider en el aprtido
	 */
	private String cambiosLider = "";
	
	/**
	 * Puntos consecutivos del equipo local sin que meta el visitante
	 */
	private String localPuntosConsecutivos = "";
	
	/**
	 * Puntos consecutivos del equipo visitante sin que meta el local
	 */
	private String visitantePuntosConsecutivos = "";

	/**
	 * Si el partido es de PlayOff
	 */
	private boolean playOff = false;

	
	/**
	 * Número de cuartos del partido
	 */
	private Integer numeroCuartos = 0;
}
