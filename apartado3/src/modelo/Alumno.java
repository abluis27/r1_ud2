package modelo;

import java.io.Serializable;

public class Alumno implements Comparable, Serializable {

	//ATRIBUTOS DE CLASE
	private static float notaCorte= 0.0F;
	
	//ATRIBUTOS DE INSTANCIA
	String dni;
	String nombre;
	float nota;
	String curso;
	
	public Alumno(String dni, String nombre, float nota, String curso) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.nota = nota;
		this.curso = curso;
	}
	
	public Alumno(String dni, String nombre) {
		this(dni, nombre, -1.0F, "FPB");
	}
	
	public String toString() {
		return "DNI: " + this.dni+", Nombre: "+this.nombre+", Nota: "+this.nota+", Curso: "+ this.curso;
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

	public String getCurso() {
		return curso;
	}

	public void setCurso(String nivel) {
		this.curso = curso;
	}

	@Override
	public int compareTo(Object o) {
		Alumno aux= (Alumno) o;
		//ordenar por nota
		return (int) (this.nota-aux.nota);
	}

}//class





