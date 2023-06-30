package pruebaMongo;

import java.io.File;

public class EliminarArchivo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File fichero = new File("G://TFG//Web//Eclipse//pruebaMongo//src//pruebaMongo//nuevosId.txt");
		if (fichero.delete())
			   System.out.println("El fichero ha sido borrado satisfactoriamente");
			else
			   System.out.println("El fichero no puede ser borrado");
	}

}
