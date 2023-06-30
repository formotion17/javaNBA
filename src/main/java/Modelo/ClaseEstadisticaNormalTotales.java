package Modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClaseEstadisticaNormalTotales {

	/**
	 * TEMPORADA EN LA QUE ESTAMOS
	 */
	private String temporada = "";
	
	/**
	 * TIEMPO JUGADOR
	 */
	private String tiempo="";
	
	/**
	 * TIPO RESULTADO
	 */
	private String tipoResultado = "";
	
	/**
	 * PARTIDOS JUGADOS
	 */
	private String partidosJugados="";
	
	/**
	 * PARTIDOS JUGADOS EN EL QUINTETO INICIAL
	 */
	private String partidosQuintetoInicial="";
	
	/**
	 * MINUTOS JUGADOS
	 */
	private String minutos="";
	
	/**
	 * ID JUGADOR
	 */
	private String idJugador="";
	
	/**
	 * TIROS DE CAMPO METIDOS
	 */
	private String tirosCampoMetidos = "";
	
	/**
	 * TIROS DE CAMPO INTENTADOS
	 */
	private String tirosCampoIntentados = "";
	
	/**
	 * PORCENTAJE EN TIROS DE CAMPO
	 */
	private String tirosCampoPorcentaje = "";
	
	/**
	 * TRIPLES METIDOS
	 */
	private String triplesMetidos = "";
	
	/**
	 * TRIPLES INTENTADOS
	 */
	private String triplesIntentados = "";
	
	/**
	 * PORCENTAJE EN TRIPLES
	 */
	private String triplesPorcentaje = "";
	
	/**
	 * CANASTAS DE DOS PUNTOS METIDAS
	 */
	private String dosPuntosMetidos = "";
	
	/**
	 * CANASTAS DE DOS PUNTOS INTENTADAS
	 */
	private String dosPuntosIntentados = "";
	
	/**
	 * PORCENTAJE DE TIROS DE DOS
	 */
	private String dosPuntosPorcentaje = "";
	
	/**
	 * TIROS LIBRES METIDOS
	 */
	private String tirosLibresMetidos = "";
	
	/**
	 * TIROS LIBRES INTENTADOS
	 */
	private String tirosLibresIntentados = "";
	
	/**
	 * PORCENTAJE DE TIROS LIBRES
	 */
	private String tirosLibresPorcentaje = "";
	
	/**
	 * REBOTES OFENSIVOS
	 */
	private String reboteOfensivo = "";
	
	/**
	 * REBOTES DEFENSIVOS
	 */
	private String reboteDefensivo = "";
	
	/**
	 * TOTAL REBOTES
	 */
	private String totalRebotes = "";
	
	/**
	 * ASISTENCIAS
	 */
	private String asistencias = "";
	
	/**
	 * ROBOS
	 */
	private String robos = "";
	
	/**
	 * TAPONES
	 */
	private String tapones = "";
	
	/**
	 * PERDIDAS
	 */
	private String perdidas = "";
	
	/**
	 * FALTAS PERSONALES
	 */
	private String faltasPersonales = "";
	
	/**
	 * PUNTOS
	 */
	private String puntos = "";
	
	/**
	 * PUNTOS POR PARTIDO
	 */
	private Double puntosPartidos=0.0;
	
	/**
	 * REBOTES POR PARTIDO
	 */
	private Double rebotesPartido=0.0;
	
	/**
	 * ASISTENCIAS POR PARTIDO
	 */
	private Double asistenciasPartido=0.0;
	
	/**
	 * TAPONES POR PARTIDO
	 */
	private Double taponesPartido=0.0;
	
	/**
	 * ROBOS POR PARTIDO
	 */
	private Double robosPartido=0.0;
	
	/**
	 * MINUTOS JUGADOS POR PARTIDO
	 */
	private Double minutosJugadosPartido=0.0;
	
	/**
	 * TIROS DE CAMPO ANOTADOS POR PARTIDO
	 */
	private Double tiroCampoAnotadosPartido=0.0;
	
	/**
	 * TIROS DE CAMPO INTENTADOS POR PARTIDO
	 */
	private Double tiroCampoIntentadosPartido=0.0;
	
	/**
	 * TRIPLES ANOTADOS POR PARTIDO
	 */
	private Double triplesAnotadosPartido=0.0;
	
	/**
	 * TRIPLES INTENTADOS POR PARTIDO
	 */
	private Double triplesIntentadosPartido=0.0;
	
	/**
	 * TIROS LIBRES ANOTADOS POR PARTIDO
	 */
	private Double tirosLibresAnotadosPartido=0.0;
	
	/**
	 * TIROS LIBRES INTENTADOS POR PARTIDO
	 */
	private Double tirosLibresIntentadosPartido=0.0;
	
	/**
	 * PERDIDAS POR PARTIDO
	 */
	private Double perdidasPartido=0.0;
	
	/**
	 * FALTAS POR PARTIDO
	 */
	private Double faltasPartido=0.0;
		
	/**
	 * Calculamos los puntos por partido
	 */
	public void setPuntosPartidos() {
		this.puntosPartidos = (double)Integer.parseInt(getPuntos())/Integer.parseInt(getPartidosJugados());
	}


	/**
	 * Calculamos el total de rebotes por partido
	 */
	public void setRebotesPartido() {
		this.rebotesPartido = (double)Integer.parseInt(getTotalRebotes())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos las asistencias por partido
	 */
	public void setAsistenciasPartido() {
		this.asistenciasPartido = (double)Integer.parseInt(getAsistencias())/Integer.parseInt(getPartidosJugados());
	}


	/**
	 * Calculamos los tapones por partido
	 */
	public void setTaponesPartido( ) {
		this.taponesPartido = (double)Integer.parseInt(getTapones())/Integer.parseInt(getPartidosJugados());;
	}


	/**
	 * Calculamos los robos por partido
	 */
	public void setRobosPartido( ) {
		this.robosPartido = (double)Integer.parseInt(getRobos())/Integer.parseInt(getPartidosJugados());
	}


	/**
	 * Calculamos los minutos por partido
	 */
	public void setMinutosJugadosPartido( ) {
		this.minutosJugadosPartido = (double)Integer.parseInt(getMinutos())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos los tiros de campo anotados por partido
	 */
	public void setTiroCampoAnotadosPartido()  {
		this.tiroCampoAnotadosPartido = (double)Integer.parseInt(getTirosCampoMetidos())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos los tiros de campo intentados por partido
	 */
	public void setTiroCampoIntentadosPartido( ) {
		this.tiroCampoIntentadosPartido = (double)Integer.parseInt(getTirosCampoIntentados())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos los triples anotados por partido
	 */
	public void setTriplesAnotadosPartido() {
		this.triplesAnotadosPartido = (double)Integer.parseInt(getTriplesMetidos())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos los triples intentados por partido
	 */
	public void setTriplesIntentadosPartido( ) {
		this.triplesIntentadosPartido = (double)Integer.parseInt(getTriplesIntentados())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos los tiros libres anotados por partido
	 */
	public void setTirosLibresAnotadosPartido( ) {
		this.tirosLibresAnotadosPartido = (double)Integer.parseInt(getTirosLibresMetidos())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos los tiros libres por partido
	 */
	public void setTirosLibresIntentadosPartido() {
		this.tirosLibresIntentadosPartido = (double)Integer.parseInt(getTirosLibresIntentados())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos las perdidas por partido
	 */
	public void setPerdidasPartido( ) {
		this.perdidasPartido = (double)Integer.parseInt(getPerdidas())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * calculamos las faltas por partido
	 */
	public void setFaltasPartido( ) {
		this.faltasPartido = (double)Integer.parseInt(getFaltasPersonales())/Integer.parseInt(getPartidosJugados());
	}

	/**
	 * Calculamos las medias
	 */
	public void calcularMedias() {
		setPuntosPartidos();
		setRebotesPartido();
		setAsistenciasPartido();
		setTaponesPartido();
		setRobosPartido();
		setMinutosJugadosPartido();
		setTiroCampoAnotadosPartido();
		setTiroCampoIntentadosPartido();
		setTriplesAnotadosPartido();
		setTriplesIntentadosPartido();
		setTirosLibresAnotadosPartido();
		setTirosLibresIntentadosPartido();
		setPerdidasPartido();
		setFaltasPartido();
	}	
}
