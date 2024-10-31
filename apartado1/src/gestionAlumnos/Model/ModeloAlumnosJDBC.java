package gestionAlumnos.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModeloAlumnosJDBC implements IModeloAlumnos {

    private static String cadenaConexion = "jdbc:mysql://localhost:3306/adat";
    private static String user = "dam2";
    private static String pass = "asdf.1234";

    Connection miConexion;
    Statement miStatement;

    public ModeloAlumnosJDBC() {
        try {
            miConexion = DriverManager.getConnection(cadenaConexion, user, pass);
            miStatement = miConexion.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAll() {
        List<String> results = new ArrayList<>();
        try {
            ResultSet result = miStatement.executeQuery("SELECT * FROM Alumnos;");
            while (result.next()) {
                String dni = result.getString("DNI");
                String nombre = result.getString("nombre");
                String apellidos = result.getString("apellidos");
                String CP = result.getString("CP");

                results.add("DNI: " + dni + ", Nombre: " + nombre + ", Apellidos: " + apellidos + ", CP: " + CP + "\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    @Override
    public Alumno getAlumnoPorDNI(String DNI) {
        Alumno alumno = null;
        String query = "SELECT * FROM Alumnos WHERE DNI = ?";
        try (PreparedStatement ps = miConexion.prepareStatement(query)) {
            ps.setString(1, DNI);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String dni = result.getString("DNI");
                String nombre = result.getString("nombre");
                String apellidos = result.getString("apellidos");
                String CP = result.getString("CP");

                alumno = new Alumno(dni, nombre, apellidos, CP);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumno;
    }

    @Override
    public void modificarAlumno(Alumno alumno) {
        String query = "UPDATE Alumnos SET nombre = ?, apellidos = ?, CP = ? WHERE DNI = ?";
        try (PreparedStatement ps = miConexion.prepareStatement(query)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidos());
            ps.setString(3, alumno.getCP());
            ps.setString(4, alumno.getDNI());
            int rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarAlumno(String DNI) {
        String query = "DELETE FROM Alumnos WHERE DNI = ?";
        try (PreparedStatement ps = miConexion.prepareStatement(query)) {
            ps.setString(1, DNI);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void crearAlumno(Alumno alumno) {
        String sql = "INSERT INTO Alumnos (DNI, nombre, apellidos, CP) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = miConexion.prepareStatement(sql)) {
            ps.setString(1, alumno.getDNI());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellidos());
            ps.setString(4, alumno.getCP());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
