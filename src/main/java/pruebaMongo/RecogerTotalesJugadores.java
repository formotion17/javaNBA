package pruebaMongo;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidades.Util;
import controller.BaseController;
import model.ClaseEstadisticaNormalTotales;

public class RecogerTotalesJugadores extends BaseController{

	public static void main(String[] args) throws UnknownHostException {

		long inicio = System.currentTimeMillis();
		System.out.println("iniciamos");
		
		String id ="jamesle01";
		
		ArrayList<ClaseEstadisticaNormalTotales> listaTotales = new ArrayList<ClaseEstadisticaNormalTotales>();
		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("prueba");
			
			MongoCollection<Document> collection = db.getCollection("totales");
			
			Document findStatsPlayer = new Document("idjugador","jamesle01");
			
			MongoCursor<Document> listaStats = collection.find(findStatsPlayer).iterator();
			
			while(listaStats.hasNext()) {
				listaTotales.add(MapJugadorEstadisticas.devolverEstadisticasTotalesJugador((Document)listaStats.next()));
			}
			
			System.out.println("Tenemos las temporadas de Lebron James");
		}

	}

}
