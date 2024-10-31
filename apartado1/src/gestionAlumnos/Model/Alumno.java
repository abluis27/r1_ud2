package gestionAlumnos.Model;

public class Alumno {

    private String DNI;
    private String nombre;
    private String apellidos;
    private String CP;

    public Alumno(String DNI, String nombre, String apellidos, String CP) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.CP = CP;
    }


    public String getDNI() {
        return DNI;
    }

    public void setDNI(String dNI) {
        DNI = dNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String cP) {
        CP = cP;
    }

    public String toString() {
        return DNI + " -- " + nombre + " -- " + apellidos + " -- " + CP;
    }
}
