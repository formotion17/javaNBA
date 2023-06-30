package Pruebas;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Mapper.MapJavaMongo;
import Modelo.ClasePartido;

public class PartidosDelDia {
	
	@SuppressWarnings("unused")
	private static ArrayList<ClasePartido> listaPartidos = new ArrayList<ClasePartido>();
	
	public static void main(String[] args) throws IOException {

		System.out.println("iniciamos");
				
		//	CONECTAMOS A MONGODB		
		MongoClient mongo = crearConexion();
					
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("prueba");
			
			// Select the collection
			MongoCollection<Document> collection = db.getCollection("partido");

			Document findDocument = new Document("dia","29");
			findDocument.put("mes", "1");
			findDocument.put("year", "2017");

			
			MongoCursor<Document> lista = collection.find(findDocument).iterator();
			
			ArrayList<ClasePartido> listaPartidos = new ArrayList<ClasePartido>();
			
			while(lista.hasNext()) {
				listaPartidos.add(MapJavaMongo.rellenarPartido((Document)lista.next()));
			}

		System.out.println("Finalizamos");
		}
	}
	
	@SuppressWarnings("unused")
	private static void imprimirJugadores(Object nombre,List<Document> jugadores) {
		System.out.println("EQUIPO: "+nombre.toString().toUpperCase());
		for(Document jugador:jugadores) {
			Document jug = (Document) jugador;
			Document box = (Document)jugador.get("boxscore");
			System.out.println(jugador.get("nombre")+" "+jugador.get("apellido")+" : "+box.get("puntos"));
		}
	}
	
	private static MongoClient crearConexion() throws UnknownHostException {
		MongoClient mongo = null;
		mongo = new MongoClient("localhost",27017);
		return mongo;
	}

}
