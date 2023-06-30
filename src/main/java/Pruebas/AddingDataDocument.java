package Pruebas;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Coleccion.Temporadas;
import Controlador.BaseController;
import Implementacion.GuardarTirosJugador;
import Modelo.ClaseJugadorDatos;
import Modelo.ClaseJugadorTiros;
import Utilidad.Util;

public class AddingDataDocument extends BaseController{
	
	private static GuardarTirosJugador guardarTirosJugador = new GuardarTirosJugador();
	
	private static ClaseJugadorDatos jugador = new ClaseJugadorDatos();

	public static void main(String[] args) throws UnknownHostException, JsonProcessingException {

		System.out.println("INICIAMOS");
		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
			
			ArrayList<ClaseJugadorTiros> listaTirosPartidos = new ArrayList<ClaseJugadorTiros>();
			
			jugador.setIdJugador("jamesle01");
			
			System.out.println("\nEmpezamos con Lebron James");
			
			MongoCollection<Document> collection =db.getCollection(COLECCIION_TIROS);
			
			collection.findOneAndDelete(new Document(ATRIBUTO_ID_JUGADOR,"jamesle01"));// new Document(ID_JUGADOR,"caldejo01");

			
			for(Temporadas t : Temporadas.values()) {
				listaTirosPartidos.addAll(guardarTirosJugador.recogerTirosTemporada(t.toString(), jugador.getIdJugador(), mongo));
			}
			
			jugador.setListaTiros(listaTirosPartidos);
			
			guardarTirosJugador.guardarTirosJugador(jugador, db);
			
			System.out.println("FINALIZMOS");
		}
			

	}

}
