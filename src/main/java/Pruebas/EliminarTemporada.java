package Pruebas;

import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidad.Util;
import Coleccion.Temporadas;
import Controlador.BaseController;

public class EliminarTemporada extends BaseController{

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub

		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			MongoDatabase db = mongo.getDatabase("prueba");
			
			for(@SuppressWarnings("unused") Temporadas season:Temporadas.values()) {
				MongoCollection<Document> collection = db.getCollection("partido");
				
				for(int i=2000 ;i<2020;i++) {
					System.out.println("Recojemos el año: "+i);
					Document seasonEliminar = new Document("year",Integer.toString(i));
//					seasonEliminar.put("mes", "2");
					
					
					MongoCursor<Document> listaPartidos = collection.find(seasonEliminar).iterator();
					

					// Eliminamos los partidos de la temporada
					while(listaPartidos.hasNext()) {
						Document partido = (Document)listaPartidos.next();
						System.out.println("Eliminamos: "+partido.get("temporada")+" "+partido.get("tiempo")+" "+partido.get("tiporesultado")+" "+partido.get("idjugador"));
						collection.deleteOne(partido);
					}
					System.out.println("FINALIZAMOS el año: "+i);
				}
				
			}
			
			System.out.println("FINALIZAMOS TODOS LOS AÑOS");
		}
	}

}
