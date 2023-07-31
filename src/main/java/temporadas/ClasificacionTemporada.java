package temporadas;

import java.net.UnknownHostException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Coleccion.ListaEquipos;
import Controlador.BaseController;
import Utilidad.Util;

public class ClasificacionTemporada extends BaseController{
	
	private static ArrayList<Equipo> listaEquipos = new ArrayList<>();
	private static MongoClient mongo;
	private static MongoDatabase db;
	
	public static void main(String[] args) throws UnknownHostException{
		
		System.out.println("INICAMOS LA APLICACION PARA LAS CLASIFICACIONES DE TEMPORADA");
		
		mongo = Util.crearConexion();
		
		Map.Entry<String, Integer> tuple = new AbstractMap.SimpleEntry<>("season20002001", 2000);
		
		String orden="conferencia"; // conferencia / division
		
		if(mongo!=null) {
			
			// Recogemos los equipos de la tempoarada, pasamos el year que empieza la temporada
			// ya que si es a partir de 2004/2005 empiezan las nuevas divisiones
			recogerEquiposTemporada(tuple.getKey(),tuple.getValue());
			
			MongoCursor<Document> listaPartidos=devolverPartidos(tuple.getKey());

            while (listaPartidos.hasNext()) {
            	operarPartido(listaPartidos.next());
            }
            
            ordenarPartidos(tuple.getValue(),orden);

		}
	}
	
	private static void ordenarPartidos(int year,String orden) {
		
		if(DIVISION.equals(orden)) {
			if(year<2004) {
				ordenarDivisionAntiguos();
			}else {
				ordenarDivisionNuevos();
			}
		}else {
			ordenarConferencia();
		}
	}
	
	private static void ordenarConferencia(){
		List<Equipo> equiposConferenciaEste = obtenerEquiposConferencia(CONFERENCIA_ESTE);
        List<Equipo> equiposConferenciaOeste = obtenerEquiposConferencia(CONFERENCIA_OESTE);
        
        Collections.sort(equiposConferenciaEste, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposConferenciaOeste, Comparator.comparingInt(Equipo::getVictorias).reversed());
        
        imprimirClasificacion(equiposConferenciaEste,CONFERENCIA_ESTE,CONFERENCIA);
        imprimirClasificacion(equiposConferenciaOeste,CONFERENCIA_OESTE,CONFERENCIA);        
        
        imprimirBracket(equiposConferenciaEste);
        imprimirBracket(equiposConferenciaOeste);
        
	}
	
	private static void imprimirBracket(List<Equipo> conferencia){
		System.out.println(String.format("\t%-35s -\t%-35s",conferencia.get(0).getNombre(),conferencia.get(7).getNombre()));
		System.out.println(String.format("\t%-35s -\t%-35s",conferencia.get(3).getNombre(),conferencia.get(4).getNombre()));
		System.out.println(String.format("\t%-35s -\t%-35s",conferencia.get(2).getNombre(),conferencia.get(5).getNombre()));
		System.out.println(String.format("\t%-35s -\t%-35s",conferencia.get(1).getNombre(),conferencia.get(6).getNombre()));
	}
	private static void ordenarDivisionAntiguos() {
		
		// Filtrar los equipos que tienen la conferencia deseada
        List<Equipo> equiposDivisionAtlantico = obtenerEquiposDivision(DIVISION_ATLANTICO);
        List<Equipo> equiposDivisionCentral = obtenerEquiposDivision(DIVISION_CENTRAL);
        
        List<Equipo> equiposDivisionMedioOeste = obtenerEquiposDivision(DIVISION_MEDIO_OESTE);
        List<Equipo> equiposDivisionPacifico = obtenerEquiposDivision(DIVISION_PACIFICO);
        
     // Ordenar la lista de equipos por victorias en orden descendente
        Collections.sort(equiposDivisionAtlantico, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionCentral, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionMedioOeste, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionPacifico, Comparator.comparingInt(Equipo::getVictorias).reversed());
        
        imprimirClasificacion(equiposDivisionAtlantico,DIVISION_ATLANTICO,DIVISION);
        imprimirClasificacion(equiposDivisionCentral,DIVISION_CENTRAL,DIVISION);
        imprimirClasificacion(equiposDivisionMedioOeste,DIVISION_MEDIO_OESTE,DIVISION);
        imprimirClasificacion(equiposDivisionPacifico,DIVISION_PACIFICO,DIVISION);
	}
	
