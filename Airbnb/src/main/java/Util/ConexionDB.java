/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import SistemaInterno.Alojamiento;
import SistemaInterno.Resenha;
import SistemaInterno.Reserva;
import SistemaInterno.Sistema;
import static SistemaInterno.Sistema.getOpcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Usuarios.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static int idServicio = 10;
    private static int idRegla = 10;
    private static int idFavorito = 10;
    
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
    
//-------------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA CONSULTAR DATOS DE LA BD       -------------------------------------------
//-------------------------------------------------------------------------------------------------------------
    
    public static void mostrarReservasInfo(Connection conexion, int id_usuario) {
        try {
            // Iterar a través de los resultados y mostrar los atributos de cada reserva
            String consulta = "SELECT * FROM reserva WHERE cliente_id = " + id_usuario;
            ResultSet rs = realizarConsultar(conexion, consulta);
            while (rs.next()) {
                int reservaId = rs.getInt("reserva_id");
                int clienteId = rs.getInt("cliente_id");
                int alojamientoId = rs.getInt("alojamiento_id");
                String fechaInicio = rs.getString("fecha_ingreso");
                String fechaSalida = rs.getString("fecha_salida");

                // Mostrar los atributos de la reserva
                System.out.print("Reserva ID: " + reservaId);
                System.out.print(", Cliente ID: " + clienteId);
                System.out.print(", fecha IN: " + fechaInicio);
                System.out.print(", FechaSA: " + fechaSalida);
                System.out.println(", Alojamiento ID: " + alojamientoId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public static ArrayList<Reserva> reservasPorCliente(int id_usuario) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM reserva WHERE cliente_id = " + id_usuario;
        ResultSet rs = realizarConsultar(c, consulta);
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
    
    
    public static ArrayList<Reserva> reservasPorAlojamiento(int id_alojamiento) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM reserva WHERE alojamiento_id = " + id_alojamiento;
        ResultSet rs = realizarConsultar(c, consulta);
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

    
    public static ArrayList<Usuario> usuarios() {
        Connection c = ConexionDB.getConection();
        ArrayList<Usuario> usuarios= new ArrayList<>();
        String consulta = "SELECT * FROM cliente union SELECT * FROM anfitrion";
        ResultSet rs = realizarConsultar(c, consulta);
        try {

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
            System.out.println(e.getMessage());
        }
        return usuarios;
    }
    
    public static ArrayList<Alojamiento> alojamientos() {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM alojamiento";
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList<Usuario> usuarios = usuarios();
        ArrayList<Alojamiento> alojamientos = new ArrayList<>();
        
        try {
            while (rs.next()) {
                int alojamientoId = rs.getInt("alojamiento_id");
                int anfitrionId = rs.getInt("anfitrion_id");
                double precioNoche = rs.getDouble("precio_noche");
                int habitaciones = rs.getInt("habitaciones");
                String ubicacion = rs.getString("ubicacion");
                double calificacion = rs.getDouble("calificacion");
                double tarifaAirbnb = rs.getDouble("tarifa_airbnb");
                
//                ArrayList <String> servicios = servicios(alojamientoId);
//                ArrayList <String> reglamentos = reglamento(alojamientoId);
                
                for (Usuario u: usuarios){
                    if (u.getUsuarioID()==(anfitrionId)){  
                        Anfitrion anfitrion = (Anfitrion)u;
                        Alojamiento a = new Alojamiento(alojamientoId,anfitrion,precioNoche,habitaciones,ubicacion,tarifaAirbnb);                      
                        //Agrega los servicios,reglas y calificacion del alojamiento
//                        a.setServicios(servicios);
//                        a.setReglamento(reglamentos);
                        a.cambiarCalificaion(calificacion);
                        alojamientos.add(a);
                        resenhas(a);
                    }                    
                }
            }    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alojamientos;
    }
    
    public static ArrayList<String> servicios(int alojamientoId) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM servicio_alojamiento WHERE alojamiento_id = " + alojamientoId;
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList <String> servicios = new ArrayList<>();

        try {
            while (rs.next()) {
                String servicio = rs.getString("servicio");
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return servicios;
    }
    
    public static ArrayList<String> reglamento(int alojamientoId) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM regla_alojamiento WHERE alojamiento_id = " + alojamientoId;
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList <String> reglamentos = new ArrayList<>();

        try {
            while (rs.next()) {
                String regla = rs.getString("regla");
                reglamentos.add(regla);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reglamentos;
    }
    
    public static ArrayList<Alojamiento> favoritos(int clienteId) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM lista_favorito WHERE cliente_id = " + clienteId;
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList <Alojamiento> alojamientos = alojamientos();
        ArrayList <Alojamiento> alojFavs = new ArrayList<>();
        try {
            while (rs.next()) {
                int aloj_id = rs.getInt("alojamiento_id");
                for (Alojamiento a:alojamientos){
                    if(a.getAlojaminetoID()==aloj_id){
                        alojFavs.add(a);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alojFavs;
    }
    
    public static ArrayList<Resenha> resenhas(Alojamiento alojamiento) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM resenia WHERE alojamiento_id = " + alojamiento.getAlojaminetoID();
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList <Resenha> resenhas = new ArrayList<>();
        ArrayList<Usuario> usuarios = usuarios();

        try {
            while (rs.next()) {
                String comentario = rs.getString("comentario");
                double calificacion = rs.getDouble("calificacion");
                int idCliente = rs.getInt("cliente_id");
                
                for (Usuario u: usuarios){
                    if (u.getUsuarioID()==(idCliente)){  
                        Cliente cliente = (Cliente)u;
                        Resenha r = new Resenha(comentario,alojamiento,calificacion,cliente);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resenhas;
    }
    
    public static void resenhas(Cliente cliente) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM resenia WHERE cliente_id = " + cliente.getUsuarioID();
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList<Alojamiento> alojamientos = alojamientos();

        try {
            while (rs.next()) {
                String comentario = rs.getString("comentario");
                double calificacion = rs.getDouble("calificacion");
                int idalojamiento = rs.getInt("alojamiento_id");
                
                for (Alojamiento a: alojamientos){
                    if (a.getAlojaminetoID()==(idalojamiento)){  
                        Resenha r = new Resenha(comentario,a,calificacion,cliente);
                        System.out.println(r.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
//----------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA REGISTRAR(INSERT) DATOS EN LA BD       --------------------------------
//----------------------------------------------------------------------------------------------------------
    
    public static void registrarReserva(Reserva r) {
        Connection c = ConexionDB.getConection();

        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO reserva VALUES (?,?,?,?,?)");
            stmt.setString(1, ""+r.getReservaID());
            stmt.setString(2, ""+r.getClienteID());
            stmt.setString(3, ""+r.getAlojaminetoID());
            stmt.setString(4, r.getFechaInicio());
            stmt.setString(5, r.getFechaSalida());
            stmt.executeUpdate();
            System.out.println("**SE HA REALIZADO SU RESERVA CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void registrarAlojamiento(Alojamiento a) {
        Connection c = ConexionDB.getConection();

        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO alojamiento VALUES (?,?,?,?,?,?)");
            stmt.setString(1, ""+a.getAlojaminetoID());
            stmt.setString(2, ""+a.getAnfitrion().getUsuarioID());
            stmt.setString(3, ""+a.getHabitaciones());
            stmt.setString(4, a.getUbicacion());
            stmt.setString(5, ""+a.getCalificacion());
            stmt.setString(5, ""+a.getTarifaAirbnb());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void registrarServicio(int aloj_id,String servicio) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO servicio_alojamiento VALUES (?,?,?)");
            stmt.setString(1, ""+aloj_id);
            stmt.setString(2, ""+idServicio);
            stmt.setString(3, servicio);
            stmt.executeUpdate();
            idServicio++;
            System.out.println("** REGISTRADO CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void registrarRegla(int aloj_id,String regla) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO regla_alojamiento VALUES (?,?,?)");
            stmt.setString(1, ""+aloj_id);
            stmt.setString(2, ""+idRegla);
            stmt.setString(3, regla);
            stmt.executeUpdate();
            idRegla++;
            System.out.println("** REGISTRADO CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void registrarResenha(int cliente_id,int aloj_id,String comentario,double calificacion) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO resenia VALUES (?,?,?,?)");
            stmt.setString(1, ""+cliente_id);
            stmt.setString(2, ""+aloj_id);
            stmt.setString(3, comentario);
            stmt.setString(4, ""+calificacion);
            stmt.executeUpdate();
            System.out.println("** REGISTRADO CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void registrarFavorito(int aloj_id,int cliente_id) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO lista_favorito VALUES (?,?)");
            stmt.setString(1, ""+aloj_id);
            stmt.setString(2, ""+cliente_id);
            stmt.executeUpdate();
            System.out.println("** REGISTRADO CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
//---------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA ELIMINAR(DELETE) DATOS EN LA BD       --------------------------------
//---------------------------------------------------------------------------------------------------------
    
 
    public static void deleteAlojamiento(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call procedure 'nombre del sp (falta crearlo)'("+aloj.getAlojaminetoID()+")");
            stmt.executeUpdate();
            System.out.println("**SE HA ELIMINADO CORRECTAMENTE**");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    
    public static void deleteReserva(Alojamiento aloj,String fecha) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call procedure 'nombre del sp (falta crearlo)'("+aloj.getAlojaminetoID()+",'"+fecha+"')");
            stmt.executeUpdate();
            System.out.println("**SE HA ELIMINADO CORRECTAMENTE**");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    

//----------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA MODIFICAR(UPDATE) DATOS EN LA BD       --------------------------------
//----------------------------------------------------------------------------------------------------------
    
    public static void modificarAlojamiento(Anfitrion anfitrion){
        Scanner sc = new Scanner(System.in);
        Connection c = ConexionDB.getConection();
        Alojamiento aloj = Sistema.elegirUnAlojamiento(anfitrion);
        if (aloj!=null){
            System.out.println("""
                               Que cambio desea realizar?
                               1. cantidad de habitaciones
                               2. precio x noche
                               3. ubicacion del alojamiento
                               4. Volver
                               """);
            int opcionE = getOpcion(4);
            if(opcionE!=4){
                String tipo=null;
                String cambio=null;             
                try {
                    switch(opcionE){
                        case 1:
                            tipo = "habitaciones";
                            System.out.println("Numero de habitaciones: ");
                            cambio = ""+sc.nextInt();
                            sc.nextLine();
                                break;
                        case 2:
                            tipo = "precio_noche";
                            System.out.println("Nuevo precio: ");
                            cambio = ""+sc.nextDouble();
                            sc.nextLine();
                            break;
                        case 3:
                            tipo = "ubicacion";
                            System.out.println("Nueva ubicacion: ");
                            cambio = "'"+sc.nextLine()+"'";
                            break;
                    }

                        PreparedStatement stmt = c.prepareStatement("UPDATE alojamiento SET "+tipo+" = "+cambio+" WHERE alojamiento_id="+aloj.getAlojaminetoID());
                        stmt.executeUpdate();
                        System.out.println("**SE HA CAMBIADO CORRECTAMENTE**");
                } catch (SQLException e) {
                    System.out.print(e.getMessage());
                }
                
                System.out.println("Desea realizar otro cambio a este alojamiento?");
                System.out.println("1. Si\n2. No");
                int opcion2 = getOpcion(2);
                if(opcion2==1){
                    modificarAlojamiento(anfitrion);
                }
            }
        }
    }
    
}
