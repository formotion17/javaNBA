package pruebaMongo;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidades.Util;
import collection.Temporadas;
import controller.BaseController;
import model.ClaseJugador;
import model.ClasePartido;


public class EstadisticasJugador extends BaseController{
	
	
	public static void main(String[] args) throws IOException {
		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");
		
		ArrayList<String> listaJugadores = new ArrayList<String>();
		
		//recogerJugadores();
		listaJugadores = Util.recogerJugadores();
		
		//	CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
					
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("nba");
			
			for(String id:listaJugadores) {
				//String id = "jamesle01";
				
				System.out.println("############################################################################");
				System.out.println(" JUGADOR:  "+id);
				System.out.println("############################################################################");
				for(Temporadas t :Temporadas.values()) {
					// Select the collection
				
					MongoCollection<Document> collection = db.getCollection(t.toString());
					
					// VAMOS A DEVOLVER LAS ESTADISTICAS DE UN JUGADOR DE UNA TEMPORADA
	
					// COMO LOCAL
					Document findLocal = new Document("equipoLocal.jugadores.id",id);
	
					MongoCursor<Document> lista = collection.find(findLocal).iterator();
					
					ArrayList<ClasePartido> listaPartidos = new ArrayList<ClasePartido>();
					
					while(lista.hasNext()) {
						listaPartidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,"local"));
					}
					
					// COMO VISITANTE
					Document findVisitante = new Document("equipoVisitante.jugadores.id",id);
	
					lista = collection.find(findVisitante).iterator();
					
					while(lista.hasNext()) {
						listaPartidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,"visitante"));
					}
					
					tienePlayOff(listaPartidos);
								
					if(!listaPartidos.isEmpty()) {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: "+t.toString()+" tiene "+listaPartidos.size()+" partidos para "+id);
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
											setPartidoTemporadaInicio(getPartidoTemporadaInicio()+1);
											
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
											setPartidoTemporadaInicio(getPartidoTemporadaInicio()+1);
											
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
						MongoCollection<Document> insert = db.getCollection("totales");
						
						insertarDocumento(rellenarDocumentosTotales(id,t.toString(),"regular",getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
						insertarDocumento(rellenarDocumentosMedia(id,t.toString(),"regular",getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
						
						if(isTemporadaPlayoff()) {
							insertarDocumento(rellenarDocumentosTotales(id,t.toString(),"playoff",getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
							insertarDocumento(rellenarDocumentosMedia(id,t.toString(),"playoff",getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
						}
						
						insertarDocumento(rellenarDocumentosTotales(id,t.toString(),"temporada",getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
						insertarDocumento(rellenarDocumentosMedia(id,t.toString(),"temporada",getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
						
						
					}else {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: "+t.toString()+" NO TIENE PARTIDOS");
						System.out.println("############################################################################");
					}
					
					resetearDatos();
				}
				// INSERTAMOS LA CARRERA
				
				MongoCollection<Document> insert = db.getCollection("totales");
				
				insertarDocumento(rellenarDocumentosTotales(id,"carrera","regular",getEstadisticasRegularCarrera(),getPartidosTotalesRegular(),getPartidosTotalesRegularInicio()), insert);
				insertarDocumento(rellenarDocumentosMedia(id,"carrera","regular",getEstadisticasRegularCarrera(),getPartidosTotalesRegular(),getPartidosTotalesRegularInicio()), insert);
				
				insertarDocumento(rellenarDocumentosTotales(id,"carrera","playoff",getEstadisticasPlayOffCarrera(),getPartidosTotalesPlayoff(),getPartidosTotalesPlayoffInicio()),insert);
				insertarDocumento(rellenarDocumentosMedia(id,"carrera","playoff",getEstadisticasPlayOffCarrera(),getPartidosTotalesPlayoff(),getPartidosTotalesPlayoffInicio()),insert);
				
				insertarDocumento(rellenarDocumentosTotales(id,"carrera","temporada",getEstadisticasTemporadaCarrera(),getPartidosTotalesTemporada(),getPartidosTotalesTemporadaInicio()),insert);
				insertarDocumento(rellenarDocumentosMedia(id,"carrera","temporada",getEstadisticasTemporadaCarrera(),getPartidosTotalesTemporada(),getPartidosTotalesTemporadaInicio()),insert);
				
				resetearTotalesCarrera();
				
			}	// END DEL FOR
			
		System.out.println("Finalizamos");
		long fin = System.currentTimeMillis();
		
		double tiempo = (double) ((fin - inicio)/1000);
        
        System.out.println("SE HA TARDADO EN DESCARGAR: "+tiempo+" Segundos");
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
	}
}
