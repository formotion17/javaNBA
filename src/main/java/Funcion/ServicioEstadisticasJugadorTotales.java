package Funcion;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Coleccion.Temporadas;
import Controlador.BaseController;
import Mapper.MapJugadorEstadisticas;
import Modelo.ClasePartido;
import Utilidad.Util;

/**
 * Servicios el cual recorre jugadores y calcula las estadisticas de cada temporada:
 * 		REGULAR TOTAL
 * 		REGULAR MEDIA
 * 		PLAYOFF TOTAL
 * 		PLAYOFF MEDIA
 * 		TEMPORADA TOTAL
 * 		TEMPORADA MEDIA
 */

public class ServicioEstadisticasJugadorTotales extends BaseController {

	public static void main(String[] args) throws IOException {
		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");

		ArrayList<String> listaJugadores = new ArrayList<String>();

		// recogerJugadores();
		listaJugadores = Util.recogerJugadores();
		
		int numJugadores=0;

		for(String id:listaJugadores) {
			//String id = "jamesle01";
			
			// CONECTAMOS A MONGODB
			MongoClient mongo = Util.crearConexion();
			
			if(mongo !=null) {
				
				MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);

				eliminarTotalesJugador(id);
				
				numJugadores++;
	
				System.out.println("############################################################################");
				System.out.println(" JUGADOR:  " + id);
				System.out.println("############################################################################");
				for (Temporadas t : Temporadas.values()) {
					// Select the collection
	
					MongoCollection<Document> collection = db.getCollection(t.toString());
	
					// VAMOS A DEVOLVER LAS ESTADISTICAS DE UN JUGADOR DE UNA TEMPORADA
	
					// COMO LOCAL
					Document findLocal = new Document(ID_JUGADOR_LOCAL, id);
	
					MongoCursor<Document> lista = collection.find(findLocal).iterator();
	
					ArrayList<ClasePartido> listaPartidos = new ArrayList<ClasePartido>();
	
					while (lista.hasNext()) {
						listaPartidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document) lista.next(), id,
								ATRIBUTO_LOCAL));
					}
	
					// COMO VISITANTE
					Document findVisitante = new Document(ID_JUGADOR_VISITANTE, id);
	
					lista = collection.find(findVisitante).iterator();
	
