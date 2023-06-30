package Modelo;

import lombok.Data;

/**
 * Clases para guardar las estadisticas avanzadas
 * @author hatashi
 *
 */

@Data
public class ClaseEstadisticaAvanzada {

	/**
	 * DIFERENCIA ENTRE OFFENSIVE RATING Y DEFENSIVE RATING
	 */
	private Double NETRTG = 0.0;
	
	/**
	 * PUNTOS FANTASY
	 */
	private Double FANTASY = 0.0;
	
	/**
	 * % TIROS LIBRES RESPECTO A LOS TIROS DE CAMPO
	 */
	private Double FTARate = 0.0;
	
	/**
	 * % DE PUNTOS DE TIRO LIBRE RESPECTO AL TOTAL DE PUNTOS
	 */
	private Double perPTS1 = 0.0;
	
	/**
	 * % DE PUNTOS DE 2 RESPECTO AL TOTAL DE PUNTOS
	 */
	private Double perPTS2 = 0.0;
	
	/**
	 * % DE PUNTOS DE 3 RESPECTO AL TOTAL DE PUNTOS
	 */
	private Double perPTS3 = 0.0;
	
	/**
	 * % TIROS DE 2 INTENTADOS DE UN JUGADOR O EQUIPO RESPECTO AL TOTAL
	 */
	private Double perPTS2PT = 0.0;
	
	/**
	 * % TIROS DE 3 INTENTADOS DE UN JUGADOR O EQUIPO RESPECTO AL TOTAL
	 */
	private Double perPTS3PT = 0.0;
	
	/**
	 * % TIROS DE 2 METIDOS DE UN JUGADOR O EQUIPO RESPECTO AL TOTAL
	 */
	private Double perPTS2PTM = 0.0;
	
	/**
	 * % TIROS DE 3 METIDOS DE UN JUGADOR O EQUIPO RESPECTO AL TOTAL
	 */
	private Double perPTS3PTM = 0.0;
	
	/**
	 * EFICIENCIA EN EL TIRO TOMANDO EN CUENTA LOS TIROS METIDOS DE 1,2 Y 3
	 */
	private Double trueShootPer = 0.0;
	
	/**
	 * TIROS LIBRES INTENTADOS POR TIROS DE CAMPO REALIZADO
	 */
	private Double freeThrowRate = 0.0;
	
	/**
	 * PORCENTAJE TOTAL QUE REPRESENTA DENTRO DE LOS TIROS DE CAMPO REALIZADOS
	 */
	private Double threePointRate = 0.0;
	
	/**
	 * ROBOS QUE SE REALIZAN POR EL TOTAL DE POSESIONES DEL RIVAL
	 */
	private Double stealPercentage = 0.0;
	
	/**
	 * CANTIDAD DE REBOTES CAPTURADOS SOBRE EL TOTAL DISPONIBLE
	 */
	private Double totalReboundPer = 0.0;
	
	/**
	 * VOLUMEN DE USO DE UN JUGADOR DE LAS JUGADAS OFENSIVAS DEL EQUIPO
	 */
	private Double usagePercentage = 0.0;
	
	/**
	 * PUNTOS QUE PRODUCE UN JUGADOR/EQUIPO TRAS 100 POSESIONES
	 */
	private Double offensiveRating = 0.0;
	
	/**
	 * TIROS DE CAMPO INTENTADOS POR EL OPONENTE QUE HAN SIDO TAPONADOS
	 */
	private Double blockPercentage = 0.0;
	
	/**
	 * CANTIDAD DE PUNTOS QUE UN EQUIPO/JUGADOR A PERMITE TRAS 100 POSESIONES
	 */
	private Double defensiveRating = 0.0;
	
	/**
	 * CANTIDAD DE ASISTENCIAS RESPECTO AL TOTAL DEL EQUIPO
	 */
	private Double assistPercentage = 0.0;
	
	/**
	 * ACIERTO EN EL TIRO DANDOLE UN PUNTO MÁS A LOS TRIPLES RESPECTO A LOS TIROS DE DOS
	 */
	private Double effectiveGoalPer = 0.0;
	
	/**
	 * PERDIDAS COMETIDAS POR CADA 100 POSESIONES
	 */
	private Double turnoverPercentage = 0.0;
	
	/**
	 * CANTIDAD DE REBOTES DEFENSIVOS CAPTURADOS SOBRE EL TOTAL
	 */
	private Double defensiveReboundPer = 0.0;
	
	/**
	 * CANTIDAD DE REBOTES OFENSIVOS CAPTURADOS SOBRE EL TOTAL
	 */
	private Double offensiveReboundPer = 0.0;

}
