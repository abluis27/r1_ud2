package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import modelo.Alumno.Nivel;

public class ES_Alumnos {
	private static Scanner teclado = new Scanner(System.in);
	private static final String tablaAlumnos = "alumnosApartado2";

	// CREACION ALUMNOS A PARTIR DE LOS DATOS INSERTADOS POR TECLADO
	public static Alumno leeAlumnoDeTeclado() {
		Alumno alumno;
		// Pedir DNI
		System.out.print("Dame dni de nuevo alumno a insertar o vacío para terminar: ");
		String dni = teclado.nextLine();
		if (dni.isBlank() || dni.length() > 50) {
			System.out.println("DNI invalido. Se cancela la operacion");
			return null;
		}

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
		alumno = new Alumno(dni,nombre, nota, Nivel.CFGS);
		return alumno;
	} // leeAlumnoDeTeclado

	// RECORREMOS LA TABLA Y INSTANCIAMOS ALUMNOS
	public static Clase leerAlumnosBBDD(Connection conexionBBDD) {
		Clase clase = new Clase();
		Alumno alumno = null;
		String query = "SELECT * FROM " + tablaAlumnos;

		try (PreparedStatement selectStatement = conexionBBDD.prepareStatement(query);
			 ResultSet resultado = selectStatement.executeQuery()) {
			if(!resultado.isBeforeFirst()) {
				return null;
			} else {
				while(resultado.next()) {
					alumno = reconstruirAlumno(resultado);
					if(alumno != null) {
						clase.insertarSinRep(alumno);
					}
				}
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clase;
	}

	// GUARDAMOS LOS ALUMNOS DE LA CLASE EN LA BBDD
	public static void guardarAlumnosBBDD(Connection conexionBBDD, Clase clase) {
		String lineaSQL = "INSERT INTO " + tablaAlumnos + " (dni, nombre, nota, nivel) VALUES (?, ?, ?, ?);";
		Alumno alumno = null;
		try(PreparedStatement insertStatement = conexionBBDD.prepareStatement(lineaSQL)) {
			for(int i = 0; i < clase.size(); i++) {
				alumno = clase.getAlumno(i);
				insertarAlumno(alumno, insertStatement);
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	private static void insertarAlumno(Alumno alumno, PreparedStatement insertStatement) {
        try {
            insertStatement.setString(1, alumno.getDni());
			insertStatement.setString(2, alumno.getNombre());
			insertStatement.setFloat(3, alumno.getNota());
			insertStatement.setString(4, "CFGS");
			insertStatement.executeUpdate();
        } catch (SQLException _) {
			// En caso de que el alumno ya este registrado en la BBDD
        }
	}

	private static Alumno reconstruirAlumno(ResultSet resultado) {
        try {
			String dni = resultado.getString(1);
            String nombre = resultado.getString(2);
			float nota = resultado.getFloat(3);
			return new Alumno(dni, nombre, nota, Nivel.CFGS);
        } catch (SQLException e) {
			System.out.println("Error al obtener datos de los alumnos");
			return null;
        }
    }

	// EN CASO DE NO EXISTIR LA TABLA SE CREA
	public static void inicializarBBDD(Connection conexionBBDD) {
		String lineaSQL = "CREATE TABLE IF NOT EXISTS " + tablaAlumnos + " ("
				+ "dni VARCHAR(20) PRIMARY KEY, "
				+ "nombre VARCHAR(50), "
				+ "nota FLOAT, "
				+ "nivel ENUM('FPB', 'CFGM', 'CFGS')"
				+ ");";
        try (PreparedStatement createStatement = conexionBBDD.prepareStatement(lineaSQL)) {
			createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
} // class