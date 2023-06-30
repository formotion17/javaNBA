package Modelo;

import lombok.Data;

@Data
public class ClaseFullBoxscore {

	/**
	 * En el cuarto que estamos
	 * Tambien el total del partido
	 */
	private String cuarto = "";

	/**
	 * Mates
	 */
	private Integer mate = 0;
	
	/**
	 * Roboes
	 */
	private Integer robos = 0;
	
	/**
	 * Ganchos metidos
	 */
	private Integer gancho = 0;
	
	/**
	 * Puntos
	 */
	private Integer puntos = 0;
	
	/**
	 * Tapones
	 */
	private Integer tapones = 0;
	
	/**
	 * Bandejas
	 */
	private Integer bandeja = 0;
	
	/**
	 * Perdidas
	 */
	private Integer perdidas = 0;
	
	/**
	 * Mas/menos en el partido
	 */
	private Integer masMenos = 0;
	
	/**
	 * Canastas en suspension
	 */
	private Integer suspension = 0;
	
	/**
	 * Asistencias
	 */
	private Integer asistencias = 0;
	
	/**
	 * Mate fallado
	 */
	private Integer mateFallado = 0;
	
	/**
	 * Falta tecniva
	 */
	private Integer faltaTecnica = 0;
	
	/**
	 * Perdidas por pasos
	 */
	private Integer perdidaPasos = 0;
	
	/**
	 * Total de rebotes
	 */
	private Integer totalRebotes = 0;
	
	/**
	 * Tapones recibidos
	 */
	private Integer taponRecibido = 0;
	
	/**
	 * Ganchos fallados
	 */
	private Integer ganchoFallado = 0;
	
	/**
	 * Triples metidos
	 */
	private Integer triplesMetidos = 0;
	
	/**
	 * Bandejas falladas
	 */
	private Integer bandejaFallada = 0;
	
	/**
	 * Perdidas por mal pase
	 */
	private Integer perdidaMalPase = 0;
	
	/**
	 * Rebotes ofensivos
	 */
	private Integer reboteOfensivo = 0;
	
	/**
	 * Rebotes defensivos
	 */
	private Integer reboteDefensivo = 0;
	
	/**
	 * Faltas personales
	 */
	private Integer faltasPersonales = 0;
	
	/**
	 * Tiros de campo metidos
	 */
	private Integer tirosCampoMetidos = 0;
	
	/**
	 * Triples intentados
	 */
	private Integer triplesIntentados = 0;
	
	/**
	 * Perdidas por fuera de banda
	 */
	private Integer perdidaFueraBanda = 0;
	
	/**
	 * Perdidas por campo atras
	 */
	private Integer perdidaCampoAtras = 0;
	
	/**
	 * Perdidas otras
	 */
	private Integer perdidaOtros = 0;
	
	/**
	 * Perdidas 3 segundos
	 */
	private Integer perdidaTresSegundos = 0;
	
	/**
	 * Perdida por falta personal
	 */
	private Integer perdidaFalta = 0;
	
	/**
	 * Perdida por dobles
	 */
	private Integer perdidaDobles = 0;
	
	/**
	 * Perdida por pie
	 */
	private Integer perdidaPie = 0;
	
	/**
	 * Suspensiones falladas
	 */
	private Integer suspensionFallado = 0;
	
	/**
	 * Faltas personales en el tiro
	 */
	private Integer faltaPersonalTiro = 0;
	
	/**
	 * Perdidas por pisar fuera
	 */
	private Integer perdidaPisarFuera = 0;
	
	/**
	 * Tiros libres metidos
	 */
	private Integer tirosLibresMetidos = 0;
	
	/**
	 * Perdidas por Goal Tending
	 */
	private Integer perdidaGoaltending = 0;
	
	/**
	 * Perdidas en balon perdido
	 */
	private Integer perdidaBalonPerdido = 0;
	
	/**
	 * Faltas personales en ataque
	 */
	private Integer faltaPersonalAtaque = 0;
	
	/**
	 * Tapones recibidos en el triple
	 */
	private Integer taponRecibidoTriple = 0;
	
	/**
	 * Total del primer tiro libre fuera
	 */
	private Integer primerTiroLibreFuera = 0;
	
	/**
	 * Total del primer tiro libre tirado
	 */
	private Integer primerTiroLibreTotal = 0;
	
	/**
	 * Total del tercer tiro libre fuera
	 */
	private Integer tercerTiroLibreFuera = 0;
	
	/**
	 * Total del tercer tiro libre tirados
	 */
	private Integer tercerTiroLibreTotal = 0;
	
	/**
	 * Tiros de campo intentados
	 */
	private Integer tirosCampoIntentados = 0;
	
	/**
	 * Faltas perdonales en defensa
	 */
	private Integer faltaPersonalDefensa = 0;
	
	/**
	 * Total de segundos tiros libres fallados
	 */
	private Integer segundoTiroLibreFuera = 0;
	
	/**
	 * Total de segundos tiros libres tirados
	 */
	private Integer segundoTiroLibreTotal = 0;
	
	/**
	 * Tercer tiro libre dentro
	 */
	private Integer tercerTiroLibreDentro = 0;
	
	/**
	 * Primer tiro libre metido
	 */
	private Integer primerTiroLibreDentro = 0;
	
	/**
	 * Tiros libres intentados
	 */
	private Integer tirosLibresIntentados = 0;
	
	/**
	 * Segundo tiro libre metido
	 */
	private Integer segundoTiroLibreDentro = 0;
	
	/**
	 * Faltas personales provocadas
	 */
	private Integer faltasPersonalesProvocadas = 0;
	
	/**
	 * Faltas personales provocadas en el tiro
	 */
	private Integer faltaPersonalProvocadaEnTiro = 0;
	
	/**
	 * Faltas personales provovadas en ataque
	 */
	private Integer faltaPersonalProvocadaEnAtaque = 0;
	
	/**
	 * Faltas personales provocadas en defensa
	 */
	private Integer faltaPersonalProvocadaEnDefensa = 0;
	
	/**
	 * Porcentaje de triples
	 */
	private Double triplesPorcentaje = 0.0;
	
	/**
	 * Porcentaje de tiros de campo
	 */
	private Double tirosCampoPorcentaje = 0.0;
	
	/**
	 * Porcentaje de tiros libres
	 */
	private Double tirosLibresPorcentaje = 0.0;
	
	/**
	 * Porcentaje del primer tiro libre metido
	 */
	private Double primerTiroLibrePorcentaje = 0.0;
	
	/**
	 * Porcentaje del tercer tiro libre metido
	 */
	private Double tercerTiroLibrePorcentaje = 0.0;
	
	/**
	 * Porcentaje del segundo tiro libre metido
	 */
	private Double segundoTiroLibrePorcentaje = 0.0;
	
	/**
	 * Faltas personales provocadas en defensa
	 */
	private Integer faltaPersonalDoble = 0;
	

}
