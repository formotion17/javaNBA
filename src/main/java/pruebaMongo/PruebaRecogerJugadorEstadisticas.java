package pruebaMongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import Utilidad.Util;
import Controlador.BaseController;

public class PruebaRecogerJugadorEstadisticas extends BaseController{

	private static int atributo=0;

	private static String cuarto="boxscore";
	private static String busqueda="";
	private static String busqueda2="";
	
	
	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
            
			busqueda="tirosCampoMetidos";
			busqueda2="tirosCampoIntentados";
			
			DB db = mongo.getDB("prueba");
			
			DBCollection collection =db.getCollection("season20192020");
			
			BasicDBObject allQuery = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("equipoLocal.jugadores.id", "willizi01"));
			obj.add(new BasicDBObject("equipoVisitante.jugadores.id", "willizi01"));
			allQuery.put("$or", obj);
			allQuery.put("playOff", false);
			fields.put("_id", 0);
			
			fields.put("equipoLocal.jugadores.id", 1);
			fields.put("equipoVisitante.jugadores.id", 1);
						
			fields.put("equipoLocal.jugadores."+cuarto+"."+busqueda, 1);
			fields.put("equipoVisitante.jugadores."+cuarto+"."+busqueda, 1);
			
			fields.put("equipoLocal.jugadores."+cuarto+"."+busqueda2, 1);
			fields.put("equipoVisitante.jugadores."+cuarto+"."+busqueda2, 1);
			
			DBCursor cursor = collection.find(allQuery,fields);

			while(cursor.hasNext()) {
				DBObject get = cursor.next();
				ArrayList<BasicDBObject> listaLocales =
						(ArrayList<BasicDBObject>) ((BasicDBObject) get.get("equipoLocal")).get("jugadores");
				ArrayList<BasicDBObject> listaVisitantes =
						(ArrayList<BasicDBObject>) ((BasicDBObject) get.get("equipoVisitante")).get("jugadores");
				
				sumarAtributo(listaLocales);
				sumarAtributo(listaVisitantes);
				
			}
			
			System.out.println("PUNTOS TOTALES: "+atributo);
		}

	}
	
	private static void sumarAtributo(ArrayList<BasicDBObject> lista) {
		for(BasicDBObject jugador:lista) {
			if("willizi01".equals(jugador.get("id"))) {
				BasicDBObject puntos = (BasicDBObject) jugador.get(cuarto);
				if(puntos!=null) {
					System.out.println("Lebron James Puntos: "+puntos.get(busqueda)+" de "+puntos.get(busqueda2));
					if(puntos.get("tirosCampoMetidos")!=null) {
						atributo=atributo + (Integer)puntos.get(busqueda);
					}
				}
			}
		}
	}

}
