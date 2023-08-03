package Controlador;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import Mapper.Atributos;
import Modelo.ClaseEstadisticaNormalTotales;
import Modelo.ClaseJugador;
import Modelo.ClasePartido;

/**
 * Clase que hara de clase padre para muchas paginas y asi poder beneficiarse de los diferentes atributos a la hora de
 * hacer calculos
 * @author hatashi
 *
 */
public class BaseController extends Atributos{

	private static ClaseJugador estadisticasRegularTotal = new ClaseJugador();
	private static ClaseJugador estadisticasPlayOffTotal = new ClaseJugador();
	private static ClaseJugador estadisticasTemporadaTotal = new ClaseJugador();
	private static ClaseJugador estadisticasRegularCarrera = new ClaseJugador();
	private static ClaseJugador estadisticasPlayOffCarrera = new ClaseJugador();
	private static ClaseJugador estadisticasTemporadaCarrera = new ClaseJugador();
	
	private static int partidosTotalesRegular = 0;
	private static int partidosTotalesRegularInicio = 0;
	private static int partidosTotalesPlayoff = 0;
	private static int partidosTotalesPlayoffInicio = 0;
	private static int partidosTotalesTemporada = 0;
	private static int partidosTotalesTemporadaInicio = 0;
	
	private static int partidosRegular=0;
	private static int partidosPlayOff=0;
	private static int partidoTemporada = 0;
	private static int partidoRegularInicio=0;
	private static int partidoPlayOffInicio=0;
	private static int partidoTemporadaInicio=0;
	private static boolean temporadaPlayoff=false;
	
	// Alias Base Datos Colecciones
	protected static String BASE_DATOS_NBA="NBA";
	protected static String COLECCIION_TIROS="tiros";
	protected static String COLECCION_TOTALES="totales";
	
	// Alias Ruta bson busqueda
	protected static String ATRIBUTO_ID_JUGADOR="idJugador";
	protected static String ATRIBUTO_TEMPORADA="temporada";
	protected static String ATRIBUTO_TIEMPO="tiempo";
	protected static String ATRIBUTO_TIPO_RESULTADO="tiporesultado";
	protected static String ATRIBUTO_LOCAL="local";
	protected static String ATRIBUTO_VISITANTE="visitante";
	protected static String ATRIBUTO_REGULAR="regular";
	protected static String ATRIBUTO_PLAYOFF="playoff";
	protected static String ATRIBUTO_CARRERA="carrera";
	protected static String ATRIBUTO_PARTIDO="partidos";
	protected static String ATRIBUTO_PTS="PTS";
	protected static String ATRIBUTO_PF="PF";
	protected static String ATRIBUTO_TOV="TOV";
	protected static String ATRIBUTO_BLK="BLK";
	protected static String ATRIBUTO_STL="STL";
	protected static String ATRIBUTO_AST="AST";
	protected static String ATRIBUTO_TRB="TRB";
	protected static String ATRIBUTO_DRB="DRB";
	protected static String ATRIBUTO_ORB="ORB";
	protected static String ATRIBUTO_FTP="FT%";
	protected static String ATRIBUTO_FTA="FTA";
	protected static String ATRIBUTO_FT="FT";
	protected static String ATRIBUTO_2PP="2P%";
	protected static String ATRIBUTO_2PA="2PA";
	protected static String ATRIBUTO_2P="2P";
	protected static String ATRIBUTO_3PP="3P%";
	protected static String ATRIBUTO_3PA="3PA";
	protected static String ATRIBUTO_3P="3P";
	protected static String ATRIBUTO_FGP="FG%";
	protected static String ATRIBUTO_FGA="FGA";
	protected static String ATRIBUTO_FG="FG";
	protected static String ATRIBUTO_MP="MP";
	protected static String ATRIBUTO_G="G";
	protected static String ATRIBUTO_GS="GS";
	protected static String TIPO_RESULTADO_MEDIA="media";
	protected static String TIPO_RESULTADO_TOTAL="total";
	
	protected static String ID_JUGADOR_LOCAL="equipoLocal.jugadores.id";
	protected static String ID_JUGADOR_VISITANTE="equipoVisitante.jugadores.id";
	
