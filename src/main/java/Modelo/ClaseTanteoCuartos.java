package Modelo;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ClaseTanteoCuartos {
	
	/**
	 * Tanteo de los cuartos
	 */
	private Map<String, Integer> cuartos = new HashMap<String, Integer>();

	/**
	 * Insertamos el tanteo en el cuarto
	 * @param cuarto
	 * @param puntos
	 */
	public void insertarCuarto(String cuarto, Integer puntos) {
		cuartos.put(cuarto, puntos);
	}

	/**
	 * Devolvemos el tanteo segun el cuarto
	 * @param cuarto
	 * @return
	 */
	public Integer devolverCuarto(String cuarto) {
		return cuartos.get(cuarto);
	}

}
