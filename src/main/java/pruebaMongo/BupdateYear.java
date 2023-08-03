package pruebaMongo;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidad.Util;
import Controlador.BaseController;
import Modelo.ClaseJugador;
import Modelo.ClasePartido;

public class BupdateYear extends BaseController{
	
	public static void main(String[] args) throws IOException {
		
		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");
		
		//recogerJugadores();
		setListaJugadores(Util.recogerJugadores());
 
//		CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
			
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("nba");
								
			MongoCollection<Document> collection = db.getCollection("totales");
			
			Document seasonEliminar = new Document("temporada","season20192020");
			
			
			MongoCursor<Document> listaPartidos = collection.find(seasonEliminar).iterator();
			

			// Eliminamos los partidos de la temporada
			while(listaPartidos.hasNext()) {
				Document partido = (Document)listaPartidos.next();
				System.out.println("Eliminamos: "+partido.get("temporada")+" "+partido.get("tiempo")
				+" "+partido.get("tiporesultado")+" "+partido.get("idjugador"));
				collection.deleteOne(partido);
			}
			
			// Hacemos la busqueda de la temporada y hacemos insersi�n de lo nuevo
			
				for(String id:getListaJugadores()) {
					collection = db.getCollection("season20192020");
					
					// LOCAL
					Document findLocal = new Document("equipoLocal.jugadores.id",id);
					MongoCursor<Document> lista = collection.find(findLocal).iterator();
					ArrayList<ClasePartido> partidos = new ArrayList<ClasePartido>();
					
					while(lista.hasNext()) {
						partidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,"local"));
					}
					
					// VISITANTE
					// COMO VISITANTE
					Document findVisitante = new Document("equipoVisitante.jugadores.id",id);
	
					lista = collection.find(findVisitante).iterator();
					
					while(lista.hasNext()) {
						partidos.add(MapJugadorEstadisticas.rellenarJuegadorEstadisticas((Document)lista.next(),id,"visitante"));
					}
					
					tienePlayOff(partidos);
					
					if(!partidos.isEmpty()) {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: Season2019 2020 tiene "+partidos.size()+" partidos para "+id);
						System.out.println("############################################################################");

						for(int i=0;i<partidos.size();i++) {
							for(int j = 0;j<partidos.get(i).getEquipoLocal().getJugadores().size();j++) {
								if(partidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
									if(partidos.get(i).getEquipoLocal().getJugadores().get(j).getSegundos()!=null) {
										
										setPartidosPlayOff(getPartidosPlayOff()+1);
										setPartidoTemporada(getPartidoTemporada()+1);

										rellenarJugador(getEstadisticasPlayOffTotal(),partidos.get(i).getEquipoLocal().getJugadores().get(j));
										rellenarJugador(getEstadisticasTemporadaTotal(),partidos.get(i).getEquipoLocal().getJugadores().get(j));
										
										if(partidos.get(i).getEquipoLocal().getJugadores().get(j).getInicio()) {
											setPartidoPlayOffInicio(getPartidoPlayOffInicio()+1);
											setPartidoTemporada(getPartidoTemporada()+1);
										}
									}
								}else { // NOOO ESTAMOS EN PLAYOFF
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
							for(int j = 0;j<partidos.get(i).getEquipoVisitante().getJugadores().size();j++) {
								if(partidos.get(i).isPlayOff()) { // ESTAMOS EN PLAYOFF
									if(partidos.get(i).getEquipoVisitante().getJugadores().get(j).getSegundos()!=null) {

										setPartidosPlayOff(getPartidosPlayOff()+1);
										setPartidoTemporada(getPartidoTemporada()+1);
										
										rellenarJugador(getEstadisticasPlayOffTotal(),partidos.get(i).getEquipoVisitante().getJugadores().get(j));
										rellenarJugador(getEstadisticasTemporadaTotal(),partidos.get(i).getEquipoVisitante().getJugadores().get(j));
										
										if(partidos.get(i).getEquipoVisitante().getJugadores().get(j).getInicio()) {
											setPartidoPlayOffInicio(getPartidoPlayOffInicio()+1);
											setPartidoTemporada(getPartidoTemporada()+1);
										}
									}
								}else { // NOOO ESTAMOS EN PLAYOFF
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
						 * Insertamos los documentos en la colecci�n de Totales
						 */
						MongoCollection<Document> insert = db.getCollection("totales");
						
						insertarDocumento(rellenarDocumentosTotales(id,"season20192020","regular",getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);
						insertarDocumento(rellenarDocumentosMedia(id,"season20192020","regular",getEstadisticasRegularTotal(),getPartidosRegular(),getPartidoRegularInicio()), insert);						
						
						if(isTemporadaPlayoff()) {
							insertarDocumento(rellenarDocumentosTotales(id,"season20192020","playoff",getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
							insertarDocumento(rellenarDocumentosMedia(id,"season20192020","playoff",getEstadisticasPlayOffTotal(),getPartidosPlayOff(),getPartidoPlayOffInicio()),insert);
						}
						
						insertarDocumento(rellenarDocumentosTotales(id,"season20192020","temporada",getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
						insertarDocumento(rellenarDocumentosMedia(id,"season20192020","temporada",getEstadisticasTemporadaTotal(),getPartidoTemporada(),getPartidoTemporadaInicio()),insert);
						
						
					}else {
						System.out.println("############################################################################");
						System.out.println(" TEMPORADA: "+"season20192020"+" NO TIENE PARTIDOS");
						System.out.println("############################################################################");
					}
					
					resetearDatos();
				}
						
		}
		System.out.println("Finalizamos");
		long fin = System.currentTimeMillis();
		
		double tiempo = (double) ((fin - inicio)/1000);
        
        System.out.println("SE HA TARDADO EN DESCARGAR: "+tiempo+" Segundos");
		
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
