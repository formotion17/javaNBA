package pruebaMongo;

import java.util.Comparator;

import Modelo.ClaseJugador;

public class ordenMaximoReboteador  implements Comparator<ClaseJugador>{

	@Override
	public int compare(ClaseJugador e1, ClaseJugador e2) {
		  if(e1.getTotalPartido().getTotalRebotes()>e2.getTotalPartido().getTotalRebotes()){
	            return -1;
	        }else if(e1.getTotalPartido().getTotalRebotes()>e2.getTotalPartido().getTotalRebotes()){
	            return 0;
	        }else{
	            return 1;
	        }
	}
}
