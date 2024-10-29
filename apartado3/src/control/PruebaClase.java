package control;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import modelo.Alumno;
import modelo.ES_Alumnos;

public class PruebaClase {
	private static final String urlDB = "jdbc:mysql://localhost:3306/adat7";;
	private static Connection conexionDB;
	private static final String usuario = "dam2";
	private static final String password = "asdf.1234";
	static int opcion;

	public static void main(String[] args) {
		conexionDB = obtenerConexionBD(urlDB, usuario, password);
		ES_Alumnos.inicializarBBDD(conexionDB);
		opcion = 0;
		boolean salir = false;

		while(!salir) {
			mostrarMenu();
			opcion = pedirOpcionMenuPrincipal();
			salir = operar();
		}
	}

	// Funciones internas
	private static Connection obtenerConexionBD(String url, String usuario, String password) {
		try {
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			return conexion;
		} catch (SQLException e) {
			System.out.println("Error al iniciar conexion con la base de datos");
			return null;
		}
	}

	private static void mostrarMenu() {
		System.out.println("+-----------------------------------------------------+");
		System.out.println("|                 GESTOR BBDD ALUMNOS                 |");
		System.out.println("+-----------------------------------------------------+");
		System.out.println("""
                1) Listar todos los alumnos registrados\
                
                2) Registrar un alumno\
                
                3) Modificar informacion de un alumno\
                
                4) Eliminar un alumno\
                
                5) Salir\
                
                """);
	}

	private static int pedirOpcionMenuPrincipal() {
		Scanner entrada = new Scanner(System.in);
		int opcion = 0;
		while (opcion < 1 || opcion > 5) {
			try {
				System.out.print("Elija una opcion: ");
				opcion = entrada.nextInt();
				if(opcion < 1 || opcion > 5) {
					System.out.println("[ERROR] La opcion debe de ser del 1 al 6");
				}
			} catch (InputMismatchException e) {
				System.out.println("[ERROR] Debes ingresar un número válido.");
				entrada.next();  // Limpiar la entrada incorrecta del scanner
			}
		}
		return opcion;
	}

	private static boolean operar() {
		System.out.println();
		switch (opcion) {
			case 1:
				System.out.println("|-------------- [LISTADO ALUMNOS REGISTRADOS] --------------|");
				ArrayList<Alumno> alumnos = ES_Alumnos.leerAlumnos(conexionDB);
				if(alumnos != null) {
					for (Alumno alum : alumnos) {
						System.out.println(alum);
					}
				} else {
					System.out.println("No hay alumnos registrados actualmente");
				}
				return false;
			case 2:
				System.out.println("|-------------- [REGISTRANDO ALUMNO] --------------|");
				Alumno alumnoNuevo = ES_Alumnos.leeAlumnoDeTeclado();
				ES_Alumnos.registrarAlumno(conexionDB, alumnoNuevo);
				return false;
			case 3:
				System.out.println("|-------------- [MODIFICAR ALUMNO] --------------|");
				String dni = pedirDNI();
				System.out.println(dni);
				return false;
			case 4:
				System.out.println("|-------------- [ELIMINAR ALUMNO] --------------|");
				return false;
			case 5:
				System.out.println("+---------------------------------------------------+");
				System.out.println("|                  FIN DEL PROGRAMA                 |");
				System.out.println("+---------------------------------------------------+");
		}
		return true;
	}

	public static String pedirDNI() {
		Scanner entrada = new Scanner(System.in);
		boolean dniValido = false;
		String dni = "";

		while(!dniValido) {
			System.out.print("Introduzca el DNI del alumno: ");
			dni = entrada.nextLine();
			dniValido = !dni.isEmpty();
			if(!dniValido) {
				System.out.println("[ERROR] El campo no puede estar vacio");
			}
		}
		return dni;
	}

}