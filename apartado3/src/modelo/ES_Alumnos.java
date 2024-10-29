package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ES_Alumnos {
	private static Scanner teclado = new Scanner(System.in);
	private static final String tablaAlumnos = "alumnmosApartado3";

	// CREACION ALUMNOS A PARTIR DE LOS DATOS INSERTADOS POR TECLADO
	public static Alumno leeAlumnoDeTeclado() {
		Alumno alumno;
		// Pedir DNI
		System.out.print("Dame dni de nuevo alumno: : ");
		String dni = teclado.nextLine();

		// Pedir nombre completo
		System.out.print("Dame el nombre: ");
		String nombre = teclado.nextLine();

		// Pedir nota
		System.out.print("Dame la nota: ");
		String notaStr = teclado.nextLine();
		float nota;
		try { 
			nota = Float.parseFloat(notaStr);
		}
		catch (Exception e) {
			System.out.println("Nota invalida. Se pondra como 0");
			nota = 0.0f;
		}
		System.out.print("Dame el curso del alumno: ");
		String curso = teclado.nextLine();
		alumno = new Alumno(dni,nombre, nota, curso);
		return alumno;
	}

	public static void inicializarBBDD(Connection conexion) {
		String lineaSQL = "CREATE TABLE IF NOT EXISTS " + tablaAlumnos + " ("
				+ "dni VARCHAR(50), "
				+ "nombre VARCHAR(50), "
				+ "nota FLOAT, "
				+ "curso VARCHAR(50)"
				+ ");";
		try (PreparedStatement createStatement = conexion.prepareStatement(lineaSQL)) {
			createStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static ArrayList<Alumno> leerAlumnos(Connection conextionBBDD) {
		ArrayList<Alumno> alumnos = new ArrayList<>();
		Alumno alumno = null;

		String query = "SELECT * FROM " + tablaAlumnos;
		try (PreparedStatement selectStatement = conextionBBDD.prepareStatement(query);
			 ResultSet resultado = selectStatement.executeQuery()) {
			if(!resultado.isBeforeFirst()) {
				return null;
			} else {
				while(resultado.next()) {
					alumno = reconstruirAlumno(resultado);
					if(alumno != null) {
						alumnos.add(alumno);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return alumnos;
	}

	private static Alumno reconstruirAlumno(ResultSet resultado) {
		try {
			String dni = resultado.getString(1);
			String nombre = resultado.getString(2);
			float nota = resultado.getFloat(3);
			String curso = resultado.getString(4);
			return new Alumno(dni, nombre, nota, curso);
		} catch (SQLException e) {
			System.out.println("[ERROR] No se pudo obtener los datos del alumno");
			return null;
		}
	}

	public static void registrarAlumno(Connection conextionBBDD, Alumno alumno) {
		String lineaSQL = "INSERT INTO " + tablaAlumnos +" (dni, nombre, nota, curso) VALUES(?, ?, ?, ?)";
		try(PreparedStatement insertStatement = conextionBBDD.prepareStatement(lineaSQL)) {
			insertStatement.setString(1, alumno.getDni());
			insertStatement.setString(2, alumno.getNombre());
			insertStatement.setFloat(3, alumno.getNota());
			insertStatement.setString(4, alumno.getCurso());
			insertStatement.executeUpdate();
			System.out.println("Alumno con el DNI " + alumno.getDni() + " ha sido registrado");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

//
//	public static void guardarAlumnosBBDD(Connection conexion, Clase clase) {
//		String lineaSQL = "INSERT INTO alumnosGestorFinal (dni, nombre, nota, nivel) VALUES (?, ?, ?, ?);";
//		Alumno alumno = null;
//		try(PreparedStatement insertStatement = conexion.prepareStatement(lineaSQL)) {
//			for(int i = 0; i < clase.size(); i++) {
//				alumno = clase.getAlumno(i);
//				insertarAlumno(alumno, insertStatement);
//			}
//		} catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//	private static void insertarAlumno(Alumno alumno, PreparedStatement insertStatement) {
//        try {
//            insertStatement.setString(1, alumno.getDni());
//			insertStatement.setString(2, alumno.getNombre());
//			insertStatement.setFloat(3, alumno.getNota());
//			insertStatement.setString(4, "CFGS");
//			insertStatement.executeUpdate();
//        } catch (SQLException _) {
//        }
//	}
//

}