	protected static String HOST="localhost";
	protected static int PUERTO=27017;
	protected static String TRES_DECIMALES="0.###";
	protected static String UN_DECIMAL="0.#";
	
	private static ArrayList<String> listaJugadores = new ArrayList<String>();
	
	/**
	 * Funci�n que nos inserta un documento en la colecci�n de la base de datos que le hayamos pasado
	 * @param document
	 * @param collection
	 */
	public static void insertarDocumento(Document document,MongoCollection<Document> collection) {
		collection.insertOne(document);
	}
	
	/**
	 * Rellenemos el documento con los totales del jugador
	 * @param id			idJugador
	 * @param temporada		Temporada que se juega
	 * @param tiempo		Temporada Regular, playOff o toda la temporada
	 * @param jugador		Clase Jugador
	 * @param jugados		Partidos jugados
	 * @param inicio		Partido Titular
	 * @return
	 */
	public static Document rellenarDocumentosTotales(String id, String temporada, String tiempo, ClaseJugador jugador,int jugados,int inicio) {
		
		Document info = new Document();
		
		info.put(ATRIBUTO_ID_JUGADOR, id);
		info.put(ATRIBUTO_JUGADOR_NOMBRE, jugador.getNombre());
		info.put(ATRIBUTO_JUGADOR_APELLIDO, jugador.getApellido());
		info.put(ATRIBUTO_TEMPORADA, temporada);
		info.put(ATRIBUTO_TIEMPO,tiempo);
		info.put(ATRIBUTO_TIPO_RESULTADO,TIPO_RESULTADO_TOTAL);
		info.put(ATRIBUTO_G,jugados);
		info.put(ATRIBUTO_GS,inicio);
		if(jugados!=0) {
			info.put(ATRIBUTO_MP,jugador.getSegundos());
		}else {
			info.put(ATRIBUTO_MP,"0");
		}
		info.put(ATRIBUTO_FG,jugador.getBoxscore().getTirosCampoMetidos());
		info.put(ATRIBUTO_FGA,jugador.getBoxscore().getTirosCampoIntentados());
		info.put(ATRIBUTO_FGP,jugador.getBoxscore().getPorcentajeTirosCampo());
		info.put(ATRIBUTO_3P,jugador.getBoxscore().getTriplesMetidos());
		info.put(ATRIBUTO_3PA,jugador.getBoxscore().getTriplesIntentados());
		info.put(ATRIBUTO_3PP,jugador.getBoxscore().getPorcentajeTriples());
		info.put(ATRIBUTO_2P,jugador.getBoxscore().getDosPuntosMetidos());
		info.put(ATRIBUTO_2PA,jugador.getBoxscore().getDosPuntosIntentados());
		info.put(ATRIBUTO_2PP,jugador.getBoxscore().getPorcentajeDosPuntos());
		info.put(ATRIBUTO_FT,jugador.getBoxscore().getTirosLibresMetidos());
		info.put(ATRIBUTO_FTA,jugador.getBoxscore().getTirosLibresIntentados());
		info.put(ATRIBUTO_FTP,jugador.getBoxscore().getPorcentajeTiroLibre());
		info.put(ATRIBUTO_ORB,jugador.getBoxscore().getReboteOfensivo());
		info.put(ATRIBUTO_DRB,jugador.getBoxscore().getReboteDefensivo());
		info.put(ATRIBUTO_TRB,jugador.getBoxscore().getTotalRebotes());
		info.put(ATRIBUTO_AST,jugador.getBoxscore().getAsistencias());
		info.put(ATRIBUTO_STL,jugador.getBoxscore().getRobos());
		info.put(ATRIBUTO_BLK,jugador.getBoxscore().getTapones());
		info.put(ATRIBUTO_TOV,jugador.getBoxscore().getPerdidas());
		info.put(ATRIBUTO_PF,jugador.getBoxscore().getFaltasPersonales());
		info.put(ATRIBUTO_PTS,jugador.getBoxscore().getPuntos());
		
		return info;
	}
	
	
	/**
	 * Rellenamos el documento con la media del jugador
	 * @param id			idJugador
	 * @param temporada		Temporada que se juega
	 * @param tiempo		Temporada Regular, playOff o toda la temporada
	 * @param jugador		Clase Jugador
	 * @param jugados		Partidos jugados
	 * @param inicio		Partido Titular
	 * @return
	 */
	public static Document rellenarDocumentosMedia(String id, String temporada, String tiempo, ClaseJugador jugador,int jugados,int inicio) {
		
		Document info = new Document();
		info.put(ATRIBUTO_ID_JUGADOR, id);
		info.put(ATRIBUTO_JUGADOR_NOMBRE, jugador.getNombre());
		info.put(ATRIBUTO_JUGADOR_APELLIDO, jugador.getApellido());
		info.put(ATRIBUTO_TEMPORADA, temporada);
		info.put(ATRIBUTO_TIEMPO,tiempo);
		info.put(ATRIBUTO_TIPO_RESULTADO,TIPO_RESULTADO_MEDIA);
		info.put(ATRIBUTO_G,jugados);
		info.put(ATRIBUTO_GS,inicio);
		if(jugados!=0) {
			info.put(ATRIBUTO_MP,jugador.getSegundos()/60/jugados);
		}else {
			info.put(ATRIBUTO_MP,"0");
		}
		info.put(ATRIBUTO_FG,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTirosCampoMetidos(),jugados));
		info.put(ATRIBUTO_FGA,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTirosCampoIntentados(),jugados));
		info.put(ATRIBUTO_FGP,jugador.getBoxscore().getPorcentajeTirosCampo());
		info.put(ATRIBUTO_3P,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTriplesMetidos(),jugados));
		info.put(ATRIBUTO_3PA,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTriplesIntentados(),jugados));
		info.put(ATRIBUTO_3PP,jugador.getBoxscore().getPorcentajeTriples());
		info.put(ATRIBUTO_2P,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getDosPuntosMetidos(),jugados));
		info.put(ATRIBUTO_2PA,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getDosPuntosIntentados(),jugados));
		info.put(ATRIBUTO_2PP,jugador.getBoxscore().getPorcentajeDosPuntos());
		info.put(ATRIBUTO_FT,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTirosLibresMetidos(),jugados));
		info.put(ATRIBUTO_FTA,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTirosLibresIntentados(),jugados));
		info.put(ATRIBUTO_FTP,jugador.getBoxscore().getPorcentajeTiroLibre());
		info.put(ATRIBUTO_ORB,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getReboteOfensivo(),jugados));
		info.put(ATRIBUTO_DRB,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getReboteDefensivo(),jugados));
		info.put(ATRIBUTO_TRB,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTotalRebotes(),jugados));
		info.put(ATRIBUTO_AST,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getAsistencias(),jugados));
		info.put(ATRIBUTO_STL,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getRobos(),jugados));
		info.put(ATRIBUTO_BLK,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getTapones(),jugados));
		info.put(ATRIBUTO_TOV,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getPerdidas(),jugados));
		info.put(ATRIBUTO_PF,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getFaltasPersonales(),jugados));
		info.put(ATRIBUTO_PTS,devolverValorFormateadoUnDecimal(jugador.getBoxscore().getPuntos(),jugados));
		
		return info;
	}
	
	/**
	 * Funci�n que nos devuelve un numero formateado
	 * @param a
	 * @param b
	 * @return
	 */
	private static String devolverValorFormateadoUnDecimal(int a,int b) {
		if(b == 0) {
			return "0";
		}
		return new java.text.DecimalFormat(UN_DECIMAL).format(Double.valueOf(((double) a/b)));
	}
	
	/**
	 * Funci�n que nos devuelve un numero formateado a 3 decimales
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings("unused")
	private String devolverValorFormateadoTresDecimales(int a, int b) {
		return new java.text.DecimalFormat(TRES_DECIMALES).format(Double.valueOf(((double) a/b)));
	}
	
	/**
	 * Funci�n para crear la conexi�n
	 * @return
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unused")
	private static MongoClient crearConexion() throws UnknownHostException {
		MongoClient mongo = null;
		mongo = new MongoClient(HOST,PUERTO);
		return mongo;
	}
	
	/**
	 * Funci�n que nos dice si el partido es de playOff o no
	 * @param listaPartidos
	 */
	public static void tienePlayOff(ArrayList<ClasePartido> listaPartidos) {
		temporadaPlayoff = false;
		for(int i=0;i<listaPartidos.size();i++) {
			if(listaPartidos.get(i).isPlayOff()) {
				temporadaPlayoff = true;
				break;
			}
		}
	}
	


	public static void resetearTotalesCarrera() {
		setEstadisticasRegularCarrera(new ClaseJugador());
		setEstadisticasPlayOffCarrera(new ClaseJugador());
		setEstadisticasTemporadaCarrera(new ClaseJugador());
		setPartidosTotalesRegular(0);
		setPartidosTotalesPlayoff(0);
		setPartidosTotalesTemporada(0);
		setPartidosTotalesRegularInicio(0);
		setPartidosTotalesPlayoffInicio(0);
		setPartidosTotalesTemporadaInicio(0);
	}
	
	public static void resetearDatos() {
		setEstadisticasRegularTotal(new ClaseJugador());
		setEstadisticasPlayOffTotal(new ClaseJugador());
		setEstadisticasTemporadaTotal(new ClaseJugador());
		setPartidosRegular(0);
		setPartidosPlayOff(0);
		setPartidoTemporada(0);
		setPartidoRegularInicio(0);
		setPartidoPlayOffInicio(0);
		setPartidoTemporadaInicio(0);
		//setPartidosTotalesRegular(0);
		//setPartidosTotalesPlayoff(0);
		//setPartidosTotalesTemporada(0);
		//setPartidosTotalesRegularInicio(0);
		//setPartidosTotalesPlayoffInicio(0);
		//setPartidosTotalesTemporadaInicio(0);
	}
	
	/**
	 * Funci�n que nos rellena estadisticas del jugador
	 * @param jugador
	 * @param box
	 */
	public static void rellenarJugador(ClaseJugador jugador, ClaseJugador box) {
		jugador.setNombre(box.getNombre());
		jugador.setApellido(box.getApellido());
		jugador.getBoxscore().setPuntos(box.getBoxscore().getPuntos()+jugador.getBoxscore().getPuntos());
		jugador.getBoxscore().setFaltasPersonales(box.getBoxscore().getFaltasPersonales()+jugador.getBoxscore().getFaltasPersonales());
		jugador.getBoxscore().setPerdidas(box.getBoxscore().getPerdidas()+jugador.getBoxscore().getPerdidas());
		jugador.getBoxscore().setTapones(box.getBoxscore().getTapones()+jugador.getBoxscore().getTapones());
		jugador.getBoxscore().setRobos(box.getBoxscore().getRobos()+jugador.getBoxscore().getRobos());
		jugador.getBoxscore().setAsistencias(box.getBoxscore().getAsistencias()+jugador.getBoxscore().getAsistencias());
		jugador.getBoxscore().setTotalRebotes(box.getBoxscore().getTotalRebotes()+jugador.getBoxscore().getTotalRebotes());
		jugador.getBoxscore().setReboteDefensivo(box.getBoxscore().getReboteDefensivo()+jugador.getBoxscore().getReboteDefensivo());
		jugador.getBoxscore().setReboteOfensivo(box.getBoxscore().getReboteOfensivo()+jugador.getBoxscore().getReboteOfensivo());
		jugador.getBoxscore().setTirosLibresIntentados(box.getBoxscore().getTirosLibresIntentados()+jugador.getBoxscore().getTirosLibresIntentados());
		jugador.getBoxscore().setTirosLibresMetidos(box.getBoxscore().getTirosLibresMetidos()+jugador.getBoxscore().getTirosLibresMetidos());
		jugador.getBoxscore().setTriplesIntentados(box.getBoxscore().getTriplesIntentados()+jugador.getBoxscore().getTriplesIntentados());
		jugador.getBoxscore().setTriplesMetidos(box.getBoxscore().getTriplesMetidos()+jugador.getBoxscore().getTriplesMetidos());
		jugador.getBoxscore().setTirosCampoIntentados(box.getBoxscore().getTirosCampoIntentados()+jugador.getBoxscore().getTirosCampoIntentados());
		jugador.getBoxscore().setTirosCampoMetidos(box.getBoxscore().getTirosCampoMetidos()+jugador.getBoxscore().getTirosCampoMetidos());
		jugador.setSegundos(box.getSegundos()+jugador.getSegundos());
	}
	
	/**
	 * Funci�n que nos rellena estadisticas del jugador con la condici�n que nos suma partidos jugados
	 * @param jugador
	 * @param box
	 * @param i
	 */
	public static void rellenarJugador(ClaseJugador jugador, ClaseEstadisticaNormalTotales box,int i) {
		jugador.getBoxscore().setPuntos(Integer.parseInt(box.getPuntos())+jugador.getBoxscore().getPuntos());
		jugador.getBoxscore().setFaltasPersonales(Integer.parseInt(box.getFaltasPersonales())+jugador.getBoxscore().getFaltasPersonales());
		jugador.getBoxscore().setPerdidas(Integer.parseInt(box.getPerdidas())+jugador.getBoxscore().getPerdidas());
		jugador.getBoxscore().setTapones(Integer.parseInt(box.getTapones())+jugador.getBoxscore().getTapones());
		jugador.getBoxscore().setRobos(Integer.parseInt(box.getRobos())+jugador.getBoxscore().getRobos());
		jugador.getBoxscore().setAsistencias(Integer.parseInt(box.getAsistencias())+jugador.getBoxscore().getAsistencias());
		jugador.getBoxscore().setTotalRebotes(Integer.parseInt(box.getTotalRebotes())+jugador.getBoxscore().getTotalRebotes());
		jugador.getBoxscore().setReboteDefensivo(Integer.parseInt(box.getReboteDefensivo())+jugador.getBoxscore().getReboteDefensivo());
		jugador.getBoxscore().setReboteOfensivo(Integer.parseInt(box.getReboteOfensivo())+jugador.getBoxscore().getReboteOfensivo());
		jugador.getBoxscore().setTirosLibresIntentados(Integer.parseInt(box.getTirosLibresIntentados())+jugador.getBoxscore().getTirosLibresIntentados());
		jugador.getBoxscore().setTirosLibresMetidos(Integer.parseInt(box.getTirosLibresMetidos())+jugador.getBoxscore().getTirosLibresMetidos());
		jugador.getBoxscore().setTriplesIntentados(Integer.parseInt(box.getTriplesIntentados())+jugador.getBoxscore().getTriplesIntentados());
		jugador.getBoxscore().setTriplesMetidos(Integer.parseInt(box.getTriplesMetidos())+jugador.getBoxscore().getTriplesMetidos());
		jugador.getBoxscore().setTirosCampoIntentados(Integer.parseInt(box.getTirosCampoIntentados())+jugador.getBoxscore().getTirosCampoIntentados());
		jugador.getBoxscore().setTirosCampoMetidos(Integer.parseInt(box.getTirosCampoMetidos())+jugador.getBoxscore().getTirosCampoMetidos());
		jugador.setSegundos(Integer.parseInt(box.getMinutos())+jugador.getSegundos());
		switch(i) {
			case 1:
				partidosTotalesRegular=partidosTotalesRegular+Integer.parseInt(box.getPartidosJugados());
				partidosTotalesRegularInicio=partidosTotalesRegularInicio+Integer.parseInt(box.getPartidosQuintetoInicial());
				break;
			case 2:
				partidosTotalesPlayoff=partidosTotalesPlayoff+Integer.parseInt(box.getPartidosJugados());
				partidosTotalesPlayoffInicio=partidosTotalesPlayoffInicio+Integer.parseInt(box.getPartidosQuintetoInicial());
				break;
			case 3:
				partidosTotalesTemporada=partidosTotalesTemporada+Integer.parseInt(box.getPartidosJugados());
				partidosTotalesTemporadaInicio=partidosTotalesTemporadaInicio+Integer.parseInt(box.getPartidosQuintetoInicial());
				break;
		}
	}

	/**
	 * GETTERS AND SETTERS
	 */
	
	/**
	 * @return estadisticasRegularTotal
	 */
	public static ClaseJugador getEstadisticasRegularTotal() {
		return estadisticasRegularTotal;
	}
	
	/**
	 * @param estadisticasRegularTotal
	 */
	public static void setEstadisticasRegularTotal(ClaseJugador estadisticasRegularTotal) {
		BaseController.estadisticasRegularTotal = estadisticasRegularTotal;
	}
	
	/**
	 * @return estadisticasPlayOffTotal
	 */
	public static ClaseJugador getEstadisticasPlayOffTotal() {
		return estadisticasPlayOffTotal;
	}
	
	/**
	 * @param estadisticasPlayOffTotal
	 */
	public static void setEstadisticasPlayOffTotal(ClaseJugador estadisticasPlayOffTotal) {
		BaseController.estadisticasPlayOffTotal = estadisticasPlayOffTotal;
	}
	
	/**
	 * @return estadisticasTemporadaTotal
	 */
	public static ClaseJugador getEstadisticasTemporadaTotal() {
		return estadisticasTemporadaTotal;
	}
	
	/**
	 * @param estadisticasTemporadaTotal
	 */
	public static void setEstadisticasTemporadaTotal(ClaseJugador estadisticasTemporadaTotal) {
		BaseController.estadisticasTemporadaTotal = estadisticasTemporadaTotal;
	}
	
	/**
	 * @return partidosRegular
	 */
	public static int getPartidosRegular() {
		return partidosRegular;
	}
	
	/**
	 * @param partidosRegular
	 */
	public static void setPartidosRegular(int partidosRegular) {
		BaseController.partidosRegular = partidosRegular;
	}
	
	/**
	 * @return partidosPlayOff
	 */
	public static int getPartidosPlayOff() {
		return partidosPlayOff;
	}
	
	/**
	 * @param partidosPlayOff
	 */
	public static void setPartidosPlayOff(int partidosPlayOff) {
		BaseController.partidosPlayOff = partidosPlayOff;
	}
	
	/**
	 * @return partidoTemporada
	 */
	public static int getPartidoTemporada() {
		return partidoTemporada;
	}
	
	/**
	 * @param partidoTemporada
	 */
	public static void setPartidoTemporada(int partidoTemporada) {
		BaseController.partidoTemporada = partidoTemporada;
	}
	
	/**
	 * @return partidoRegularInicio
	 */
	public static int getPartidoRegularInicio() {
		return partidoRegularInicio;
	}
	
	/**
	 * @param partidoRegularInicio
	 */
	public static void setPartidoRegularInicio(int partidoRegularInicio) {
		BaseController.partidoRegularInicio = partidoRegularInicio;
	}
	
	/**
	 * @return partidoPlayOffInicio
	 */
	public static int getPartidoPlayOffInicio() {
		return partidoPlayOffInicio;
	}
	
	/**
	 * @param partidoPlayOffInicio
	 */
	public static void setPartidoPlayOffInicio(int partidoPlayOffInicio) {
		BaseController.partidoPlayOffInicio = partidoPlayOffInicio;
	}
	
	/**
	 * @return partidoTemporadaInicio
	 */
	public static int getPartidoTemporadaInicio() {
		return partidoTemporadaInicio;
	}
	
	/**
	 * @param partidoTemporadaInicio
	 */
	public static void setPartidoTemporadaInicio(int partidoTemporadaInicio) {
		BaseController.partidoTemporadaInicio = partidoTemporadaInicio;
	}
	
	/**
	 * @return temporadaPlayoff
	 */
	public static boolean isTemporadaPlayoff() {
		return temporadaPlayoff;
	}
	
	/**
	 * @param temporadaPlayoff
	 */
	public static void setTemporadaPlayoff(boolean temporadaPlayoff) {
		BaseController.temporadaPlayoff = temporadaPlayoff;
	}
	
	/**
	 * @return the listaJugadores
	 */
	public static ArrayList<String> getListaJugadores() {
		return listaJugadores;
	}
	/**
	 * @param listaJugadores the listaJugadores to set
	 */
	public static void setListaJugadores(ArrayList<String> listaJugadores) {
		BaseController.listaJugadores = listaJugadores;
	}

	/**
	 * @return estadisticasRegularCarrera
	 */
	public static ClaseJugador getEstadisticasRegularCarrera() {
		return estadisticasRegularCarrera;
	}

	/**
	 * @param estadisticasRegularCarrera
	 */
	public static void setEstadisticasRegularCarrera(ClaseJugador estadisticasRegularCarrera) {
		BaseController.estadisticasRegularCarrera = estadisticasRegularCarrera;
	}

	/**
	 * @return estadisticasPlayOffCarrera
	 */
	public static ClaseJugador getEstadisticasPlayOffCarrera() {
		return estadisticasPlayOffCarrera;
	}

	/**
	 * @param estadisticasPlayOffCarrera
	 */
	public static void setEstadisticasPlayOffCarrera(ClaseJugador estadisticasPlayOffCarrera) {
		BaseController.estadisticasPlayOffCarrera = estadisticasPlayOffCarrera;
	}

	/**
	 * @return estadisticasTemporadaCarrera
	 */
	public static ClaseJugador getEstadisticasTemporadaCarrera() {
		return estadisticasTemporadaCarrera;
	}

	/**
	 * @param estadisticasTemporadaCarrera
	 */
	public static void setEstadisticasTemporadaCarrera(ClaseJugador estadisticasTemporadaCarrera) {
		BaseController.estadisticasTemporadaCarrera = estadisticasTemporadaCarrera;
	}

	/**
	 * @return partidosTotalesRegular
	 */
	public static int getPartidosTotalesRegular() {
		return partidosTotalesRegular;
	}

	/**
	 * @param partidosTotalesRegular
	 */
	public static void setPartidosTotalesRegular(int partidosTotalesRegular) {
		BaseController.partidosTotalesRegular = partidosTotalesRegular;
	}

	/**
	 * @return partidosTotalesPlayoff
	 */
	public static int getPartidosTotalesPlayoff() {
		return partidosTotalesPlayoff;
	}

	/**
	 * @param partidosTotalesPlayoff
	 */
	public static void setPartidosTotalesPlayoff(int partidosTotalesPlayoff) {
		BaseController.partidosTotalesPlayoff = partidosTotalesPlayoff;
	}

	/**
	 * @return partidosTotalesTemporada
	 */
	public static int getPartidosTotalesTemporada() {
		return partidosTotalesTemporada;
	}
	
	/**
	 * @param partidosTotalesTemporada
	 */
	public static void setPartidosTotalesTemporada(int partidosTotalesTemporada) {
		BaseController.partidosTotalesTemporada = partidosTotalesTemporada;
	}

	/**
	 * @return partidosTotalesRegularInicio
	 */
	public static int getPartidosTotalesRegularInicio() {
		return partidosTotalesRegularInicio;
	}

	/**
	 * @param partidosTotalesRegularInicio
	 */
	public static void setPartidosTotalesRegularInicio(int partidosTotalesRegularInicio) {
		BaseController.partidosTotalesRegularInicio = partidosTotalesRegularInicio;
	}

	/**
	 * @return partidosTotalesPlayoffInicio
	 */
	public static int getPartidosTotalesPlayoffInicio() {
		return partidosTotalesPlayoffInicio;
	}

	/**
	 * @param partidosTotalesPlayoffInicio
	 */
	public static void setPartidosTotalesPlayoffInicio(int partidosTotalesPlayoffInicio) {
		BaseController.partidosTotalesPlayoffInicio = partidosTotalesPlayoffInicio;
	}

	/**
	 * @return partidosTotalesTemporadaInicio
	 */
	public static int getPartidosTotalesTemporadaInicio() {
		return partidosTotalesTemporadaInicio;
	}

	/**
	 * @param partidosTotalesTemporadaInicio
	 */
	public static void setPartidosTotalesTemporadaInicio(int partidosTotalesTemporadaInicio) {
		BaseController.partidosTotalesTemporadaInicio = partidosTotalesTemporadaInicio;
	}
	

}
