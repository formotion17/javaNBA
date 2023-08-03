package pruebaMongo;

import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidad.Util;

public class EliminarTotalesJugador {

	public static void main(String[] args) throws UnknownHostException {
		int cont=0;
//		CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
		
		MongoDatabase db = mongo.getDatabase("nba");
		// TODO Auto-generated method stub
		MongoCollection<Document> collection = db.getCollection("totales");
		
		//Eliminamos las estadisticas de la temporada actual
		Document estadisticasEliminar = new Document("idJugador","jamesle01");
		
		
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
