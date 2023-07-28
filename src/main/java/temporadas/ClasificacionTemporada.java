package temporadas;

import java.net.UnknownHostException;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Coleccion.ListaEquipos;
import Coleccion.Temporadas;
import Controlador.BaseController;
import Utilidad.Util;

public class ClasificacionTemporada extends BaseController{
	
	public static void main(String[] args) throws UnknownHostException{
		
		System.out.println("INICAMOS LA APLICACION PARA LAS CLASIFICACIONES DE TEMPORADA");
		
		MongoClient mongo = Util.crearConexion();
		
		DBCursor cursor;
		int i=0;
		
		if(mongo!=null) {
			
			for(Temporadas temporadas : Temporadas.values()) {
				
				i++;
				String temporada =temporadas.toString();
				System.out.println(i+"	TEMPORADA: "+temporada+"\n");
				
				MongoDatabase db = mongo.getDatabase("NBA");
				
				MongoCursor<String> c = db.getCollection(temporada).
						distinct("equipoLocal.nombre", String.class).iterator();
				
				if("season20052006".equals(temporada)) {
					System.out.println("paramos");
				}
				while(c.hasNext()) {
					String nombreEquipo = c.next().replace("/", " ");
					System.out.println(nombreEquipo);
					if(i<=4) {
						System.out.println(" Conferencia: "+ListaEquipos.findConferenciaAntiguaByTeam(nombreEquipo)+
								"	Division: "+ListaEquipos.findDivisionAntiguaByTeam(nombreEquipo)+"	"+nombreEquipo);
					}else {
						System.out.println(" Conferencia: "+ListaEquipos.findConferenciaNuevaByTeam(nombreEquipo)+
								"	Division: "+ListaEquipos.findDivisionNuevaByTeam(nombreEquipo)+"	"+nombreEquipo);
					}
				}
				
				System.out.println("\n\n");
			}
			
				
		}
	}

}
