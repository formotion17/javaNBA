package Modelo;

import java.text.DecimalFormat;

import lombok.Data;

/**
 * Clase para guardar las estadisticas normales de un jugador o equipo
 * @author hatashi
 *
 */

@Data
public class ClaseEstadisticaNormal {

	/**
	 * ROBOS
	 */
	private Integer robos = 0;
	
	/**
	 * PUNTOS
	 */
	private Integer puntos = 0;
	
	/**
	 * TAPONES
	 */
	private Integer tapones = 0;
	
	/**
	 * PERDIDAS
	 */
	private Integer perdidas = 0;
	
	/**
	 * ASISTENCIAS
	 */
	private Integer asistencias = 0;
	
	/**
	 * TOTAL REBOTES
	 */
	private Integer totalRebotes = 0;
	
	/**
	 * TRIPLES METIDOS
	 */
	private Integer triplesMetidos = 0;
	
	/**
	 * REBOTES OFENSIVOS
	 */
	private Integer reboteOfensivo = 0;
	
	/**
	 * REBOTES DEFENSIVOS
	 */
	private Integer reboteDefensivo = 0;
	
	/**
	 * FALTAS PERSONALES
	 */
	private Integer faltasPersonales = 0;
	
	/**
	 * TIROS DE CAMPO METIDOS
	 */
	private Integer tirosCampoMetidos = 0;
	
	/**
	 * TRIPLES INTENTADOS
	 */
	private Integer triplesIntentados = 0;
	
	/**
	 * TIROS LIBRES METIDOS
	 */
	private Integer tirosLibresMetidos = 0;
	
	/**
	 * TIROS DE CAMPO INTENTADOS
	 */
	private Integer tirosCampoIntentados = 0;
	
	/**
	 * TIROS LIBRES INTENTADOS
	 */
	private Integer tirosLibresIntentados = 0;
	
	/**
	 * TIPO DE CUARTO QUE ESTAMOS RECOGIENDO
	 * 1º CUARTO - 2º CUARTO - 3º CUARTO - 4º CUARTO
	 * 1º OVERTIME ...ETC
	 * TOTAL PARTIDO 
	 */
	private String cuarto = "";
	
	/**
	 * PORCENTAJE EN EL TRIPLE
	 */
	private Double triplesPorcentaje = 0.0;
	
	/**
	 * PORCENTAJE DE TIROS LIBRES
	 */
	private Double tirosLibresPorcentaje = 0.0;
	
	/**
	 * PORCENTAJE DE TIROS DE CAMPO
	 */
	private Double tirosCampoPorcentaje = 0.0;
	
	/**
	 * PORCENTAJE TIROS LIBRES
	 */
	private String porcentajeTiroLibre = "";
	
	/**
	 * PORCENTAJE TRIPLES
	 */
	private String porcentajeTriples = "";
	
	/**
	 * PORCENTAJE TIROS DE CAMPO
	 */
	private String porcentajeTirosCampo = "";
	
	/**
	 * PORCENTAJE DE TIROS DE DOS
	 */
	private String porcentajeDosPuntos = "";
	
	/**
	 * TIROS DE DOS PUNTOS METIDOS
	 */
	private Integer dosPuntosMetidos = 0;
	
	/**
	 * TIROS DE DOS PUNTOS INTENTADOS
	 */
	private Integer dosPuntosIntentados = 0;

	/**
	 * Función para calcular las canastas de dos puntos
	 */
	public void calcularCanastasDosPuntos() {
		dosPuntosMetidos = tirosCampoMetidos - triplesMetidos;
		dosPuntosIntentados = tirosCampoIntentados - triplesIntentados;
	}

	/**
	 * Función para calcular el porcentaje de tiros de:
	 * Tiros Libres
	 * Tiros de dos puntos
	 * Tiros de tres puntos
	 * Tiros de campo
	 */
	public void calcularPorcentajes() {
		DecimalFormat df = new DecimalFormat("#.###");
		porcentajeTiroLibre = df.format(((double) tirosLibresMetidos) / tirosLibresIntentados);
		porcentajeTriples = df.format(((double) triplesMetidos) / triplesIntentados);
		porcentajeTirosCampo = df.format(((double) tirosCampoMetidos) / tirosCampoIntentados);
		porcentajeDosPuntos = df.format(((double) dosPuntosMetidos) / dosPuntosIntentados);
	}

		
}
