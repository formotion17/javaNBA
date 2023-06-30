package Modelo;

import java.util.ArrayList;
import java.util.Comparator;

import lombok.Data;

@Data
public class ClaseJugador {

	/**
	 * Total de estadisticas en el partido
	 */
	private ClaseFullBoxscore totalPartido = new ClaseFullBoxscore();
	
	/**
	 * Lista de tiros del jugador
	 */
	private ArrayList<ClaseTiros> listaTiros = new ArrayList<ClaseTiros>();
	
	/**
	 * Estadisticas normales del jugador
	 */
	private ClaseEstadisticaNormal boxscore = new ClaseEstadisticaNormal();
	
	/**
	 * Estadisticas avanzadas del jugador
	 */
	private ClaseEstadisticaAvanzada estadisticaAvanzada = new ClaseEstadisticaAvanzada();
	
	/**
	 * Estadisticas por cuartos
	 */
	private ArrayList<ClaseFullBoxscore> cuartos = new ArrayList<ClaseFullBoxscore>();

	/**
	 * Id del jugador
	 */
	private String id = "";
	
	/**
	 * Nombre del jugador
	 */
	private String nombre = "";
	
	/**
	 * Apellido del jugador
	 */
	private String apellido = "";

	/**
	 * Si el jugador ha salido en el quintento titular
	 */
	private Boolean inicio = false;
	private int posicionTabla;

	/**
	 * Tiempo jugado por el jugador
	 */
	private Integer segundos = 0;
	

	/**
	 * Ordenamos por puntos metidos
	 */
	public void ordenarJugadoresPuntosMetidos() {
		@SuppressWarnings("unused")
		Comparator<ClaseJugador> comparator = new Comparator<ClaseJugador>() {

			@Override
			public int compare(ClaseJugador o1, ClaseJugador o2) {
				return o1.getBoxscore().getPuntos().compareTo(o2.getBoxscore().getPuntos());
			}
		};
	}
	
	public void addCuartoJugador(ClaseFullBoxscore devolverFullBox) {
		cuartos.add(devolverFullBox);
	}
}
