package Funcion;

/**
 * Clase para Recalcular las estaditicas siguiente de cada Jugador de cada Temporada:
 * Estadisticas 	Totales 	de Temporada Regular
 * Estadisticas 	Medias 		de Temporara Regular
 * Estadisticas 	Totales 	de Plyaoff
 * Estadisticas 	Medias 		de Playoff
 * Estadisticas 	Totales 	de Temporada
 * Estadisticas 	Medias 		de Temporada
 */

import java.io.IOException;
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

public class CalcularEstadisticasIndividualesTemporadas extends BaseController{
	
	public static void main(String[] args) throws IOException {
		
		// Contador del tiempo que requerimos para realizar todo el proceso
		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");
		
		// Recogemos todos los jugadores
		setListaJugadores(Util.recogerJugadores());
		
		//ArrayList<String> listaJug=new ArrayList<String>();
		//listaJug.add("jamesle01");
		//setListaJugadores(listaJug);
 
		// Conectamos a MongoDB	
		MongoClient mongo = Util.crearConexion();
			
		// Revismos que tenemos la conexión
		if(mongo!=null) {
			
			// Recogemos la Base de Datos
			MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
							
			// Recogemos la colección de "totales"
			MongoCollection<Document> collection = db.getCollection(COLECCION_TOTALES);
			
			// Recorremos las temporadas que tenemos en la clase Temporadas.java
			for(Temporadas temporada : Temporadas.values()) {
				
				// Creamos un documento con la temporada correspondiente
				Document seasonEliminar = new Document(ATRIBUTO_TEMPORADA,temporada.toString());
				
				// Recogemos la lista de partidos que vamos a eliminar
				MongoCursor<Document> listaPartidos = collection.find(seasonEliminar).iterator();
				
	
				// Eliminamos las estadisticas de la temporada correspondiente
				while(listaPartidos.hasNext()) {
					Document partido = (Document)listaPartidos.next();
					System.out.println("Eliminamos: "+partido.get(ATRIBUTO_TEMPORADA)+" "+partido.get(ATRIBUTO_TIEMPO)+" "+partido.get(ATRIBUTO_TIPO_RESULTADO)+" "+partido.get(ATRIBUTO_ID_JUGADOR));
					collection.deleteOne(partido);
				}
			
				// Recorremos la lista de Jugadores
				for(String id:getListaJugadores()) {
					
					// Recogemos la colección de Temporada									
					collection = db.getCollection(temporada.toString());
					
					// LOCAL
					// Creamos un documento de los partidos locales con el id del jugador
					Document findLocal = new Document(ID_JUGADOR_LOCAL,id);
					
					MongoCursor<Document> lista = collection.find(findLocal).iterator();
					ArrayList<ClasePartido> partidos = new ArrayList<ClasePartido>();
					
					// Recogemos los partidos de ese jugador jugando de Local
					while(lista.hasNext()) {
						partidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,ATRIBUTO_LOCAL));
					}
					
					// VISITANTE
					// Creamos un documento de los partidos visitantes con el id del jugador
					Document findVisitante = new Document(ID_JUGADOR_VISITANTE,id);
	
					lista = collection.find(findVisitante).iterator();
					
					// Recogemos los partidos de ese jugador jugando de Visitante
					while(lista.hasNext()) {
						partidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,ATRIBUTO_VISITANTE));
					}
					
					// Miramos si los partidos son de PlayOff o no
					tienePlayOff(partidos);
					
