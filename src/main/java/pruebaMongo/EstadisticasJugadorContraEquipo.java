package pruebaMongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import Utilidades.Util;
import controller.BaseController;

public class EstadisticasJugadorContraEquipo extends BaseController{
	
	private static String cuarto="boxscore";
	private static String equipo="brk";

	public static void main(String[] args) throws UnknownHostException {

		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			DB db = mongo.getDB("prueba");
			
			DBCollection collection =db.getCollection("partido");
			
			BasicDBObject allQuery = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			
//          EL JUGADOR COMO LOCAL ***************************************************************
			
			obj.add(new BasicDBObject("equipoLocal.jugadores.id", "jamesle01"));
			obj.add(new BasicDBObject("equipoVisitante.nombreAbreviado", equipo));
			allQuery.put("$and", obj);
			
			//allQuery.put("playOff", false); // SI QUEREMOS PLAYOFF
			
			fields.put("_id", 0);
			
			fields.put("equipoLocal.jugadores.id", 1);
			fields.put("playOff", 1);
						
			fields.put("equipoLocal.jugadores."+cuarto, 1);
			fields.put("equipoLocal.jugadores.listaTiros", 1);
			
			DBCursor cursor = collection.find(allQuery,fields);

			int contador = 0;
			while(cursor.hasNext()) {
				contador++;
				DBObject get = cursor.next();
				boolean playoff = (boolean) get.get("playOff");
				ArrayList<BasicDBObject> listaLocales =
						(ArrayList<BasicDBObject>) ((BasicDBObject) get.get("equipoLocal")).get("jugadores");
				//ArrayList<BasicDBObject> listaVisitantes =
						//(ArrayList<BasicDBObject>) ((BasicDBObject) get.get("equipoVisitante")).get("jugadores");
				
				System.out.println("Local en el while: "+contador);
				
			}
			
//          EL JUGADOR COMO VISITANTE ***************************************************************
			
			allQuery = new BasicDBObject();
			fields = new BasicDBObject();
			obj = new ArrayList<BasicDBObject>();
			
			obj.add(new BasicDBObject("equipoVisitante.jugadores.id", "jamesle01"));
			obj.add(new BasicDBObject("equipoLocal.nombreAbreviado", equipo));
			allQuery.put("$and", obj);
			
			//allQuery.put("playOff", false); // SI QUEREMOS PLAYOFF
			
			fields.put("_id", 0);
			
			fields.put("equipoVisitante.jugadores.id", 1);
						
			fields.put("equipoVisitante.jugadores."+cuarto, 1);
			fields.put("equipoVisitante.jugadores.listaTiros", 1);
			
			cursor = collection.find(allQuery,fields);
			
			
			while(cursor.hasNext()) {
				contador++;
				DBObject get = cursor.next();
				ArrayList<BasicDBObject> listaVisitantes =
						(ArrayList<BasicDBObject>) ((BasicDBObject) get.get("equipoVisitante")).get("jugadores");
				
				System.out.println("visitante en el while: "+contador);
				
			}
			
			System.out.println("hemos acabado");
			
		}

	}

}
