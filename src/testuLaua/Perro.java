package testuLaua;

public class Perro {
	
	 private String color;
	    private String raza;
	    private boolean enfermo;
	    private int edad;

	    public Perro(String color, String raza, boolean enfermo, int edad) {
	        this.color = color;
	        this.raza = raza;
	        this.enfermo = enfermo;
	        this.edad = edad;
	    }

	    @Override
	    public String toString() {
	        return "Color: " + color + "\n" +
	               "Raza: " + raza + "\n" +
	               "Enfermo: " + (enfermo ? "Sí" : "No") + "\n" +
	               "Edad: " + edad + " años\n*****";
	    }	

}