					// Si hay partidos, entramos
					if(!partidos.isEmpty()) {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: "+temporada.toString()+" tiene "+partidos.size()+" partidos para "+id);
						System.out.println("############################################################################");
	
						for(int i=0;i<partidos.size();i++) {
							// Miramos los partidos de Local
							for(int j = 0;j<partidos.get(i).getEquipoLocal().getJugadores().size();j++) {
								// Es partido de PlayOff
								if(partidos.get(i).isPlayOff()) {
									// Miramos si el jugador ha jugado minutos
									if(partidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos()!=null) {
										
										// Sumamos partido de PlayOff y partido de Temporada
										setPartidosPlayOff(getPartidosPlayOff()+1);
										setPartidoTemporada(getPartidoTemporada()+1);
	
										rellenarJugador(getEstadisticasPlayOffTotal(),partidos.get(i).getEquipoLocal().getJugadores().get(j));
										rellenarJugador(getEstadisticasTemporadaTotal(),partidos.get(i).getEquipoLocal().getJugadores().get(j));
										
										if(partidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
											setPartidoPlayOffInicio(getPartidoPlayOffInicio()+1);
											setPartidoTemporada(getPartidoTemporada()+1);
										}
									}
								}else { // Es partido de Temporada Regular
									// Miramos si el jugador ha jugado minutos
									if(partidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos()!=null) {
										
										setPartidosRegular(getPartidosRegular()+1);
										setPartidoTemporada(getPartidoTemporada()+1);
										
										rellenarJugador(getEstadisticasRegularTotal(),partidos.get(i).getEquipoLocal().getJugadores().get(j));
										rellenarJugador(getEstadisticasTemporadaTotal(),partidos.get(i).getEquipoLocal().getJugadores().get(j));
										
										if(partidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
											setPartidoRegularInicio(getPartidoRegularInicio()+1);
											setPartidoTemporadaInicio(getPartidoTemporadaInicio()+1);
										}
									}
								}
							}
							// Miramos los partidos de Visitante
							for(int j = 0;j<partidos.get(i).getEquipoVisitante().getJugadores().size();j++) {
								if(partidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
									// Miramos si el jugador ha jugado minutos
									if(partidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos()!=null) {
										
										// Sumamos partido de PlayOff y partido de Temporada
										setPartidosPlayOff(getPartidosPlayOff()+1);
										setPartidoTemporada(getPartidoTemporada()+1);
										
										rellenarJugador(getEstadisticasPlayOffTotal(),partidos.get(i).getEquipoVisitante().getJugadores().get(j));
										rellenarJugador(getEstadisticasTemporadaTotal(),partidos.get(i).getEquipoVisitante().getJugadores().get(j));
										
										if(partidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
											setPartidoPlayOffInicio(getPartidoPlayOffInicio()+1);
											setPartidoTemporada(getPartidoTemporada()+1);
										}
									}
								}else { // Es partido de Temporada Regular
									if(partidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos()!=null) {
	
										setPartidosRegular(getPartidosRegular()+1);
										setPartidoTemporada(getPartidoTemporada()+1);
										
										rellenarJugador(getEstadisticasRegularTotal(),partidos.get(i).getEquipoVisitante().getJugadores().get(j));
										rellenarJugador(getEstadisticasTemporadaTotal(),partidos.get(i).getEquipoVisitante().getJugadores().get(j));
										
										if(partidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
											setPartidoRegularInicio(getPartidoRegularInicio()+1);
											setPartidoTemporadaInicio(getPartidoTemporadaInicio()+1);
										}
									}
								}
							}
						}
						
	
						/**
						 * Calculamos las canastas de 2 puntos
						 */
						getEstadisticasRegularTotal().getBoxscore().calcularCanastasDosPuntos();
						if(isTemporadaPlayoff()) {
							getEstadisticasPlayOffTotal().getBoxscore().calcularCanastasDosPuntos();
						}
						getEstadisticasTemporadaTotal().getBoxscore().calcularCanastasDosPuntos();
						
						/**
						 * Calculamos los porcentajes de
						 * Tiro libre
						 * Tiros de Campo
						 * Triples
						 */
						getEstadisticasRegularTotal().getBoxscore().calcularPorcentajes();
						if(isTemporadaPlayoff()) {
							getEstadisticasPlayOffTotal().getBoxscore().calcularPorcentajes();
						}
						getEstadisticasTemporadaTotal().getBoxscore().calcularPorcentajes();		
						
						/**
						 * Insertamos los documentos en la colección de Totales
						 */
						MongoCollection<Document> insert = db.getCollection(COLECCION_TOTALES);
						
						insertarDocumento(rellenarDocumentosTotales(id,temporada.toString(),ATRIBUTO_REGULAR,getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
						insertarDocumento(rellenarDocumentosMedia(id,temporada.toString(),ATRIBUTO_REGULAR,getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);						
						
						if(isTemporadaPlayoff()) {
							insertarDocumento(rellenarDocumentosTotales(id,temporada.toString(),ATRIBUTO_PLAYOFF,getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
							insertarDocumento(rellenarDocumentosMedia(id,temporada.toString(),ATRIBUTO_PLAYOFF,getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
						}
						
						insertarDocumento(rellenarDocumentosTotales(id,temporada.toString(),ATRIBUTO_TEMPORADA,getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
						insertarDocumento(rellenarDocumentosMedia(id,temporada.toString(),ATRIBUTO_TEMPORADA,getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
						
						
					}else {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: "+temporada.toString()+" "+id+" NO TIENE PARTIDOS");
						System.out.println("############################################################################");
					}
					
					resetearDatos();
				}
			}
						
		}
		System.out.println("Finalizamos");
		long fin = System.currentTimeMillis();
		
		double tiempo = (double) ((fin - inicio)/1000);
        
        System.out.println("SE HA TARDADO EN DESCARGAR: "+tiempo+" Segundos");
		
	}
}
