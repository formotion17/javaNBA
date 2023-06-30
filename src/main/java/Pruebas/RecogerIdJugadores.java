package Pruebas;

/**
 * Clase para recoger los distintos ID de jugador
 */

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import Controlador.BaseController;
import Mapper.Atributos;
import Utilidad.Util;

public class RecogerIdJugadores extends BaseController{

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("INICIAMOS LA APLICACI�N PARA ERCOGER LOS DIFERENTES ID'S QUE HAY");
		
		// CONECTAMOS A MONGODB
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
			
			MongoCursor<String> c = db.getCollection(ATRIBUTO_PARTIDO).distinct(ID_JUGADOR_LOCAL, String.class).iterator();
						
			while(c.hasNext()) {
				String id=c.next();
				
				Document findDocument = new Document(EQUIPOLOCAL_JUGADORES_ID,id);

		        MongoCursor<Document> nuevaLista = db.getCollection(ATRIBUTO_PARTIDO).find(findDocument)
		        		.limit(1)
		        		.projection(Projections.fields(
		        				Projections.include(
		        						EQUIPOLOCAL_JUGADORES_ID ,
		        						EQUIPOLOCAL_JUGADORES_NOMBRE,
		        						EQUIPOLOCAL_JUGADORES_APELLIDO)))
		        		.iterator();
		        
		        while(nuevaLista.hasNext()) {
		        	Document doc = (Document) nuevaLista.next().get(ATRIBUTO_EQUIPO_LOCAL);
		        	ArrayList<Document> listaJugadores = (ArrayList<Document>) doc.get(ATRIBUTO_JUGADORES); 
		        	for(Document jugador: listaJugadores) {
		        		if(id.equals(jugador.get(ATRIBUTO_ID))) {
		        			
		        			// apellido / id / nombre
			        		//System.out.println(jugador.get(ATRIBUTO_APELLIDO)+"/"+id+"/"+jugador.get(ATRIBUTO_NOMBRE));
		        			
			        		// id
			        		System.out.println(id);
			        		break;
		        		}
		        	}
		        }		        
			}
			
		}	
		
	}

}
