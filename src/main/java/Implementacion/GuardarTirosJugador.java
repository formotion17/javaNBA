package Implementacion;

import java.util.ArrayList;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Controlador.BaseController;
import Mapper.MapJugadorEstadisticas;
import Modelo.ClaseJugadorDatos;
import Modelo.ClaseJugadorTiros;

/**
 * Función que guardar los tiros de un jugador en una temporada en concreto
 * @author hatashi
 *
 */

public class GuardarTirosJugador extends BaseController{
	
	public ArrayList<ClaseJugadorTiros> recogerTirosTemporada(String temporada,String idJugador,MongoClient mongo) {
		
		ArrayList<ClaseJugadorTiros> listaTirosPartidos = new ArrayList<ClaseJugadorTiros>();
		
		MongoDatabase db = mongo.getDatabase(BASE_DATOS_NBA);
		
		MongoCollection<Document> collection =db.getCollection(temporada);
		
		Document find = new Document(ID_JUGADOR_LOCAL,idJugador);

		MongoCursor<Document> lista = collection.find(find).iterator();
		
		while(lista.hasNext()) {
			listaTirosPartidos.add(MapJugadorEstadisticas.rellenarTirosPartidoJugador((Document)lista.next(),idJugador,true,temporada));
		}
		
		find = new Document(ID_JUGADOR_VISITANTE,idJugador);
		
		lista = collection.find(find).iterator();

		while(lista.hasNext()) {
			listaTirosPartidos.add(MapJugadorEstadisticas.rellenarTirosPartidoJugador((Document)lista.next(),idJugador,false,temporada));
		}
		
		return listaTirosPartidos;
		
	}
	
	public void guardarTirosJugador(ClaseJugadorDatos jugador,MongoDatabase db) throws JsonProcessingException {
		
		 ObjectMapper Obj = new ObjectMapper();
	        
         String jsonStr = Obj.writeValueAsString(jugador);
         
         Document doc = Document.parse(jsonStr);
         
         MongoCollection<Document> coleccion =db.getCollection(COLECCIION_TIROS);
         
         insertarDocumento(doc, coleccion);
			
         System.out.println("Finalizamos "+jugador.getIdJugador()+ "\n");
		
	}

}
