/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import SistemaInterno.Alojamiento;
import SistemaInterno.Reserva;
import Util.ConexionDB;
import Util.*;
import Util.RealizarConsulta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vv
 */
public class PersistenciaReserva implements RealizarConsulta, MostrarElemento{
    
    public PersistenciaReserva(){}
    
    @Override
    public void mostrar(int id){
        String consulta = "select * from vistainformacionreservas";
        ResultSet rs = realizarConsulta(consulta);
        int pos = 1;
        try {
            while(rs.next()){
                if((rs.getInt("anfitrion_id")==id) || (rs.getInt("usuario_id")==id)){
                    int reservaId = rs.getInt("reserva_id");
                    int anfitrionId = rs.getInt("anfitrion_id");
                    String nombreAlojamiento = rs.getString("nombre_alojamiento");
                    String clienteId = rs.getString("usuario_id");
                    String fechaIngreso = rs.getString("fecha_ingreso");
                    String fechaSalida = rs.getString("fecha_salida");


                    System.out.println(pos+")\nId reserva: " + reservaId);
                    System.out.println("ID Anf: " + anfitrionId);
                    System.out.println("ID Clien: " + clienteId);
                    System.out.println("Alojamiento: " + nombreAlojamiento);
                    System.out.println("Fecha Ingreso: " + fechaIngreso);
                    System.out.println("Fecha Salida: " + fechaSalida);
                    try{
                    double montoPagar = rs.getDouble("monto_pagar");
                    System.out.print("Monto a Pagar: " + montoPagar);
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    pos++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(pos + ".- Volver");
        
    }
    
    
    public static void deleteReserva(Alojamiento aloj,String fecha) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call procedure BorrarReserva("+aloj.getAlojaminetoID()+",'"+fecha+"')");
            stmt.executeUpdate();
            System.out.println("**SE HA ELIMINADO CORRECTAMENTE**");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    
    public ArrayList<Reserva> listarReservasPorUsuario(int id) {
        String consulta = "SELECT * FROM reserva";
        ResultSet rs = realizarConsulta(consulta);
        ArrayList<Reserva> reservas= new ArrayList<>();
        try {
            
            while (rs.next()) {
                if((rs.getInt("cliente_id")==id)){
                    int reservaId = rs.getInt("reserva_id");
                    int clienteId = rs.getInt("cliente_id");
                    int alojamientoId = rs.getInt("alojamiento_id");
                    String fechaInicio = rs.getString("fecha_ingreso");
                    String fechaSalida = rs.getString("fecha_salida");

                    Reserva r = new Reserva(reservaId,clienteId,alojamientoId,fechaInicio,fechaSalida);
                    reservas.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservas;
    }
    
    
    public ArrayList<Reserva> listarReservasPorAlojamiento(int id_alojamiento) {
        String consulta = "SELECT * FROM reserva WHERE alojamiento_id = " + id_alojamiento;
        ResultSet rs = realizarConsulta(consulta);
        ArrayList<Reserva> reservas= new ArrayList<>();
        try {
            
            while (rs.next()) {
                int reservaId = rs.getInt("reserva_id");
                int clienteId = rs.getInt("cliente_id");
                int alojamientoId = rs.getInt("alojamiento_id");
                String fechaInicio = rs.getString("fecha_ingreso");
                String fechaSalida = rs.getString("fecha_salida");
                
                Reserva r = new Reserva(reservaId,clienteId,alojamientoId,fechaInicio,fechaSalida);
                reservas.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservas;
    }
    
}