					while (lista.hasNext()) {
						listaPartidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document) lista.next(), id,
								ATRIBUTO_VISITANTE));
					}
	
					tienePlayOff(listaPartidos);
	
					if (!listaPartidos.isEmpty()) {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: " + t.toString() + " tiene " + listaPartidos.size() + " partidos para " + id);
						System.out.println("############################################################################");
	
						for (int i = 0; i < listaPartidos.size(); i++) {
	
							// RECORREMOS PARTIDOS COMO LOCAL
							for (int j = 0; j < listaPartidos.get(i).getEquipoLocal().getJugadores().size(); j++) {
								if (listaPartidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
									if (listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos() != null) {
	
										setPartidosPlayOff(getPartidosPlayOff() + 1);
										setPartidosTotalesPlayoff(getPartidosTotalesPlayoff() + 1);
										rellenarJugador(getEstadisticasPlayOffTotal(),
												listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
										rellenarJugador(getEstadisticasPlayOffCarrera(),
												listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
	
										if (listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
											setPartidoPlayOffInicio(getPartidoPlayOffInicio() + 1);
											setPartidoTemporadaInicio(getPartidoTemporadaInicio() + 1);
	
											setPartidosTotalesPlayoffInicio(getPartidosTotalesPlayoffInicio() + 1);
											setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio() + 1);
										}
									}
								} else { // NOOO ESTAMOS EN PLAYOFF
									if (listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos() != null) {
	
										setPartidosRegular(getPartidosRegular() + 1);
										setPartidosTotalesRegular(getPartidosTotalesRegular() + 1);
										rellenarJugador(getEstadisticasRegularTotal(),
												listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
										rellenarJugador(getEstadisticasRegularCarrera(),
												listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
	
										if (listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
											setPartidoRegularInicio(getPartidoRegularInicio() + 1);
											setPartidosTotalesRegularInicio(getPartidosTotalesRegularInicio() + 1);
										}
									}
								}
	
								if (listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos() != null) {
									setPartidoTemporada(getPartidoTemporada() + 1);
									setPartidosTotalesTemporada(getPartidosTotalesTemporada() + 1);
									rellenarJugador(getEstadisticasTemporadaTotal(),
											listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaCarrera(),
											listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
	
									if (listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
										setPartidoTemporadaInicio(getPartidoTemporadaInicio() + 1);
										setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio() + 1);
									}
								}
	
							}
	
							// RECORREMOS PARTIDOS COMO VISITANTE
							for (int j = 0; j < listaPartidos.get(i).getEquipoVisitante().getJugadores().size(); j++) {
								if (listaPartidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
									if (listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos() != null) {
	
										setPartidosPlayOff(getPartidosPlayOff() + 1);
										setPartidosTotalesPlayoff(getPartidosTotalesPlayoff() + 1);
										rellenarJugador(getEstadisticasPlayOffTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
										rellenarJugador(getEstadisticasPlayOffCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
										
										if (listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
											setPartidoPlayOffInicio(getPartidoPlayOffInicio() + 1);
											setPartidosTotalesPlayoffInicio(getPartidosTotalesPlayoffInicio() + 1);
										}
									}
								} else { // NOOO ESTAMOS EN PLAYOFF
									if (listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos() != null) {
	
										setPartidosRegular(getPartidosRegular() + 1);
										setPartidosTotalesRegular(getPartidosTotalesRegular() + 1);
										rellenarJugador(getEstadisticasRegularTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
										rellenarJugador(getEstadisticasRegularCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
										
										if (listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
											setPartidoRegularInicio(getPartidoRegularInicio() + 1);
											setPartidosTotalesRegularInicio(getPartidosTotalesRegularInicio() + 1);
										}
									}
								}
								
								
								if (listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos() != null) {
	
									setPartidoTemporada(getPartidoTemporada() + 1);
									setPartidosTotalesTemporada(getPartidosTotalesTemporada() + 1);
									rellenarJugador(getEstadisticasTemporadaTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									
									if (listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
										setPartidoTemporadaInicio(getPartidoTemporadaInicio() + 1);
										setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio() + 1);									
									}
								}
								
							}
						}
	
						/**
						 * Calculamos las canastas de 2 puntos
						 */
						getEstadisticasRegularTotal().getBoxscore().calcularCanastasDosPuntos();
						getEstadisticasRegularCarrera().getBoxscore().calcularCanastasDosPuntos();
	
						if (isTemporadaPlayoff()) {
							getEstadisticasPlayOffTotal().getBoxscore().calcularCanastasDosPuntos();
							getEstadisticasPlayOffCarrera().getBoxscore().calcularCanastasDosPuntos();
						}
	
						getEstadisticasTemporadaTotal().getBoxscore().calcularCanastasDosPuntos();
						getEstadisticasTemporadaCarrera().getBoxscore().calcularCanastasDosPuntos();
	
						/**
						 * Calculamos los porcentajes de Tiro libre Tiros de Campo Triples
						 */
						getEstadisticasRegularTotal().getBoxscore().calcularPorcentajes();
						getEstadisticasRegularCarrera().getBoxscore().calcularPorcentajes();
	
						if (isTemporadaPlayoff()) {
							getEstadisticasPlayOffTotal().getBoxscore().calcularPorcentajes();
							getEstadisticasPlayOffCarrera().getBoxscore().calcularPorcentajes();
						}
	
						getEstadisticasTemporadaTotal().getBoxscore().calcularPorcentajes();
						getEstadisticasTemporadaCarrera().getBoxscore().calcularPorcentajes();
	
						/**
						 * Insertamos los documentos en la colecci�n de Totales
						 */
						MongoCollection<Document> insert = db.getCollection(COLECCION_TOTALES);
	
						insertarDocumento(rellenarDocumentosTotales(id, t.toString(), ATRIBUTO_REGULAR,getEstadisticasRegularTotal(), getPartidosRegular(), getPartidoRegularInicio()), insert);
						insertarDocumento(rellenarDocumentosMedia(id, t.toString(), ATRIBUTO_REGULAR,getEstadisticasRegularTotal(), getPartidosRegular(), getPartidoRegularInicio()), insert);
	
						if (isTemporadaPlayoff()) {
							insertarDocumento(rellenarDocumentosTotales(id, t.toString(), ATRIBUTO_PLAYOFF,getEstadisticasPlayOffTotal(), getPartidosPlayOff(), getPartidoPlayOffInicio()),insert);
							insertarDocumento(rellenarDocumentosMedia(id, t.toString(), ATRIBUTO_PLAYOFF,getEstadisticasPlayOffTotal(), getPartidosPlayOff(), getPartidoPlayOffInicio()),insert);
						}
	
						insertarDocumento(rellenarDocumentosTotales(id, t.toString(), ATRIBUTO_TEMPORADA,getEstadisticasTemporadaTotal(), getPartidoTemporada(), getPartidoTemporadaInicio()),insert);
						insertarDocumento(rellenarDocumentosMedia(id, t.toString(), ATRIBUTO_TEMPORADA,getEstadisticasTemporadaTotal(), getPartidoTemporada(), getPartidoTemporadaInicio()),insert);
	
					} else {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: " + t.toString() + " NO TIENE PARTIDOS");
						System.out.println("############################################################################");
					}
	
					resetearDatos();
				}
				// INSERTAMOS LA CARRERA
	
				MongoCollection<Document> insert = db.getCollection(COLECCION_TOTALES);
	
				insertarDocumento(rellenarDocumentosTotales(id, ATRIBUTO_CARRERA, ATRIBUTO_REGULAR,getEstadisticasRegularCarrera(), getPartidosTotalesRegular(), getPartidosTotalesRegularInicio()),insert);
				insertarDocumento(rellenarDocumentosMedia(id, ATRIBUTO_CARRERA, ATRIBUTO_REGULAR,getEstadisticasRegularCarrera(), getPartidosTotalesRegular(), getPartidosTotalesRegularInicio()),insert);
	
				insertarDocumento(rellenarDocumentosTotales(id, ATRIBUTO_CARRERA, ATRIBUTO_PLAYOFF,getEstadisticasPlayOffCarrera(), getPartidosTotalesPlayoff(), getPartidosTotalesPlayoffInicio()),insert);
				insertarDocumento(rellenarDocumentosMedia(id, ATRIBUTO_CARRERA, ATRIBUTO_PLAYOFF,getEstadisticasPlayOffCarrera(), getPartidosTotalesPlayoff(), getPartidosTotalesPlayoffInicio()),insert);
	
				insertarDocumento(rellenarDocumentosTotales(id, ATRIBUTO_CARRERA, ATRIBUTO_TEMPORADA,getEstadisticasTemporadaCarrera(), getPartidosTotalesTemporada(),getPartidosTotalesTemporadaInicio()), insert);
				insertarDocumento(rellenarDocumentosMedia(id, ATRIBUTO_CARRERA, ATRIBUTO_TEMPORADA, getEstadisticasTemporadaCarrera(),getPartidosTotalesTemporada(), getPartidosTotalesTemporadaInicio()),insert);
	
				resetearTotalesCarrera();
				
				System.out.println("Numero de jugadores descargados: "+numJugadores);
			}
			
			mongo.close();

		} // END DEL FOR

		System.out.println("Finalizamos");
		long fin = System.currentTimeMillis();

		double tiempo = (double) ((fin - inicio) / 1000);

		System.out.println("SE HA TARDADO EN DESCARGAR: " + tiempo + " Segundos");

	}

	private static void eliminarTotalesJugador(String id) throws UnknownHostException {
		int cont=0;
		//		CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
		
		MongoDatabase db = mongo.getDatabase("NBA");
		// TODO Auto-generated method stub
		MongoCollection<Document> collection = db.getCollection("totales");
		
		//Eliminamos las estadisticas de la temporada actual
		Document estadisticasEliminar = new Document("idJugador",id);
		
		
		MongoCursor<Document> listaEstadisticas = collection.find(estadisticasEliminar).iterator();
		

		// Eliminamos los partidos de la temporada
		while(listaEstadisticas.hasNext()) {
			cont++;
			Document partido = (Document)listaEstadisticas.next();
			System.out.println("Eliminamos: "+partido.get("temporada")+" "+partido.get("tiempo")+" "+partido.get("tiporesultado")+" "+partido.get("idjugador"));
			collection.deleteOne(partido);
		}
		System.out.println("ELIMINADOS: "+cont);
		
	}
}
