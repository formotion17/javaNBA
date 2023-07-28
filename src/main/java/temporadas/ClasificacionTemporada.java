package temporadas;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Coleccion.ListaEquipos;
import Coleccion.Temporadas;
import Controlador.BaseController;
import Utilidad.Util;

public class ClasificacionTemporada extends BaseController{
	
	private static ArrayList<Equipo> listaEquipos = new ArrayList<>();
	private static MongoClient mongo;
	private static MongoDatabase db;
	
	public static void main(String[] args) throws UnknownHostException{
		
		System.out.println("INICAMOS LA APLICACION PARA LAS CLASIFICACIONES DE TEMPORADA");
		
		mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			// Recogemos los equipos de la tempoarada, pasamos el year que empieza la temporada
			// ya que si es a partir de 2004/2005 empiezan las nuevas divisiones
			recogerEquiposTemporada("season20002001",2000);
			
			MongoCursor<Document> listaPartidos=devolverPartidos("season20002001");

            while (listaPartidos.hasNext()) {
                Document document = listaPartidos.next();
                
                Equipo equipoLocal = devolverEquipo((String) ((Document) document.get("equipoLocal")).get("nombre"));
                int tanteoLocal = Integer.parseInt((String) ((Document) document.get("equipoLocal")).get("tanteo"));
                
                Equipo equipoVisitante = devolverEquipo((String) ((Document) document.get("equipoVisitante")).get("nombre"));                
                int tanteoVisitante = Integer.parseInt((String) ((Document) document.get("equipoVisitante")).get("tanteo"));
                
                if(tanteoLocal>tanteoVisitante) {
                	
                	equipoLocal.addVictoriaLocal();
                	equipoLocal.addVictoria();
                	equipoVisitante.addDerrotaVisitante();
                	equipoVisitante.addDerrota();
                	
                	if(equipoLocal.getDivision().equals(equipoVisitante.getDivision())) {
                    	equipoLocal.addVictoriaDivisionLocal();
                    	equipoVisitante.addDerrotaDivisionVisitante();
                	}
                	if(equipoLocal.getConferencia().equals(equipoVisitante.getConferencia())) {
                		equipoLocal.addVictoriaConferenciaLocal();
                		equipoVisitante.addDerrotaConferenciaVisitante();
                	}
                	
                }else{

                	equipoLocal.addDerrotaLocal();
                	equipoVisitante.addVictoriaVisitante();
                	equipoLocal.addDerrota();
                	equipoVisitante.addVictoria();
                	
                	if(equipoLocal.getDivision().equals(equipoVisitante.getDivision())) {
                    	equipoLocal.addDerrotaDivisionLocal();
                    	equipoVisitante.addVictoriaDivisionVisitante();
                	}
                	if(equipoLocal.getConferencia().equals(equipoVisitante.getConferencia())) {
                		equipoLocal.addDerrotaConferenciaLocal();
                		equipoVisitante.addVictoriaConferenciaVisitante();
                	}
                }
            }
		}
		
		System.out.println("paramos");
		System.out.println(listaEquipos.get(0).toString());
	}
	
	private static MongoCursor<Document> devolverPartidos(String temporada){
		MongoCollection<Document> collection = db.getCollection(temporada);
        
        // Crear el filtro para la consulta
        Document filter = new Document("playOff", false);
		
		// Realizar la consulta para recuperar solo el campo "nombre"
        MongoCursor<Document> cursor = collection.find(filter).projection(
        		new Document("equipoLocal.nombre", 1)
        		.append("equipoLocal.tanteo",1)
        		.append("equipoVisitante.nombre", 1)
        		.append("equipoVisitante.tanteo", 1)
        		).iterator();
        return cursor;
	}
	
	private static void recogerEquiposTemporada(String temporada,int year) {
		System.out.println("	TEMPORADA: "+temporada+"\n");
		
		db = mongo.getDatabase("NBA");
		
		MongoCursor<String> c = db.getCollection(temporada).
				distinct("equipoLocal.nombre", String.class).iterator();
		
		
		while(c.hasNext()) {
			String nombreEquipo = c.next().replace("/", " ");
			if(year<2004) {
				
				listaEquipos.add(new Equipo(nombreEquipo,
								ListaEquipos.findConferenciaAntiguaByTeam(nombreEquipo),
								ListaEquipos.findDivisionAntiguaByTeam(nombreEquipo)));
			}else {
				listaEquipos.add(new Equipo(nombreEquipo,
						ListaEquipos.findConferenciaNuevaByTeam(nombreEquipo),
						ListaEquipos.findDivisionNuevaByTeam(nombreEquipo)));
			}
		}
	}

	private static Equipo devolverEquipo(String nombre) {
		for(Equipo equipo:listaEquipos) {
			if(nombre.equals(equipo.getNombre())) {
				return equipo;
			}
		}
		return null;
	}
	
	
	
	
	
}
