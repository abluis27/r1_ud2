package modelo;

import java.io.Serializable;

import modelo.Alumno.Nivel;

public class Clase implements Serializable {
	//constantes
	public static final int MAX_ELTOS= 50;
	
	//atributos de instancia
	
	private Alumno[] clase;
	private int cont;
	
	//constructores
	
	public Clase(int n) {	
		clase= new Alumno[n];
		cont= 0;
	}
	
	public Clase() {
		this(MAX_ELTOS);
	}
	
	public int size() { return cont; }
	
	public Alumno getAlumno(int pos) {
		if (pos<0 || pos>=cont) return null;
		return clase[pos];
	} // getAlumno
	
	public String toString() {
		String result= "La clase esta vacia";

		if (cont != 0) {
			result= "ALUMNOS: ";
			for(int i=0; i<cont; i++) result += "\n"+clase[i];
			result+= "\nFIN\n";
		}
		return result;
	}

	public Alumno getAlumno(String dni) {
		for (int i=0; i<cont; i++)
			if (clase[i].getDni().equals(dni)) return clase[i];
		return null;
	}

	public boolean posicionValida(int pos) {
		return pos>=0 && pos<clase.length;		

	}
	
	public boolean estaOcupado(int pos) {
		//return clase[pos]!= null;
		return pos<cont;
	}

	
	public void insertar(Alumno a) {
				
		if(cont<clase.length) { //hay sitio para insertar
			
			clase[cont]= a;
			cont++;
			
		}else  
		System.out.println("No se ha podido insertar. La clase est� llena");
				
	}//insertar

	public boolean estaRepetido(String dni) {
		
		//recorrer cada elemento de la clase
		//y comprobar si el dni est� repetido
		
		for(int i=0; i<cont; i++) {
			if(dni.equals(clase[i].getDni())) return true;	
			}		
				
		return false;	
		
	}

	public void insertarSinRep(Alumno a) {
		
		//si el alumno est� repetido negar la inserci�n
		
		if(estaRepetido(a.getDni())) 
			System.out.println("El alumno ya est� en la clase.");
		
		else  insertar(a);
			
	}//insertarSinRep

	public void borrar(String dni) {
		//borrar el alumno si existe
		//suponemos que el dni solo est� una vez
		for(int i=0; i<cont; i++) { //voy recorriendo cada posici�n
			if(dni.equals(clase[i].getDni())) { //comparamos los dnis
				//borrar: asignar null a esa posici�n
				clase[i]= null;
				//compactar???
				for(int j= i; j<cont-1; j++)
					clase[j]= clase[j+1];
				//actualizar el contador
				cont--;
				//dejar de buscar
				break;
			}	
		}	
		
	}//borrar

	public void listadoPorNota(float nota) {
		
		System.out.println("\nLISTADO DE ALUMNOS CON NOTA MAYOR QUE: "+nota+"\n");
		
		int cont=0;
		
		for(int i=0; i<this.cont; i++) 
			if (clase[i].getNota()>=nota) {
				
				System.out.println(clase[i]);
				cont++;
			}
		
		System.out.println("\nTOTAL ALUMNOS: "+cont);		
		
	}//listado por nota

	public void listadoPorNivel(Nivel nivel) {
		
		System.out.println("\nLISTADO DE ALUMNOS MATRICULADOS EN: "
							+nivel.toString()+"\n");
		
		int cont=0;
		
		for(int i=0; i<this.cont; i++) {
			
			if (clase[i].getNivel()== nivel) {
				
				System.out.println(clase[i]);
				cont++;
			}			
		}
		
		System.out.println("\nTOTAL ALUMNOS: "+cont+"\n");
		
	}

	public void listadoPorNiveles() {
		
		System.out.println("\nLISTADO DE ALUMNOS POR NIVELES\n");
		
		listadoPorNivel(Nivel.FPB);
		listadoPorNivel(Nivel.CFGM);
		listadoPorNivel(Nivel.CFGS);
				
	}	
	
	public Alumno[] backup() {
		
		//instanciar una nueva clase con espacio propio en memoria
		Alumno[] copia= new Alumno[clase.length];
		
		//copiar LA REFERENCIA a cada elemento
		//for(int i=0; i<clase.length; i++) copia[i]= clase[i];
		
		//como es un backup, vamos a copiar CADA ELEMENTO	 
		 
		for(int i=0; i<cont; i++) 
			copia[i]= new Alumno(clase[i].getDni(),
					clase[i].getNombre(), clase[i].getNota(), 
					clase[i].getNivel());
							
		return copia;
		
	}

}//class
