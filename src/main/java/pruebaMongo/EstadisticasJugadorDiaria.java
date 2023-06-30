package pruebaMongo;

import java.io.File;
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


public class EstadisticasJugadorDiaria extends BaseController{
	
	private static MongoDatabase db;
	private static String temporada = "season20202021";
	
	
	public static void main(String[] args) throws IOException {
		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");
		
		ArrayList<String> listaJugadores = new ArrayList<String>();
		
		//recogerJugadores();
		listaJugadores = Util.recogerJugadoresDiaria();
		//listaJugadores.add("jamesle01");
		
		//	CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
					
		if(mongo!=null) {
			
			db = mongo.getDatabase("nba");
			
			for(String id:listaJugadores) {
				
				System.out.println("############################################################################");
				System.out.println(" JUGADOR:  "+id);
				System.out.println("############################################################################");
				
				
				MongoCollection<Document> collection = db.getCollection("totales");
				
				// ELIMINAMOS LOS PARTIDOS DE LA TEMPORADA
				Document estadisticasEliminar = new Document("idjugador",id);
				estadisticasEliminar.put("temporada", temporada);
				
				MongoCursor<Document> listaEstadisticas = collection.find(estadisticasEliminar).iterator();				

				while(listaEstadisticas.hasNext()) {
					Document partido = (Document)listaEstadisticas.next();
					System.out.println("Eliminamos: "+partido.get("temporada")+" "+partido.get("tiempo")+" "+partido.get("tiporesultado")+" "+partido.get("idjugador"));
					collection.deleteOne(partido);
				}
				
				collection = db.getCollection(temporada.toString());
				
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
					MongoCollection<Document> insert = db.getCollection("totales");
					
					insertarDocumento(rellenarDocumentosTotales(id,temporada,"regular",getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
					insertarDocumento(rellenarDocumentosMedia(id,temporada,"regular",getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
					
					if(isTemporadaPlayoff()) {
						insertarDocumento(rellenarDocumentosTotales(id,temporada,"playoff",getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
						insertarDocumento(rellenarDocumentosMedia(id,temporada,"playoff",getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
					}
					
					insertarDocumento(rellenarDocumentosTotales(id,temporada,"temporada",getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
					insertarDocumento(rellenarDocumentosMedia(id,temporada,"temporada",getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
					
					
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

				MongoCollection<Document> collectionEstadisticas = db.getCollection("totales");
				
				for(Temporadas t :Temporadas.values()) {
					
					System.out.println("TEMPORADA	"+t.toString());
					
					recorrerTemporada(collectionEstadisticas,id,t.toString(),"regular",getEstadisticasRegularCarrera(),1,"REGULAR");
					recorrerTemporada(collectionEstadisticas,id,t.toString(),"playoff",getEstadisticasPlayOffCarrera(),2,"PLAYOFF");
					recorrerTemporada(collectionEstadisticas,id,t.toString(),"temporada",getEstadisticasTemporadaCarrera(),3,"TEMPORADA");
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
        
        borrarArchivoId();
        
		}

	}
	
	private static void recorrerTemporada(MongoCollection<Document> collection, String id, String temporada, String tiempo, ClaseJugador claseJugador, int i, String tipo) {
		
		Document doc = new Document("idjugador",id);
		doc.put("temporada", temporada);
		doc.put("tiempo", tiempo);
		doc.put("tiporesultado", "total");
		
		MongoCursor<Document>  lista = collection.find(doc).iterator();
		
		while(lista.hasNext()) {
			Document partido = (Document)lista.next();
			rellenarJugador(claseJugador, MapJugadorEstadisticas.devolverEstadisticasTotalesJugador(partido),i);
			System.out.println("	"+tipo);
		}
		
		
	}
	private static void borrarEstadisticasCarrera(String id) {
		
		MongoCollection<Document> collection = db.getCollection("totales");
		
		//Eliminamos las estadisticas de la temporada actual
		Document estadisticasEliminar = new Document("idjugador",id);
		estadisticasEliminar.put("temporada", "carrera");
		
		
		MongoCursor<Document> listaEstadisticas = collection.find(estadisticasEliminar).iterator();
		

		// Eliminamos los partidos de la temporada
		while(listaEstadisticas.hasNext()) {
			Document partido = (Document)listaEstadisticas.next();
			System.out.println("Eliminamos: "+partido.get("temporada")+" "+partido.get("tiempo")+" "+partido.get("tiporesultado")+" "+partido.get("idjugador"));
			collection.deleteOne(partido);
		}
	}

	private static void borrarArchivoId() {
		File fichero = new File("G://TFG//Web//Eclipse//pruebaMongo//src//pruebaMongo//nuevosId.txt");
		if (fichero.delete())
			   System.out.println("El fichero ha sido borrado satisfactoriamente");
			else
			   System.out.println("El fichero no puede ser borrado");
		
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
		setPartidosTotalesRegular(0);
		setPartidosTotalesPlayoff(0);
		setPartidosTotalesTemporada(0);
		setPartidosTotalesRegularInicio(0);
		setPartidosTotalesPlayoffInicio(0);
		setPartidosTotalesTemporadaInicio(0);
	}
}
