package gestionAlumnos.Model;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ModeloAlumnosJDBC modelo = new ModeloAlumnosJDBC();

        int opcion = 1;
        while (opcion != 0){
            System.out.println("Elige una opcion: \n" + "1.Crear Alumno \n" + "2.Eliminar alumno \n" + "3.Modificar alumno \n" + "4.Buscar alumno por DNI \n" + "5.Ver todos los alumnos \n" + "0.Salir" );
            opcion = sc.nextInt();
            switch (opcion){
                case 0:
                    opcion = 0;
                    break;
                case 1:
                    System.out.println("Introduce el DNI del alumno: ");
                    String dni = sc.nextLine();

                    System.out.println("Introduce el Nombre del alumno: ");
                    String nombre = sc.nextLine();

                    System.out.println("Introduce los apellidos del alumno: ");
                    String apellidos = sc.nextLine();

                    System.out.println("Introduce el CP");
                    String CP = sc.nextLine();

                    Alumno Alumno1 = new Alumno(dni,nombre,apellidos,CP);

                    modelo.crearAlumno(Alumno1);
                    System.out.println("El alumno " + Alumno1.getNombre() + " ha sido creado");
                    break;
                case 2:
                    System.out.println("Introduce el DNI del alumno: ");
                    String DNI = sc.nextLine();
                    modelo.eliminarAlumno(DNI);
                    System.out.println("El alumno con DNI: " + DNI + " ha sido eliminado");
                    break;
                case 3:
                    System.out.print("Introduce el DNI del alumno a modificar: ");
                    dni = sc.nextLine();
                    Alumno alumno = modelo.getAlumnoPorDNI(dni);

                    if (alumno != null) {
                        System.out.print("Introduce el nuevo nombre: ");
                        nombre = sc.nextLine();
                        System.out.print("Introduce los nuevos apellidos: ");
                        apellidos = sc.nextLine();
                        System.out.print("Introduce el nuevo código postal: ");
                        int cp = sc.nextInt();
                        sc.nextLine();

                        alumno.setNombre(nombre);
                        alumno.setApellidos(apellidos);
                        alumno.setCP(String.valueOf(cp));

                        modelo.modificarAlumno(alumno);
                        System.out.println("Alumno modificado con éxito.");
                    } else {
                        System.out.println("No se encontró un alumno con ese DNI.");
                    }
                    break;
                case 4:
                    System.out.println("Introduce el DNI del alumno: ");
                    DNI = sc.nextLine();
                    Alumno AlumnoDni = modelo.getAlumnoPorDNI(DNI);
                    System.out.println( AlumnoDni.toString());
                    break;
                case 5:
                    System.out.println("Todos los alumnos: " +"\n" + modelo.getAll());
                    break;
            }
        }
    }

}
