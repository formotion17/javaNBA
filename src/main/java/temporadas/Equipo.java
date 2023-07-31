package temporadas;

import lombok.Data;

@Data
public class Equipo {
	
	private int posicion;

	private String nombre;
	private String conferencia;
	private String division;
	
	private int victorias;
	private int derrotas;
	private int victoriasLocal;
	private int victoriasVisitante;
	private int derrotasLocal;
	private int derrotasVisitante;
	private float porcentajeVictorias;
	
	private int victoriasDivisionLocal;
	private int victoriasDivisionVisitante;
	private int derrotasDivisionLocal;
	private int derrotasDivisionVisitante;
	
	private int victoriasConferenciaLocal;
	private int victoriasConferenciaVisitante;
	private int derrotasConferenciaLoca;
	private int derrotasConferenciaVisitante;
	
	public Equipo (String nombre, String conferencia, String division) {
		this.nombre=nombre;
		this.conferencia=conferencia;
		this.division=division;
	}
	
	public void addVictoriasLocal() {
		this.victoriasLocal++;
	}
	
	public void addVictoriasVisitante() {
		this.victoriasVisitante++;
	}
	
	public void addDerrotasLocal() {
		this.derrotasLocal++;
	}
	
	public void addDerrotasVisitante() {
		this.derrotasVisitante++;
	}
	
	public void addVictoriaDivisionLocal(){
		this.victoriasDivisionLocal++;
	}
	public void addVictoriaDivisionVisitante(){
		this.victoriasDivisionVisitante++;
	}
	
	public void addDerrotaDivisionLocal(){
		this.derrotasDivisionLocal++;
	}
	
	public void addDerrotaDivisionVisitante() {
		this.derrotasDivisionVisitante++;
	}
	
	public void addVictoriaConferenciaLocal() {
		this.victoriasConferenciaLocal++;
	}
	
	public void addVictoriaConferenciaVisitante() {
		this.victoriasConferenciaVisitante++;
	}
	
	public void addDerrotaConferenciaLocal() {
		this.derrotasConferenciaLoca++;
	}
	
	public void addDerrotaConferenciaVisitante() {
		this.derrotasConferenciaVisitante++;
	}
	
	public void addVictoria() {
		this.victorias++;
	}
	
	public void addDerrota() {
		this.derrotas++;
	}
	
	public void addVictoriaLocal() {
		this.victoriasLocal++;
	}
	
	public void addVictoriaVisitante(){
		this.victoriasVisitante++;
	}
	
	public void addDerrotaLocal() {
		this.derrotasLocal++;
	}
	
	public void addDerrotaVisitante() {
		this.derrotasVisitante++;
	}
}
