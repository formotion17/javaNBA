package Funcion;

/**
 * Clase para actualizar las estadisticas de los jugadores que han jugado este día
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidad.Util;
import Coleccion.Temporadas;
import Controlador.BaseController;
import Implementacion.GuardarTirosJugador;
import Mapper.MapJugadorEstadisticas;
import Modelo.ClaseJugador;
import Modelo.ClaseJugadorDatos;
import Modelo.ClaseJugadorTiros;
import Modelo.ClasePartido;


public class EstadisticasJugadorDiaria extends BaseController{
	
	private static MongoDatabase db;
	private static String temporada = "season20212022";
	private static GuardarTirosJugador guardarTirosJugador = new GuardarTirosJugador();
	
	
	public static void main(String[] args) throws IOException {
		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");
		
		ArrayList<String> listaJugadores = new ArrayList<String>();
		
		//recogerJugadores();
		listaJugadores = Util.recogerJugadoresDiaria();
		
		//	CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
					
		if(mongo!=null) {
			
			db = mongo.getDatabase(BASE_DATOS_NBA);
			
			for(String id:listaJugadores) {
				
				System.out.println("############################################################################");
				System.out.println(" JUGADOR:  "+id);
				System.out.println("############################################################################");
				
				
				MongoCollection<Document> collection = db.getCollection(COLECCION_TOTALES);
				
				// ELIMINAMOS LOS PARTIDOS DE LA TEMPORADA
				Document estadisticasEliminar = new Document(ATRIBUTO_ID_JUGADOR,id);
				estadisticasEliminar.put(ATRIBUTO_TEMPORADA, temporada);
				
				MongoCursor<Document> listaEstadisticas = collection.find(estadisticasEliminar).iterator();				

				while(listaEstadisticas.hasNext()) {
					Document partido = (Document)listaEstadisticas.next();
					System.out.println("Eliminamos: "+partido.get(ATRIBUTO_TEMPORADA)+" "+partido.get(ATRIBUTO_TIEMPO)+" "+partido.get(ATRIBUTO_TIPO_RESULTADO)+" "+partido.get(ATRIBUTO_ID_JUGADOR));
					collection.deleteOne(partido);
				}
				
				collection = db.getCollection(temporada.toString());
				
				// VAMOS A DEVOLVER LAS ESTADISTICAS DE UN JUGADOR DE UNA TEMPORADA
				// COMO LOCAL
				Document findLocal = new Document(ID_JUGADOR_LOCAL,id);

				MongoCursor<Document> lista = collection.find(findLocal).iterator();
				
				ArrayList<ClasePartido> listaPartidos = new ArrayList<ClasePartido>();
				
				while(lista.hasNext()) {
					listaPartidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,ATRIBUTO_LOCAL));
				}
				
				// COMO VISITANTE
				Document findVisitante = new Document(ID_JUGADOR_VISITANTE,id);

				lista = collection.find(findVisitante).iterator();
				
				while(lista.hasNext()) {
					listaPartidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,ATRIBUTO_VISITANTE));
				}
				
				tienePlayOff(listaPartidos);
							
				if(!listaPartidos.isEmpty()) {
					System.out.println("############################################################################");
					System.out.println(" TEMPORADA: "+temporada.toString()+" tiene "+listaPartidos.size()+" partidos para "+id);
					System.out.println("############################################################################");
					
					for(int i=0;i<listaPartidos.size();i++) {
						for(int j = 0;j<listaPartidos.get(i).getEquipoLocal().getJugadores().size();j++) {
							if(listaPartidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
								if(listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos()!=null) {
									
									setPartidosPlayOff(getPartidosPlayOff()+1);
									setPartidoTemporada(getPartidoTemporada()+1);

									setPartidosTotalesPlayoff(getPartidosTotalesPlayoff()+1);
									setPartidosTotalesTemporada(getPartidosTotalesTemporada()+1);

									rellenarJugador(getEstadisticasPlayOffTotal(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaTotal(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									
									rellenarJugador(getEstadisticasPlayOffCarrera(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaCarrera(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									
									if(listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
										setPartidoPlayOffInicio(getPartidoPlayOffInicio()+1);
										setPartidoTemporada(getPartidoTemporada()+1);
										
										setPartidosTotalesPlayoffInicio(getPartidosTotalesPlayoffInicio()+1);
										setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio()+1);
									}
								}
							}else { // NOOO ESTAMOS EN PLAYOFF
								if(listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos()!=null) {
									
									setPartidosRegular(getPartidosRegular()+1);
									setPartidoTemporada(getPartidoTemporada()+1);

									setPartidosTotalesRegular(getPartidosTotalesRegular()+1);
									setPartidosTotalesTemporada(getPartidosTotalesTemporada()+1);
									
									rellenarJugador(getEstadisticasRegularTotal(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaTotal(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									
									rellenarJugador(getEstadisticasRegularCarrera(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaCarrera(),listaPartidos.get(i).getEquipoLocal().getJugadores().get(j));
									
									if(listaPartidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
										setPartidoRegularInicio(getPartidoRegularInicio()+1);
										setPartidoTemporadaInicio(getPartidoTemporadaInicio()+1);
										
										setPartidosTotalesRegularInicio(getPartidosTotalesRegularInicio()+1);
										setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio()+1);
									}
								}
							}
						}
						for(int j = 0;j<listaPartidos.get(i).getEquipoVisitante().getJugadores().size();j++) {
							if(listaPartidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
								if(listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos()!=null) {

									setPartidosPlayOff(getPartidosPlayOff()+1);
									setPartidoTemporada(getPartidoTemporada()+1);

									setPartidosTotalesPlayoff(getPartidosTotalesPlayoff()+1);
									setPartidosTotalesTemporada(getPartidosTotalesTemporada()+1);										
																			
									rellenarJugador(getEstadisticasPlayOffTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									
									rellenarJugador(getEstadisticasPlayOffCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									
									if(listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
										setPartidoPlayOffInicio(getPartidoPlayOffInicio()+1);
									setPartidoTemporada(getPartidoTemporada()+1);
										
										setPartidosTotalesPlayoffInicio(getPartidosTotalesPlayoffInicio()+1);
										setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio()+1);
									}
								}
							}else { // NOOO ESTAMOS EN PLAYOFF
								if(listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos()!=null) {

									setPartidosRegular(getPartidosRegular()+1);
									setPartidoTemporada(getPartidoTemporada()+1);

									setPartidosTotalesRegular(getPartidosTotalesRegular()+1);
									setPartidosTotalesTemporada(getPartidosTotalesTemporada()+1);
									
									rellenarJugador(getEstadisticasRegularTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaTotal(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									
									rellenarJugador(getEstadisticasRegularCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									rellenarJugador(getEstadisticasTemporadaCarrera(),listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j));
									
									if(listaPartidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
										setPartidoRegularInicio(getPartidoRegularInicio()+1);
										setPartidoTemporadaInicio(getPartidoTemporadaInicio()+1);
										
										setPartidosTotalesRegularInicio(getPartidosTotalesRegularInicio()+1);
										setPartidosTotalesTemporadaInicio(getPartidosTotalesTemporadaInicio()+1);
									}
								}
							}
						}
					}
					

					/**
					 * Calculamos las canastas de 2 puntos
					 */
					getEstadisticasRegularTotal().getBoxscore().calcularCanastasDosPuntos();
					getEstadisticasRegularCarrera().getBoxscore().calcularCanastasDosPuntos();
					
					if(isTemporadaPlayoff()) {
						getEstadisticasPlayOffTotal().getBoxscore().calcularCanastasDosPuntos();
						getEstadisticasPlayOffCarrera().getBoxscore().calcularCanastasDosPuntos();
					}
					
					getEstadisticasTemporadaTotal().getBoxscore().calcularCanastasDosPuntos();
					getEstadisticasTemporadaCarrera().getBoxscore().calcularCanastasDosPuntos();
					
					/**
					 * Calculamos los porcentajes de
					 * Tiro libre
					 * Tiros de Campo
					 * Triples
					 */
					getEstadisticasRegularTotal().getBoxscore().calcularPorcentajes();
					getEstadisticasRegularCarrera().getBoxscore().calcularPorcentajes();
					
					if(isTemporadaPlayoff()) {
						getEstadisticasPlayOffTotal().getBoxscore().calcularPorcentajes();
						getEstadisticasPlayOffCarrera().getBoxscore().calcularPorcentajes();
					}
					
					getEstadisticasTemporadaTotal().getBoxscore().calcularPorcentajes();
					getEstadisticasTemporadaCarrera().getBoxscore().calcularPorcentajes();
	
					
					/**
					 * Insertamos los documentos en la colección de Totales
					 */
					MongoCollection<Document> insert = db.getCollection(COLECCION_TOTALES);
					
					insertarDocumento(rellenarDocumentosTotales(id,temporada,ATRIBUTO_REGULAR,getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
					insertarDocumento(rellenarDocumentosMedia(id,temporada,ATRIBUTO_REGULAR,getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
					
					if(isTemporadaPlayoff()) {
						insertarDocumento(rellenarDocumentosTotales(id,temporada,ATRIBUTO_PLAYOFF,getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
						insertarDocumento(rellenarDocumentosMedia(id,temporada,ATRIBUTO_PLAYOFF,getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
					}
					
					insertarDocumento(rellenarDocumentosTotales(id,temporada,ATRIBUTO_TEMPORADA,getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
					insertarDocumento(rellenarDocumentosMedia(id,temporada,ATRIBUTO_TEMPORADA,getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
					
					
				}else {
					System.out.println("############################################################################");
					System.out.println(" TEMPORADA: "+temporada+" NO TIENE PARTIDOS");
					System.out.println("############################################################################");
				}
				
				resetearDatos();
				resetearTotalesCarrera();
				
				// INSERTAMOS LA CARRERA
				// PARA ELLO RECORREMOS LAS TEMPORADAS QUE TENEMOS GUARDADAS Y VAMOS RECOPILANDO LOS DATOS
				// BORRAMOS LOS TOTALES ANTERIOES, YA QUE SI NO CREARIAMOS NUEVOS TOTALES
				
				borrarEstadisticasCarrera(id);

				MongoCollection<Document> collectionEstadisticas = db.getCollection(COLECCION_TOTALES);
				
				for(Temporadas t :Temporadas.values()) {
					
					System.out.println("TEMPORADA	"+t.toString());
					
					recorrerTemporada(collectionEstadisticas,id,t.toString(),ATRIBUTO_REGULAR,getEstadisticasRegularCarrera(),1,"REGULAR");
					recorrerTemporada(collectionEstadisticas,id,t.toString(),ATRIBUTO_PLAYOFF,getEstadisticasPlayOffCarrera(),2,"PLAYOFF");
					recorrerTemporada(collectionEstadisticas,id,t.toString(),ATRIBUTO_TEMPORADA,getEstadisticasTemporadaCarrera(),3,"TEMPORADA");
				}	
				/**
				 * Calculamos las canastas de 2 puntos
				 */
				getEstadisticasRegularTotal().getBoxscore().calcularCanastasDosPuntos();
				getEstadisticasRegularCarrera().getBoxscore().calcularCanastasDosPuntos();
				
				getEstadisticasPlayOffTotal().getBoxscore().calcularCanastasDosPuntos();
				getEstadisticasPlayOffCarrera().getBoxscore().calcularCanastasDosPuntos();
				
				
				getEstadisticasTemporadaTotal().getBoxscore().calcularCanastasDosPuntos();
				getEstadisticasTemporadaCarrera().getBoxscore().calcularCanastasDosPuntos();
				
				/**
				 * Calculamos los porcentajes de
				 * Tiro libre
				 * Tiros de Campo
				 * Triples
				 */
				getEstadisticasRegularTotal().getBoxscore().calcularPorcentajes();
				getEstadisticasRegularCarrera().getBoxscore().calcularPorcentajes();
				
				getEstadisticasPlayOffTotal().getBoxscore().calcularPorcentajes();
				getEstadisticasPlayOffCarrera().getBoxscore().calcularPorcentajes();
				
				getEstadisticasTemporadaTotal().getBoxscore().calcularPorcentajes();
				getEstadisticasTemporadaCarrera().getBoxscore().calcularPorcentajes();
				
				MongoCollection<Document> insert = db.getCollection(COLECCION_TOTALES);
				
				insertarDocumento(rellenarDocumentosTotales(id,ATRIBUTO_CARRERA,ATRIBUTO_REGULAR,getEstadisticasRegularCarrera(),getPartidosTotalesRegular(),getPartidosTotalesRegularInicio()), insert);
				insertarDocumento(rellenarDocumentosMedia(id,ATRIBUTO_CARRERA,ATRIBUTO_REGULAR,getEstadisticasRegularCarrera(),getPartidosTotalesRegular(),getPartidosTotalesRegularInicio()), insert);
				
				insertarDocumento(rellenarDocumentosTotales(id,ATRIBUTO_CARRERA,ATRIBUTO_PLAYOFF,getEstadisticasPlayOffCarrera(),getPartidosTotalesPlayoff(),getPartidosTotalesPlayoffInicio()),insert);
				insertarDocumento(rellenarDocumentosMedia(id,ATRIBUTO_CARRERA,ATRIBUTO_PLAYOFF,getEstadisticasPlayOffCarrera(),getPartidosTotalesPlayoff(),getPartidosTotalesPlayoffInicio()),insert);
				
				insertarDocumento(rellenarDocumentosTotales(id,ATRIBUTO_CARRERA,ATRIBUTO_TEMPORADA,getEstadisticasTemporadaCarrera(),getPartidosTotalesTemporada(),getPartidosTotalesTemporadaInicio()),insert);
				insertarDocumento(rellenarDocumentosMedia(id,ATRIBUTO_CARRERA,ATRIBUTO_TEMPORADA,getEstadisticasTemporadaCarrera(),getPartidosTotalesTemporada(),getPartidosTotalesTemporadaInicio()),insert);
				
				resetearTotalesCarrera();
				
				generarTirosJugador(id,mongo);
				
				
				
			}	// END DEL FOR
			
		System.out.println("Finalizamos");
		long fin = System.currentTimeMillis();
		
		double tiempo = (double) ((fin - inicio)/1000);
        
        System.out.println("SE HA TARDADO EN DESCARGAR: "+tiempo+" Segundos");
        
        mongo.close();
        
        borrarArchivoId();
        
		}

	}
	
	private static void generarTirosJugador(String idJugador, MongoClient mongo) throws JsonProcessingException {
		
		ArrayList<ClaseJugadorTiros> listaTirosPartidos = new ArrayList<ClaseJugadorTiros>();
		
		MongoCollection<Document> collection =db.getCollection(COLECCIION_TIROS);
		
		ClaseJugadorDatos jugador = new ClaseJugadorDatos();
		
		@SuppressWarnings("unused")
		Document eliminarTiros = collection.findOneAndDelete(new Document(ATRIBUTO_ID_JUGADOR,idJugador));
		
		for(Temporadas t : Temporadas.values()) {
			listaTirosPartidos.addAll(guardarTirosJugador.recogerTirosTemporada(t.toString(), idJugador, mongo));
		}
		
		jugador.setIdJugador(idJugador);
		jugador.setListaTiros(listaTirosPartidos);
		guardarTirosJugador.guardarTirosJugador(jugador, db);
	}
	
	private static void recorrerTemporada(MongoCollection<Document> collection, String id, String temporada, String tiempo, ClaseJugador claseJugador, int i, String tipo) {
		
		Document doc = new Document(ATRIBUTO_ID_JUGADOR,id);
		doc.put(ATRIBUTO_TEMPORADA, temporada);
		doc.put(ATRIBUTO_TIEMPO, tiempo);
		doc.put(ATRIBUTO_TIPO_RESULTADO, TIPO_RESULTADO_TOTAL);
		
		MongoCursor<Document>  lista = collection.find(doc).iterator();
		
		while(lista.hasNext()) {
			Document partido = (Document)lista.next();
			rellenarJugador(claseJugador, MapJugadorEstadisticas.devolverEstadisticasTotalesJugador(partido),i);
			System.out.println("	"+tipo);
		}
		
		
	}
	private static void borrarEstadisticasCarrera(String id) {
		
		MongoCollection<Document> collection = db.getCollection(COLECCION_TOTALES);
		
		//Eliminamos las estadisticas de la temporada actual
		Document estadisticasEliminar = new Document(ATRIBUTO_ID_JUGADOR,id);
		estadisticasEliminar.put(ATRIBUTO_TEMPORADA, ATRIBUTO_CARRERA);
		
		
		MongoCursor<Document> listaEstadisticas = collection.find(estadisticasEliminar).iterator();
		

		// Eliminamos los partidos de la temporada
		while(listaEstadisticas.hasNext()) {
			Document partido = (Document)listaEstadisticas.next();
			System.out.println("Eliminamos: "+partido.get(ATRIBUTO_TEMPORADA)+" "+partido.get(ATRIBUTO_TIEMPO)+" "+partido.get(ATRIBUTO_TIPO_RESULTADO)+" "+partido.get(ATRIBUTO_ID_JUGADOR));
			collection.deleteOne(partido);
		}
	}

	private static void borrarArchivoId() {
		File fichero = new File("E://Programacion//pruebasMaven//NBAMongo//nuevosId.txt");
		if (fichero.delete())
			   System.out.println("El fichero ha sido borrado satisfactoriamente");
			else
			   System.out.println("El fichero no puede ser borrado");
		
	}
}
