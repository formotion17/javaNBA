package Funcion;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.bson.Document;
import org.xml.sax.SAXException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Coleccion.Temporadas;
import Controlador.BaseController;
import Implementacion.GuardarTirosJugador;
import Modelo.ClaseJugadorDatos;
import Modelo.ClaseJugadorTiros;
import Utilidad.Util;

public class RegistrarTirosJugador extends BaseController{
	
	private static GuardarTirosJugador guardarTirosJugador = new GuardarTirosJugador();

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		long inicio = System.currentTimeMillis();
		
		System.out.println("INICIAMOS REGISTRAR TIROS JUGADORES");
		
		setListaJugadores(Util.recogerJugadores());
		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
			
			ArrayList<ClaseJugadorTiros> listaTirosPartidos = new ArrayList<ClaseJugadorTiros>();
			
			ClaseJugadorDatos jugador = new ClaseJugadorDatos();
			
			for(String id:getListaJugadores()) {
				
				System.out.println("\nEMPEZAMOS CON "+id);
				
				MongoCollection<Document> collection =db.getCollection(COLECCIION_TIROS);
				
				@SuppressWarnings("unused")
				Document eliminarTiros = collection.findOneAndDelete(new Document(ATRIBUTO_ID_JUGADOR,id));// new Document(ID_JUGADOR,"caldejo01");
				
				jugador.setIdJugador(id);
				
				for(Temporadas t : Temporadas.values()) {
					
					System.out.println("	- TEMPORADA: "+t.toString());
					listaTirosPartidos.addAll(guardarTirosJugador.recogerTirosTemporada(t.toString(), id, mongo));
					
				}

				jugador.setListaTiros(listaTirosPartidos);
				
				guardarTirosJugador.guardarTirosJugador(jugador, db);
				
				listaTirosPartidos.clear();
				
				System.out.println("FINALIZAMOS");
				
			}		
			
		}
		
		System.out.println("FINALIZAMOS TODOS LOS JUGADORES");
		
		long fin = System.currentTimeMillis();
		
		double tiempo = (double) ((fin - inicio)/1000);
        
        System.out.println("SE HA TARDADO EN DESCARGAR: "+tiempo+" Segundos");
		
	}

}
