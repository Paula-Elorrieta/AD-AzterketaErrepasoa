package firebase;

import java.util.ArrayList;

public class Usuario {
	
	private String nombreString;
	private int edad;
	private ArrayList<Actividad> actividades;
	
	public Usuario(String nombreString, int edad, ArrayList<Actividad> actividades) {
		this.nombreString = nombreString;
		this.edad = edad;
		this.actividades = actividades;
	}
	
	public String getNombreString() {
		return nombreString;
	}
	
	public void setNombreString(String nombreString) {
		this.nombreString = nombreString;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public ArrayList<Actividad> getActividades() {
		return actividades;
	}
	
	public void setActividades(ArrayList<Actividad> actividades) {
		this.actividades = actividades;
	}

}
