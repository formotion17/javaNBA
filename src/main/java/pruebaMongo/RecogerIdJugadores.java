package pruebaMongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidad.Util;

public class RecogerIdJugadores {
	

	

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("INICIAMOS LA APLICACI�N PARA ERCOGER LOS DIFERENTES ID'S QUE HAY");
		
		ArrayList<String> listaIdJugadores = new ArrayList<String>();
		
		// CONECTAMOS A MONGODB
		DBCursor cursor;
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("NBA");
			
			MongoCursor<String> c = db.getCollection("partidos").distinct("equipoLocal.jugadores.id", String.class).iterator();
			
			while(c.hasNext()) {

				DB dba = mongo.getDB("nba");
				DBCollection col =dba.getCollection("partido");

			    BasicDBObject allQuery = new BasicDBObject();
			    BasicDBObject fields = new BasicDBObject();
			    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			    
				String id=c.next();
				System.out.println(id);
				
				obj.add(new BasicDBObject("equipoLocal.jugadores.id", id));
		        allQuery.put("$and", obj);

		        fields.put("_id", 0);

		        fields.put("equipoLocal.jugadores.id", 1);
		        fields.put("equipoLocal.jugadores.nombre", 1);
		        fields.put("equipoLocal.jugadores.apellido", 1);

		        cursor = col.find(allQuery,fields).limit(1);
		        if(cursor.hasNext()) {
		        	DBObject get = cursor.next();
			        
			        ArrayList<BasicDBObject> a =(ArrayList<BasicDBObject>) ((BasicDBObject) get.get("equipoLocal")).get("jugadores");
			        
			        for(int i = 0;i<a.size();i++){
			        	if(a.get(i).get("id").equals(id)){
			        		System.out.println(a.get(i).get("apellido")+"/"+id+"/"+a.get(i).get("nombre"));
			        		break;
			        	}
			        }
		        }
		        
			}
			
		}
		
		
	}

}
