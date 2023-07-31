/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author robes
 */
public class ConexionDB {
    private static ConexionDB instance;
    private Connection conn = null;
    private String db = "airbnb";
    private String user = "root";
    private String password = "root";
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
        String consulta = "SELECT * FROM alojamientos";
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
}
