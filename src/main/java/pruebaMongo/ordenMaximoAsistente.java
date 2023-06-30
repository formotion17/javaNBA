package pruebaMongo;

import java.util.Comparator;

import model.ClaseJugador;

public class ordenMaximoAsistente  implements Comparator<ClaseJugador>{

	@Override
	public int compare(ClaseJugador e1, ClaseJugador e2) {
		  if(e1.getTotalPartido().getAsistencias()>e2.getTotalPartido().getAsistencias()){
	            return -1;
	        }else if(e1.getTotalPartido().getAsistencias()>e2.getTotalPartido().getAsistencias()){
	            return 0;
	        }else{
	            return 1;
	        }
	}

}
