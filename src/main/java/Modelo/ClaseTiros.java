package Modelo;

import lombok.Data;

@Data
public class ClaseTiros {

	/**
	 * Si el tiro ha entrado o no
	 */
	private Boolean dentro = false;

	/**
	 * Distancia respecto a la cansasta del tiro
	 */
	private Integer distancia = 0;
	
	/**
	 * Tiempo restante para acabar el cuarto
	 */
	private Integer tiempoRestante = 0;

	/**
	 * Tipo de tiro
	 */
	private String tipo = "";
	
	/**
	 * Situacion cuando se hace el tiro
	 */
	private String situacion = "";
	
	/**
	 * Posicion respecto la posicion de arriba
	 */
	private String posicionTop = "";
	
	/**
	 * Posición respecto la posición izquierda
	 */
	private String posicionLeft = "";

	/**
	 * El cuarto en el es el tiro
	 */
	private String cuarto;
	
	/**
	 * Tanteo en la canasta
	 */
	
	private String tanteo;
	
	/**
	 * Tanteo equipo
	 */
	private String tanteoEquipo;
	
	/**
	 * Tanteo rival
	 */
	private String tanteoRival;
	
	/**
	 * Situacion Antes del tiro
	 */
	private String situacionAntes;
	
	/**
	 * Situacion despues del tiro
	 */
	private String situacionDespues;
}
