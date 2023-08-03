package pruebaMongo;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import Utilidad.Util;
import Coleccion.ListaEquipos;
import Modelo.ClaseEstadisticaNormalTotales;

public class CalcularMaximosTemporadasAction {
	
	static ArrayList<ClaseEstadisticaNormalTotales> listaJugadores = new ArrayList<ClaseEstadisticaNormalTotales>();
    static DecimalFormat df = new DecimalFormat("#.##");

	public static void main(String[] args) throws UnknownHostException {

		System.out.println("iniciamos");
		
		//	CONECTAMOS A MONGODB		
		MongoClient mongo = Util.crearConexion();
		
		if(mongo!=null) {
			
				DB db = mongo.getDB("NBA");
				DBCursor cursor;
				
				DBCollection collection =db.getCollection("totales");
				
				/**
				 *  Vamos a sacar por pantalla los id's de los jugadores que
				 *  TEmporada: 2000/2001			<>	season20002001
				 *  tiempo: Temporada regular		<>	

				 *  tiporesultado					<>	total
				 */
				
				BasicDBObject allQuery = new BasicDBObject();
                BasicDBObject fields = new BasicDBObject();
                List<BasicDBObject> obj = new ArrayList<BasicDBObject>();

                obj.add(new BasicDBObject("temporada", "season20122013"));
                obj.add(new BasicDBObject("tiempo", "regular"));
                obj.add(new BasicDBObject("tiporesultado", "total"));
                
                allQuery.put("$and", obj);

                cursor = collection.find(allQuery);
                
                while(cursor.hasNext()) {

                	
                    DBObject get = cursor.next();
                    
                    listaJugadores.add(MapJugadorEstadisticas.devolverEstadisticasTotalesJugador(new Document((Map<String, Object>) get)));

                }
                
                // MAXIMOS ANOTADORES
                ordenarAnotadores();
                imprimirTop(3,"puntos");
                
                // MAXIMOS REBOTEADORES
                ordenarReboteadores();
				imprimirTop(3,"rebotes");
				
				// MAXIMOS ASISTENTES
                ordenarAsistentes();
				imprimirTop(3,"asistencias");
				
				// MAXIMOS TAPONADORES
            	ordenarTaponadores();
				imprimirTop(3,"tapones");
				
				// MAXIMOS ROTOS
                ordenarRobos();
				imprimirTop(3,"robos");
				
				// MAXIMOS MINUTOS JUGADOS
                ordenarMinutosJugados();
				imprimirTop(3,"minutos");
				
				// MAXIMOS TIROS ANOTADOS
				ordenarTirosAnotados();
				imprimirTop(3,"tirosAnotados");
				
				// MAXIMOS TIROS INTENTADOS
				ordenarTirosIntentados();
				imprimirTop(3,"tirosIntentados");
				
				// MAXIMOS TRIPLES ANOTADOS
                ordenarTriplesAnotados();
				imprimirTop(3,"triplesAnotados");
				
				// MAXIMOS TRIPLES INTENTADOS
				ordenarTriplesIntentados();
				imprimirTop(3,"triplesIntentados");
				
				// MAXIMOS LIBRES ANOTADOS
				ordenarTirosLiebresAnotados();
				imprimirTop(3,"libresAnotados");
				
				// MAXIMOS LIBRES INTENTADOS
				ordenarTirosLibresIntentados();
				imprimirTop(3,"libresIntentados");
                
				// MAXIMOS PERDIDAS
				ordenarPerdidas();
				imprimirTop(3,"perdidas");
                
				// MAXIMOS FALTONES
                ordenarFaltas();				             
				imprimirTop(3,"faltas");
                
		}
	}
	
	private static void imprimirTop(int top, String atributo) {
		System.out.println("***********************************************************************************************");
		
		switch(atributo) {
		case "puntos":
			System.out.println("MAXIMOS ANOTADORES");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getPuntosPartidos()));
			}
			break;
		case "rebotes":
            System.out.println("MAXIMOS REBOTEADORES");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getRebotesPartido()));
			}
			break;
		case "asistencias":
			System.out.println("MAXIMOS ASISTENTES");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getAsistenciasPartido()));
			}
			break;
		case "tapones":
			System.out.println("MAXIMOS TAPONADORES");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTaponesPartido()));
			}
			break;
		case "robos":
			System.out.println("MAXIMOS LADRONES");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getRobosPartido()));
			}
			break;
		case "minutos":
			System.out.println("MAXIMOS MINUTOS JUGADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getMinutosJugadosPartido()));
			}
			break;
		case "tirosAnotados":
			System.out.println("MAXIMOS TIROS DE CAMPO ANOTADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTiroCampoAnotadosPartido()));
			}
			break;
		case "tirosIntentados":
			System.out.println("MAXIMOS TIROS DE CAMPO INTENTADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTiroCampoIntentadosPartido()));
			}
			break;
		case "triplesAnotados":
			System.out.println("MAXIMOS TRIPLES ANOTADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTriplesAnotadosPartido()));
			}
			break;
		case "triplesIntentados":
			System.out.println("MAXIMOS TRIPLES INTENTADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTriplesIntentadosPartido()));
			}
			break;
		case "libresAnotados":
			System.out.println("MAXIMOS TIROS LIBRES ANOTADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTirosLibresAnotadosPartido()));
			}
			break;
		case "libresIntentados":
			System.out.println("MAXIMOS TIROS LIBRES INTENTADOS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getTirosLibresIntentadosPartido()));
			}
			break;
		case "perdidas":
			System.out.println("MAXIMOS PERDIDAS");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getPerdidasPartido()));
			}
			break;
		case "faltas":   
			System.out.println("MAXIMOS FALTONES");
			for(int i=0;i<top;i++) {
	        	System.out.println(listaJugadores.get(i).getIdJugador()+" : "+df.format(listaJugadores.get(i).getFaltasPartido()));
			}
			break;
		}
		
		System.out.println("***********************************************************************************************");
        
        
		
	}

	private static void ordenarFaltas() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getFaltasPartido(), s1.getFaltasPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarPerdidas() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getPerdidasPartido(), s1.getPerdidasPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTirosLibresIntentados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTirosLibresIntentadosPartido(), s1.getTirosLibresIntentadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTirosLiebresAnotados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTirosLibresAnotadosPartido(), s1.getTirosLibresAnotadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTirosIntentados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTiroCampoIntentadosPartido(), s1.getTiroCampoIntentadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTriplesIntentados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTriplesIntentadosPartido(), s1.getTriplesIntentadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTriplesAnotados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTriplesAnotadosPartido(), s1.getTriplesAnotadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTirosAnotados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTiroCampoAnotadosPartido(), s1.getTiroCampoAnotadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarMinutosJugados() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getMinutosJugadosPartido(), s1.getMinutosJugadosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarRobos() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getRobosPartido(), s1.getRobosPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarTaponadores() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getTaponesPartido(), s1.getTaponesPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarAsistentes() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getAsistenciasPartido(), s1.getAsistenciasPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarReboteadores() {
		Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getRebotesPartido(), s1.getRebotesPartido());
            }
        };
        Collections.sort(listaJugadores, comparator);
		
	}

	private static void ordenarAnotadores() {
        Comparator<ClaseEstadisticaNormalTotales> comparator = new Comparator<ClaseEstadisticaNormalTotales>() {
            @Override
            public int compare(ClaseEstadisticaNormalTotales s1, ClaseEstadisticaNormalTotales s2) {
                return Double.compare(s2.getPuntosPartidos(),s1.getPuntosPartidos());
            }
        };
        Collections.sort(listaJugadores, comparator);
    }

}

