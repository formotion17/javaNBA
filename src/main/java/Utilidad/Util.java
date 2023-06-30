package Utilidad;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.MongoClient;

import Controlador.BaseController;

public class Util extends BaseController{
	
	/**
	 * Función para recuperar todos los idJugadores que tenemos guardados en el archivo listaJugadoresIdNuevosBis.txt
	 * @return Devolvemos lista de los idJugadores
	 * @throws IOException
	 */
	public static ArrayList<String> recogerJugadores() throws IOException{
		ArrayList<String> listaJugadores = new ArrayList<>();
		BufferedReader readerJugadores = new BufferedReader(new FileReader("E:/TFG/idJugadoresDistinct.txt"));
	    String lineJugadores;
	    while ((lineJugadores = readerJugadores.readLine()) != null)
	    {
	    	listaJugadores.add(lineJugadores);
	    }
	    readerJugadores.close();
	    
	    return listaJugadores;
	}
	

	/**
	 * Creamos la conexión a la base de datos de mongoDB
	 * @return Devolvemos el cliente Mongo para poder usar la base de datos
	 * @throws UnknownHostException
	 */
	public static MongoClient crearConexion() throws UnknownHostException {
		MongoClient mongo = null;
		mongo = new MongoClient("localhost",27017);
		return mongo;
	}

	
	/**
	 * Función para devolver la lista de jugadores que han jugado en el día
	 * @return Devolvemos lista de los idJugadores
	 * @throws IOException
	 */
	public static ArrayList<String> recogerJugadoresDiaria() throws IOException {
		ArrayList<String> listaJugadores = new ArrayList<>();
		BufferedReader readerJugadores = new BufferedReader(new FileReader("E://Programacion//pruebasMaven//NBAMongo//nuevosId.txt"));
	    String lineJugadores;
	    while ((lineJugadores = readerJugadores.readLine()) != null)
	    {
	    	listaJugadores.add(lineJugadores);
	    }
	    readerJugadores.close();
	    
	    return listaJugadores;
	}

}
