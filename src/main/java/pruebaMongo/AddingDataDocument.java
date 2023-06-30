package pruebaMongo;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Utilidades.Util;

public class AddingDataDocument {

	public static void main(String[] args) throws UnknownHostException {

		System.out.println("INICIAMOS");
		
		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
			MongoDatabase db = mongo.getDatabase("nba");
			
			System.out.println("\nEmpezamos con Calderon");
			
			MongoCollection<Document> collection =db.getCollection("tiros");

			BasicDBObject query = new BasicDBObject();
			query.put("idJugador", "caldejo01"); // (1)

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("listaTiros", "{'ddd':'dd'}"); // (2)

			BasicDBObject updateObject = new BasicDBObject();
			updateObject.put("$set", newDocument); // (3)

			db.getCollection("tiros").updateOne(query, updateObject); // (4)
		}
			

	}

}
