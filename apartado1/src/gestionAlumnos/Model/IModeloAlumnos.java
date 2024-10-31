package gestionAlumnos.Model;

import java.util.List;

public interface IModeloAlumnos {

    public List<String> getAll();

    public Alumno getAlumnoPorDNI(String DNI);

    public void modificarAlumno(Alumno alumno);

    public void eliminarAlumno(String DNI);

    public void crearAlumno(Alumno alumno);


}
