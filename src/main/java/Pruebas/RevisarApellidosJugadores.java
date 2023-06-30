package Pruebas;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Coleccion.Temporadas;
import Controlador.BaseController;
import Utilidad.Util;

public class RevisarApellidosJugadores extends BaseController{

	private static ArrayList<String> listaJugadores=new ArrayList<String>();
	
	public static void main(String[] args) throws UnknownHostException {
		
		
		System.out.println("INICIAMOS LA APLICACIÓN PARA ERCOGER LOS DIFERENTES ID'S QUE HAY");
		
		// CONECTAMOS A MONGODB
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
						
			for(Temporadas t:Temporadas.values()) {
				MongoCursor<String> c = db.getCollection(t.toString()).distinct("equipoLocal.jugadores.apellido", String.class).iterator();
				
				while(c.hasNext()) {
					String apellido=c.next();
					if(apellido.contains(" ")) {
						if(existeJugador(apellido)) {
							System.out.println(t);
							listaJugadores.add(apellido);
						};
					}
				}
			}
			
			for(int i=0;i<listaJugadores.size();i++) {
				System.out.println(listaJugadores.get(i));
			}
			
			
		}

	}
	
	public static boolean existeJugador(String jugador) {
		for(int i=0;i<listaJugadores.size();i++) {
			if(listaJugadores.get(i).equals(jugador)) {
				return false;
			}
		}
		
		return true;
	}

}
