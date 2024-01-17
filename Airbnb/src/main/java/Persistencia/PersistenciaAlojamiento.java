/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import SistemaInterno.Alojamiento;
import SistemaInterno.Sistema;
import static SistemaInterno.Sistema.getOpcion;
import Usuarios.Anfitrion;
import Usuarios.Usuario;
import Util.ConexionDB;
import Util.*;
import Util.RealizarConsulta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author vv
 */
public class PersistenciaAlojamiento implements RealizarConsulta, MostrarElemento {
   
    public PersistenciaAlojamiento(){}
    
    public ArrayList<Alojamiento> listarAlojamientos() {
        ArrayList<Alojamiento> alojamientos = new ArrayList<>();
        String consulta = "call consultarAlojamiento()";
        ResultSet rs = realizarConsulta(consulta);
        PersistenciaUsuarios listaUsuarios = new PersistenciaUsuarios();
        ArrayList<Usuario> usuarios = listaUsuarios.listarUsuarios();
        
        try {
            while (rs.next()) {
                int alojamientoId = rs.getInt("alojamiento_id");
                int anfitrionId = rs.getInt("anfitrion_id");
                double precioNoche = rs.getDouble("precio_noche");
                int habitaciones = rs.getInt("habitaciones");
                String ubicacion = rs.getString("ciudad")+"-"+rs.getString("pais");
                double tarifaAirbnb = rs.getDouble("tarifa_airbnb");
                String nombreA = rs.getString("nombre");
                
                for (Usuario u: usuarios){
                    if (u.getUsuarioID()==(anfitrionId)){  
                        Anfitrion anfitrion = (Anfitrion)u;
                        Alojamiento a = new Alojamiento(alojamientoId,anfitrion,precioNoche,habitaciones,ubicacion,tarifaAirbnb,nombreA);
                        alojamientos.add(a);
                    }                    
                }
            }    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alojamientos;
    }
    
    public int mostrarTodo(){
        String consulta = "select * from vistaInformacionAlojamientos";
        ResultSet rs = realizarConsulta(consulta);
        int pos = 1;
        
        try {
            while(rs.next()){
                String nombreAlojamiento = rs.getString("nombre_alojamiento");
                String pais = rs.getString("pais");
                String ciudad = rs.getString("ciudad");
                double precioNoche = rs.getDouble("precio_noche");
                String nombreAnfitrion = rs.getString("nombre_anfitrion");
                double promedioResenas = rs.getDouble("promedio_resenas");

                String formattedLine = String.format(
                    "%d. %s, País: %s, Ciudad: %s, Precio/Noche: %.2f, Anfitrión: %s, Promedio Reseñas: %.2f",
                    pos,nombreAlojamiento, pais, ciudad, precioNoche, nombreAnfitrion, promedioResenas
                );
                pos++;
                System.out.println(formattedLine);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println(pos + ".- Volver");
        return Sistema.getOpcion(pos);
    }
    
    @Override
    public void mostrar(int alojamientoId){
        String consulta = "select * from vistaInformacionAlojamientos";
        ResultSet rs = realizarConsulta(consulta);
        
        try {
            while (rs.next()) {
                if(alojamientoId==rs.getInt("alojamiento_id")){
                    String nombreAlojamiento = rs.getString("nombre_alojamiento");
                    String pais = rs.getString("pais");
                    String ciudad = rs.getString("ciudad");
                    double precioNoche = rs.getDouble("precio_noche");
                    String nombreAnfitrion = rs.getString("nombre_anfitrion");
                    double promedioResenas = rs.getDouble("promedio_resenas");

                    String formattedLine = String.format(
                    " %s, País: %s, Ciudad: %s, Precio/Noche: %.2f, Anfitrión: %s, Promedio Reseñas: %.2f",
                    nombreAlojamiento, pais, ciudad, precioNoche, nombreAnfitrion, promedioResenas
                );
                    System.out.println(formattedLine);

                }
            }    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void guardar(Alojamiento alojamiento){
        Connection c = ConexionDB.getConection();
        String[] ubicacionA = alojamiento.getUbicacion().split("-");

        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarAlojamiento(?,?,?,?,?,?,?)");
            stmt.setString(1, ""+alojamiento.getAnfitrion());
            stmt.setString(2, ""+alojamiento.getPrecio());
            stmt.setString(3, ""+alojamiento.getHabitaciones());
            stmt.setString(4, ""+ubicacionA[1]+"");
            stmt.setString(5, ""+ubicacionA[0]+"");
            stmt.setString(6, ""+alojamiento.getTarifaAirbnb());
            stmt.setString(7, ""+alojamiento.getNombre()+"");
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void modificar(Anfitrion anfitrion){
        Scanner sc = new Scanner(System.in);
        Connection c = ConexionDB.getConection();
        Alojamiento aloj = Sistema.elegirUnAlojamiento(anfitrion);
        if (aloj!=null){
            System.out.println("""
                               Que cambio desea realizar?
                               1. cantidad de habitaciones
                               2. precio x noche
                               3. ubicacion del alojamiento
                               4. nombre del alojamiento
                               5. Volver
                               """);
            int opcionE = getOpcion(5);
            if(opcionE!=5){
                PreparedStatement stmt=null;
                String cambio="";
                
                try {
                    switch(opcionE){
                        case 1:
                            System.out.println("Numero de habitaciones: ");
                            cambio = ""+sc.nextInt();
                            stmt = c.prepareStatement("call updateAlojamientoHabitacion(?,?)");
                            break;
                        case 2:
                            System.out.println("Nuevo precio: ");
                            cambio = ""+sc.nextDouble();
                            stmt = c.prepareStatement("call updateAlojamientoCosto(?,?)");
                            break;
                        case 3:
                            System.out.println("Nueva ubicacion: ");
                            cambio = ""+sc.nextLine()+"";
                            stmt = c.prepareStatement("call updateAlojamientoUbi(?,?)");
                            break;
                        case 4:
                            System.out.println("Nuevo nombre: ");
                            cambio = ""+sc.nextLine()+"";
                            stmt = c.prepareStatement("call updateAlojamientoNombre(?,?)");
                            break;
                    }
                    
                    sc.nextLine();
                    stmt.setString(1, ""+aloj.getAlojaminetoID());
                    stmt.setString(2, ""+cambio);
                    stmt.executeUpdate();
                    
                }catch (SQLException e) {
                System.out.println(e.getMessage());
                }  
                  
                System.out.println("Desea realizar otro cambio a este alojamiento?");
                System.out.println("1. Si\n2. No");
                int opcion2 = getOpcion(2);
                if(opcion2==1){
                    modificar(anfitrion);
                }
            }
        }
    }
    
    public void delete(Alojamiento alojamiento) {
        Connection c = ConexionDB.getConection();
        
        try {
            PreparedStatement stmt = c.prepareStatement("call procedure deleteAlojamiento("+alojamiento.getAlojaminetoID()+")");
            stmt.executeUpdate();
            System.out.println("**SE HA ELIMINADO CORRECTAMENTE**");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    
}
