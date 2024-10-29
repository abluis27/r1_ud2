package control;

import java.sql.*;

import modelo.Alumno;
import modelo.Clase;
import modelo.ES_Alumnos;

public class PruebaClase {
	private static final String urlDB = "jdbc:mysql://localhost:3306/adat7";;
	private static Connection conexionDB;
	private static final String usuario = "dam2";
	private static final String password = "asdf.1234";
	
	public static void main(String[] args) {
		Clase daw1 = null;
		conexionDB = obtenerConexionBD(urlDB, usuario, password);
		ES_Alumnos.inicializarBBDD(conexionDB);

		if(conexionDB != null) {
			// LECTURA ALUMNOS
			daw1 = ES_Alumnos.leerAlumnosBBDD(conexionDB);
			if(daw1 == null) {
				daw1 = new Clase();
			}
			System.out.println("[LECTURA ALUMNOS]");
			System.out.println("Alumnos leídos: " + daw1);

			// AÑADIMOS ALUMNOS CON leeAlumnoDeTeclado y insertarSinRep
			Alumno alumnoNuevo;
			System.out.println("\n[REGISTRANDO ALUMNOS]");
			while ((alumnoNuevo = ES_Alumnos.leeAlumnoDeTeclado())!=null) {
				daw1.insertarSinRep(alumnoNuevo);
			}

			// VOLVEMOS A LEER LOS ALUMNOS
			System.out.println("\n[NUEVA LISTA ALUMNOS]");
			System.out.println(daw1);

			if(daw1.size() != 0) {
				ES_Alumnos.guardarAlumnosBBDD(conexionDB, daw1);
				System.out.println("Guardando alumnos en la base de datos...");
			} else {
				System.out.println("Clase vacia. No se ha guardado datos");
			}
		}

	} // main

	// Funciones internas
	private static Connection obtenerConexionBD(String url, String usuario, String password) {
		try {
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexion establecida con la base de datos");
			return conexion;
		} catch (SQLException e) {
			System.out.println("Error al iniciar conexion con la base de datos");
			return null;
		}
	}
	
} // class PruebaClase