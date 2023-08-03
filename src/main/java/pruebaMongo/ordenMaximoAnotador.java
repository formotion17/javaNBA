package pruebaMongo;

import java.util.Comparator;

import Modelo.ClaseJugador;

public class ordenMaximoAnotador  implements Comparator<ClaseJugador>{

	@Override
	public int compare(ClaseJugador e1, ClaseJugador e2) {
		  if(e1.getTotalPartido().getPuntos()>e2.getTotalPartido().getPuntos()){
	            return -1;
	        }else if(e1.getTotalPartido().getPuntos()>e2.getTotalPartido().getPuntos()){
	            return 0;
	        }else{
	            return 1;
	        }
	}

}
