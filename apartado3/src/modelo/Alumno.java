package modelo;

import java.io.Serializable;

public class Alumno implements Comparable, Serializable {
	
	public enum Nivel{FPB, CFGM, CFGS};
	
	//ATRIBUTOS DE CLASE
	private static float notaCorte= 0.0F;
	
	//ATRIBUTOS DE INSTANCIA
	String dni;
	String nombre;
	float nota;
	Nivel nivel;
	
	public Alumno(String dni, String nombre, float nota, Nivel nivel) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.nota = nota;
		this.nivel = nivel;
	}
	
	public Alumno(String dni, String nombre) {
		this(dni, nombre, -1.0F, Nivel.FPB);
	}
	
	public String toString() {
		return "DNI: " + this.dni+", Nombre: "+this.nombre+", Nota: "+this.nota+", NIVEL: "+ this.nivel;
	}
	
	public int hashCode() {
		return dni.hashCode();
	}
	
	public boolean equals(Object o) {
		Alumno a= (Alumno) o;
		return this.dni.equals(a.getDni());
	}

	public static float getNotaCorte() {
		return Alumno.notaCorte;
	}
	
	public void setNotaCorte(float notaCorte) {
		Alumno.notaCorte= notaCorte;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	@Override
	public int compareTo(Object o) {
		Alumno aux= (Alumno) o;
		//ordenar por nota
		return (int) (this.nota-aux.nota);
	}

}//class





