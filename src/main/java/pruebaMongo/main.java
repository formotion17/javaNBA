package pruebaMongo;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class main {

	public static void main(String[] args) throws IOException {

		System.out.println("iniciamos");
		
		
		
//	CONECTAMOS A MONGODB		
		MongoClient mongo = crearConexion();
		
		
//	LEEMOS ARCHIVOS LOS CUALES TENEMOS EL NOMBRE DE LOS EQUIPOS Y LOS JUGADORES 
//		
//		List<String> listaEquipos = new ArrayList<String>();
//		BufferedReader reader = new BufferedReader(new FileReader("D://listaEquipos.txt"));
//	    String line;
//	    while ((line = reader.readLine()) != null)
//	    {
//	    	listaEquipos.add(line);
//	    }
//	    reader.close();
//			
//	     
			
			
		if(mongo!=null) {
			System.out.println("Lista de bases de datos: ");
			//printDatabases(mongo);
			
			MongoDatabase db = mongo.getDatabase("nba");
			
			// Select the collection
			MongoCollection<Document> col = db.getCollection("totales");
			
			Document findDocument = new Document("idJugador","jamesle01");
	        findDocument.put("temporada", "carrera");
	        findDocument.put("tiempo", "regular");
	        findDocument.put("tiporesultado","total");
	        
	        MongoCursor<Document> lista = col.find(findDocument).iterator();
	        
	        while(lista.hasNext()) {
	        	
                Document documento = lista.next();
	        }

//	SOLAMENTE UN DOCUMENTO
//			
//			// Create the document to specify find criteria
//			Document findDocument = new Document("asistencia",18465);
//			
//			// Docoument to store query results
//			FindIterable<Document> resultDocument = col.find(findDocument);
//			
//			// Solmamente nos devuelve el primero cursor
//			System.out.println(resultDocument.first().toJson());
			
//	BUSCAMOS VARIOS DOCUEMNTOS
			// Create the document to specify find criteria
			findDocument = new Document("equipoLocal.nombreAbreviado","sas");
			
			lista = col.find(findDocument).iterator();
			
			// Iterate over the resultas printing each document
			while(lista.hasNext()) {
				Document partido = (Document)lista.next();
				//System.out.println(lista.next().getInteger("asistencia"));
				Document visitante = (Document) lista.next().get("equipoVisitante");
				Document local = (Document) lista.next().get("equipoLocal");
				System.out.println(visitante.get("nombre") + " "+visitante.get("tanteo")+""
						+ " - "+local.get("tanteo")+" "+local.get("nombre"));
				List<Document> jugadoresLocal = (List<Document>)local.get("jugadores");
				imprimirJugadores(local.get("nombre"),jugadoresLocal);
				List<Document> jugadoresVisitante = (List<Document>)visitante.get("jugadores");
				imprimirJugadores(visitante.get("nombre"),jugadoresVisitante);
				System.out.println("FIN PARTIDO");
			}
			
		}else {
			System.out.println("Error: Conexión no establecida");
		}
		
		
		
		System.out.println("Finalizamos");
	}
	
	private static void imprimirJugadores(Object nombre,List<Document> jugadores) {
		System.out.println("EQUIPO: "+nombre.toString().toUpperCase());
		for(Document jugador:jugadores) {
			Document jug = (Document) jugador;
			Document box = (Document)jugador.get("boxscore");
			System.out.println(jugador.get("nombre")+" "+jugador.get("apellido")+" : "+box.get("puntos"));
		}
	}
	
	private static MongoClient crearConexion() throws UnknownHostException {
		MongoClient mongo = null;
		mongo = new MongoClient("localhost",27017);
		
		return mongo;
	}
	
	@SuppressWarnings("deprecation")
	private static void printDatabases(MongoClient mongo) {
		List<String> dbs = mongo.getDatabaseNames();
		for(String db : dbs) {
			System.out.println(" - "+db);
		}
	}

}