	private static void ordenarDivisionNuevos() {
		// Filtrar los equipos que tienen la conferencia deseada
        List<Equipo> equiposDivisionAtlantico = obtenerEquiposDivision(DIVISION_ATLANTICO);
        List<Equipo> equiposDivisionCentral = obtenerEquiposDivision(DIVISION_CENTRAL);
        List<Equipo> equiposDivisionSureste = obtenerEquiposDivision(DIVISION_SURESTE);
        
        List<Equipo> equiposDivisionNoreste = obtenerEquiposDivision(DIVISION_NOROESTE);
        List<Equipo> equiposDivisionPacifico = obtenerEquiposDivision(DIVISION_PACIFICO);
        List<Equipo> equiposDivisionSuroeste = obtenerEquiposDivision(DIVISION_SUROESTE);
        
        
     // Ordenar la lista de equipos por victorias en orden descendente
        Collections.sort(equiposDivisionAtlantico, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionCentral, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionSureste, Comparator.comparingInt(Equipo::getVictorias).reversed());
        
        Collections.sort(equiposDivisionNoreste, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionPacifico, Comparator.comparingInt(Equipo::getVictorias).reversed());
        Collections.sort(equiposDivisionSuroeste, Comparator.comparingInt(Equipo::getVictorias).reversed());
        
        imprimirClasificacion(equiposDivisionAtlantico,DIVISION_ATLANTICO,DIVISION);
        imprimirClasificacion(equiposDivisionCentral,DIVISION_CENTRAL,DIVISION);
        imprimirClasificacion(equiposDivisionSureste,DIVISION_SURESTE,DIVISION);
        
        imprimirClasificacion(equiposDivisionNoreste,DIVISION_NOROESTE,DIVISION);
        imprimirClasificacion(equiposDivisionPacifico,DIVISION_PACIFICO,DIVISION);
        imprimirClasificacion(equiposDivisionSuroeste,DIVISION_SUROESTE,DIVISION);
	}

	
	private static List<Equipo> obtenerEquiposDivision(String division){
		return listaEquipos.stream()
                .filter(equipo -> equipo.getDivision().equals(division))
                .collect(Collectors.toList());
	}
	
	private static List<Equipo> obtenerEquiposConferencia(String conferencia){
		return listaEquipos.stream()
                .filter(equipo -> equipo.getConferencia().equals(conferencia))
                .collect(Collectors.toList());
	}
	
	private static void imprimirClasificacion(List<Equipo> lista, String orden,String tipo) {
		System.out.println(tipo.toUpperCase()+"\t"+orden.toUpperCase());
		int i=0;
		for(Equipo team:lista) {
			i++;
			 System.out.println(String.format("%dº\t%-35s\t%d\t%s",i,team.getNombre(),team.getVictorias(),team.getDerrotas()));
		}
		System.out.println();
	}
	
	private static void operarPartido(Document partido) {
		Equipo equipoLocal = devolverEquipo((String)
				((Document) partido.get(ATRIBUTO_PARTIDO_LOCAL)).get(ATRIBUTO_EQUIPO_NOMBRE));
        int tanteoLocal = Integer.parseInt(((String)
        		((Document) partido.get(ATRIBUTO_PARTIDO_LOCAL)).get(ATRIBUTO_EQUIPO_TANTEO)).trim());
        
        Equipo equipoVisitante = devolverEquipo((String) 
        		((Document) partido.get(ATRIBUTO_PARTIDO_VISITANTE)).get(ATRIBUTO_EQUIPO_NOMBRE));                
        int tanteoVisitante = Integer.parseInt(((String)
        		((Document) partido.get(ATRIBUTO_PARTIDO_VISITANTE)).get(ATRIBUTO_EQUIPO_TANTEO)).trim());
        
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
	
	
	private static MongoCursor<Document> devolverPartidos(String temporada){
		MongoCollection<Document> collection = db.getCollection(temporada);
        
        // Crear el filtro para la consulta
        Document filter = new Document(ATRIBUTO_PLAYOFF, false);
		
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
