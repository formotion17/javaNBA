package Funcion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Coleccion.Temporadas;
import Controlador.BaseController;
import Utilidad.Util;

public class ServicioRecogerIdJugadoresUnicos extends BaseController{

	private static ArrayList<String> listaJugadores=new ArrayList<String>();
	
	private static String archivoJugadoresID="/Users/formotion/tfg/java/archivos/idJugadores.txt";
	private static String archivoJugadoresIDOrdenados="/Users/formotion/tfg/java/archivos/idJugadoresOrdenados.txt";
	
	public static void main(String[] args) throws UnknownHostException {
		
		
		System.out.println("INICIAMOS LA APLICACI�N PARA ERCOGER LOS DIFERENTES ID'S QUE HAY");
		
		// CONECTAMOS A MONGODB
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			try {
				
				MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
				
				FileWriter fileWriter = new FileWriter(archivoJugadoresID);
	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
							
				for(Temporadas t:Temporadas.values()) {
					MongoCursor<String> c = db.getCollection(t.toString()).distinct("equipoLocal.jugadores.id", String.class).iterator();
					
					while(c.hasNext()) {
						String id=c.next();
						if(id.contains("")) {
							if(existeJugador(id)) {
								listaJugadores.add(id);
								bufferedWriter.write(id);
								bufferedWriter.newLine();
							};
						}
					}
				}
				
				for(int i=0;i<listaJugadores.size();i++){System.out.println(listaJugadores.get(i));}
				
				bufferedWriter.close();

				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
		}
		
		ordenarIdJugadores();

	}
	
	private static void ordenarIdJugadores(){
		
		List<String> lineas = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(archivoJugadoresID))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Ordenar la lista en orden alfabético
        Collections.sort(lineas);

        // Escribir las líneas ordenadas en un nuevo archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoJugadoresIDOrdenados))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Archivo ordenado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
		
	}
	public static boolean existeJugador(String jugador) {
		
		for(int i=0;i<listaJugadores.size();i++) {
			if(listaJugadores.get(i).equals(jugador)) {return false;}
		}
		
		return true;
	}

}
