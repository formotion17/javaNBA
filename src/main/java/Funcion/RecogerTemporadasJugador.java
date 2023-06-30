package Funcion;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Controlador.BaseController;
import Utilidad.Util;

public class RecogerTemporadasJugador extends BaseController{
	
	public static void main(String[] args) throws UnknownHostException {
		System.out.println("INICIAMOS LA APLICACIÓN PARA RECOGER LAS DIFERENTES TEMPORADAS DE UN JUGADOR");
		
		// CONECTAMOS A MONGODB
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
			
			MongoCursor<String> c = db.getCollection(COLECCION_TOTALES).distinct(ATRIBUTO_TEMPORADA,new BasicDBObject(ATRIBUTO_ID_JUGADOR, "jamesle01"), String.class).iterator();
			
			while(c.hasNext()) {
				System.out.println(c.next());
			}
			
		}
	}

}
