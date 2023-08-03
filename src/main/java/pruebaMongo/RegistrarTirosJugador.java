package pruebaMongo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bson.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidad.Util;
import Coleccion.Temporadas;
import Controlador.BaseController;
import Modelo.ClaseJugadorDatos;
import Modelo.ClaseJugadorTiros;

public class RegistrarTirosJugador extends BaseController{
	
	private static String coleccionTiros="tiros";

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		long inicio = System.currentTimeMillis();
		
		System.out.println("INICIAMOS");
		
		ArrayList<String> listaJugadores = Util.recogerJugadores();
		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("nba");
			
			ArrayList<ClaseJugadorTiros> listaTirosPartidos = new ArrayList<ClaseJugadorTiros>();
			
			ClaseJugadorDatos jugador = new ClaseJugadorDatos();
			
			for(String id:listaJugadores) {
				
				System.out.println("\nEmpezamos con "+id);
				
				jugador.setIdJugador(id);
				
				for(Temporadas t : Temporadas.values()) {
					
					MongoCollection<Document> collection =db.getCollection(t.toString());
					
					Document find = new Document("equipoLocal.jugadores.id",id);
	
					MongoCursor<Document> lista = collection.find(find).iterator();
					
					while(lista.hasNext()) {
						listaTirosPartidos.add(MapJugadorEstadisticas.rellenarTirosPartidoJugador((Document)lista.next(),id,true,t.toString()));
					}
					
					find = new Document("equipoVisitante.jugadores.id",id);
					
					lista = collection.find(find).iterator();

					while(lista.hasNext()) {
						listaTirosPartidos.add(MapJugadorEstadisticas.rellenarTirosPartidoJugador((Document)lista.next(),id,false,t.toString()));
					}
					
				}

				jugador.setListaTiros(listaTirosPartidos);
				
		        ObjectMapper Obj = new ObjectMapper();
		        
	            String jsonStr = Obj.writeValueAsString(jugador);
	            
	            Document doc = Document.parse(jsonStr);
	            
	            MongoCollection<Document> coleccion =db.getCollection(coleccionTiros);
	            
	            insertarDocumento(doc, coleccion);
				
				System.out.println("Finalizamos "+id+ "\n");
				
				listaTirosPartidos.clear();
				
			}		
			
		}
		
		System.out.println("FINALIZAMOS TODOS LOS JUGADORES");
		
		long fin = System.currentTimeMillis();
		
		double tiempo = (double) ((fin - inicio)/1000);
        
        System.out.println("SE HA TARDADO EN DESCARGAR: "+tiempo+" Segundos");
		
	}

}
