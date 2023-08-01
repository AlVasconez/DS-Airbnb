/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import SistemaInterno.Alojamiento;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Usuarios.*;
import java.util.ArrayList;

/**
 *
 * @author robes
 */
public class ConexionDB {
    private static ConexionDB instance;
    private Connection conn = null;
    private String db = "airbnb";
    private String user = "Airbnb";
    private String password = "Proyecto-Airbnb";
    private String ip = "localhost";
    private String puerto = "3306";
    private String cadenaCon = String.format("jdbc:mysql://%s/%s",ip,db);
    
    private ConexionDB(){
        try{
            conn = DriverManager.getConnection(cadenaCon,user,password);
            System.out.println("La conexion se realizó correctamente");
        }catch(SQLException e1){
            System.out.println("No se pudo conectar");
            System.out.println(e1.getMessage());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static Connection getConection(){
        if (instance==null) {
            instance = new ConexionDB();
        }
        return instance.conn;
    }
    
    private static ResultSet realizarConsultar(Connection conexion, String consulta) {
        ResultSet rs = null;
        try {
            // Consulta SQL para obtener los alojamientos

            // Preparar la consulta
            PreparedStatement pstmt = conexion.prepareStatement(consulta);

            // Ejecutar la consulta y obtener el resultado
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
     public static void mostrarReservasInfo(Connection conexion, int id_usuario) {
        try {
            // Iterar a través de los resultados y mostrar los atributos de cada reserva
            String consulta = "SELECT * FROM reservas WHERE cliente_id = " + id_usuario;
            ResultSet rs = realizarConsultar(conexion, consulta);
            while (rs.next()) {
                int reservaId = rs.getInt("reserva_id");
                int clienteId = rs.getInt("cliente_id");
                int alojamientoId = rs.getInt("alojamiento_id");
                int tiempoEstancia = rs.getInt("tiempo_estancia");

                // Mostrar los atributos de la reserva
                System.out.print("Reserva ID: " + reservaId);
                System.out.print(", Cliente ID: " + clienteId);
                System.out.print(", Alojamiento ID: " + alojamientoId);
                System.out.println(", Tiempo de Estancia: " + tiempoEstancia + " días");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void mostrarAlojamientoInfo(Connection conexion) {
        String consulta = "SELECT * FROM alojamiento";
        ResultSet rs = realizarConsultar(conexion, consulta);
        try {
            // Iterar a través de los resultados y mostrar los atributos de cada alojamiento
            while (rs.next()) {
                int alojamientoId = rs.getInt("alojamiento_id");
                int anfitrionId = rs.getInt("anfitrion_id");
                double precioNoche = rs.getDouble("precio_noche");
                int habitaciones = rs.getInt("habitaciones");
                String ubicacion = rs.getString("ubicacion");
                double calificacion = rs.getDouble("calificacion");

                // Mostrar los atributos del alojamiento
                System.out.print("Alojamiento ID: " + alojamientoId);
                System.out.print(", Anfitrión ID: " + anfitrionId);
                System.out.print(", Precio por Noche: " + precioNoche);
                System.out.print(", Habitaciones: " + habitaciones);
                System.out.print(", Ubicación: " + ubicacion);
                System.out.println(", Calificación: " + calificacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void mostrarUsuarioInfo(Connection conexion) {
        String consulta = "SELECT * FROM cliente union SELECT * FROM anfitrion";
        ResultSet rs = realizarConsultar(conexion, consulta);
        try {
            // Iterar a través de los resultados y mostrar los atributos de cada usuario
            while (rs.next()) {
                int usuarioID = rs.getInt("usuario_id");
                String nombre = rs.getString("nombre");
                String contraseña = rs.getString("contrasenia");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String direccion = rs.getString("direccion_fisica");
                boolean verificacion = rs.getBoolean("verificador");

                // Mostrar los atributos del usuario
                System.out.print("Usuario ID: " + usuarioID);
                System.out.print(", Contraseña: " + contraseña);
                System.out.print(", Nombre: " + nombre);
                System.out.print(", Apellido: " + nombre);
                System.out.print(", Correo: " + correo);
                System.out.print(", Telefono: " + telefono);
                System.out.print(", Direccion: " + direccion);
                System.out.println(", Verificado: " + verificacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
        public static ArrayList<Usuario> crearListaUsuario() {
            Connection c = ConexionDB.getConection();
            ArrayList<Usuario> usuarios= new ArrayList<>();
            String consulta = "SELECT * FROM cliente union SELECT * FROM anfitrion";
            ResultSet rs = realizarConsultar(c, consulta);
            try {
                // Iterar a través de los resultados y mostrar los atributos de cada usuario
                while (rs.next()) {
                    Integer usuarioID = rs.getInt("usuario_id");
                    String nombre = rs.getString("nombre");
                    String contraseña = rs.getString("contrasenia");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String direccionFisica = rs.getString("direccion_fisica");
                    boolean verificacion = rs.getBoolean("verificador");
                    Usuario u = null;

                    if(verificacion){
                        u = new Anfitrion(usuarioID,contraseña,nombre,correo,telefono,direccionFisica,verificacion);
                    }
                    if (verificacion==false){
                        u = new Cliente(usuarioID,contraseña,nombre,correo,telefono,direccionFisica,verificacion);
                    }

                    usuarios.add(u);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return usuarios;
    }
    
    public static ArrayList<Alojamiento> crearListaAlojamiento() {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM alojamiento";
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList<Usuario> usuarios = crearListaUsuario();
        ArrayList<Alojamiento> alojamientos = new ArrayList<>();
        try {
            // Iterar a través de los resultados y mostrar los atributos de cada alojamiento
            while (rs.next()) {
                int alojamientoId = rs.getInt("alojamiento_id");
                Integer anfitrionId = rs.getInt("anfitrion_id");
                double precioNoche = rs.getDouble("precio_noche");
                int habitaciones = rs.getInt("habitaciones");
                String ubicacion = rs.getString("ubicacion");
                double calificacion = rs.getDouble("calificacion");
                
                Anfitrion anfitrion = null;
                for (Usuario u: usuarios){
                    if (u.getUsuarioID().equals(anfitrionId.toString())){
                        anfitrion = (Anfitrion)u;
                    }
                }

                Alojamiento a = new Alojamiento(alojamientoId,anfitrion,precioNoche,habitaciones,ubicacion);
                a.cambiarCalificaion(calificacion);
                alojamientos.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alojamientos;
    }
    
    public static ArrayList<String> listaServicios() {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM servicio_alojamiento";
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList <String> servicios = new ArrayList<>();

        try {
            // Iterar a través de los resultados y mostrar los atributos de cada alojamiento
            while (rs.next()) {
                Integer alojamientoId = rs.getInt("alojamiento_id");
                String servicio = rs.getString("servicio");
                
                String servicioDatos = alojamientoId.toString()+"-"+servicio;

                servicios.add(servicioDatos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }
}
