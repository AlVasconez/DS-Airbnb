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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return reservas;
    }
    
   
    public static ArrayList<Usuario> crearListaUsuario() {
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
            while (rs.next()) {
                int alojamientoId = rs.getInt("alojamiento_id");
                int anfitrionId = rs.getInt("anfitrion_id");
                double precioNoche = rs.getDouble("precio_noche");
                int habitaciones = rs.getInt("habitaciones");
                String ubicacion = rs.getString("ubicacion");
                double calificacion = rs.getDouble("calificacion");
                double tarifaAirbnb = rs.getDouble("tarifa_airbnb");
                
                ArrayList <String> servicios = listaServicios(alojamientoId);
                ArrayList <String> reglamentos = listaReglamento(alojamientoId);
                
                for (Usuario u: usuarios){
                    if (u.getUsuarioID()==(anfitrionId)){  
                        Anfitrion anfitrion = (Anfitrion)u;
                        Alojamiento a = new Alojamiento(alojamientoId,anfitrion,precioNoche,habitaciones,ubicacion,tarifaAirbnb);                      
                        //Agrega los servicios,reglas y calificacion del alojamiento
                        a.setServicios(servicios);
                        a.setReglamento(reglamentos);
                        a.cambiarCalificaion(calificacion);
                        alojamientos.add(a);
                        listaResenhas(a);
                    }                    
                }
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alojamientos;
    }
    
    private static ArrayList<String> listaServicios(int alojamientoId) {
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
            e.printStackTrace();
        }
        return servicios;
    }
    
    private static ArrayList<String> listaReglamento(int alojamientoId) {
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
            e.printStackTrace();
        }
        return reglamentos;
    }
    
    private static ArrayList<Resenha> listaResenhas(Alojamiento alojamiento) {
        Connection c = ConexionDB.getConection();
        String consulta = "SELECT * FROM resenia WHERE alojamiento_id = " + alojamiento.getAlojaminetoID();
        ResultSet rs = realizarConsultar(c, consulta);
        ArrayList <Resenha> resenhas = new ArrayList<>();
        ArrayList<Usuario> usuarios = crearListaUsuario();

        try {
            while (rs.next()) {
                String comentario = rs.getString("comentario");
                double calificacion = rs.getDouble("calificacion");
                int idCliente = rs.getInt("cliente_id");
                
                for (Usuario u: usuarios){
                    if (u.getUsuarioID()==(idCliente)){  
                        Cliente cliente = (Cliente)u;
                        Resenha r = new Resenha(comentario,alojamiento,calificacion,cliente);
                        alojamiento.addUnaResenha(r);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resenhas;
    }
    
//------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA REGISTRAR(INSERT) DATOS EN LA BD       ------------------------------------
//-----------------------------------------------------------------------------------------------------
    
    public static void registrarReserva(Reserva r) {
        Connection c = ConexionDB.getConection();

        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO reserva VALUES (?,?,?,?,?)");
            stmt.setString(1, ""+r.getReservaID());
            stmt.setString( 2,""+r.getClienteID());
            stmt.setString(3,""+r.getAlojaminetoID());
            stmt.setString(4, r.getFechaInicio());
            stmt.setString(5,r.getFechaSalida());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
    
    public static void eliminarReserva(int reservaId){
        Connection c = ConexionDB.getConection();
        String consulta =" DELETE FROM reserva WHERE reserva_id ="+reservaId+";";
        realizarConsultar(c, consulta);
        System.out.println("Reserva Eliminada");
        
    }
    
//---------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA ELIMINAR(DELETE) DATOS EN LA BD       --------------------------------
//---------------------------------------------------------------------------------------------------------
    
    public static void deleteAlojamiento(Alojamiento a) {
        Connection c = ConexionDB.getConection();
        deleteResenias(a);
        deleteFechas(a);
        deleteReserva(a);
        deleteReglas(a);
        deleteServicio(a);
        deleteFavoritos(a);
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM alojamiento WHERE alojamiento_id="+a.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteReserva(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM reserva WHERE alojamiento_id="+aloj.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteFechas(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM fechas_reservadas WHERE alojamiento_id="+aloj.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    
    public static void deleteResenias(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM resenia WHERE alojamiento_id="+aloj.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    
    public static void deleteReglas(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM regla_alojamiento WHERE alojamiento_id="+aloj.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    public static void deleteServicio(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM servicio_alojamiento WHERE alojamiento_id="+aloj.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    
    public static void deleteFavoritos(Alojamiento aloj) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM lista_favorito WHERE alojamiento_id="+aloj.getAlojaminetoID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    
//---------------------------------------------------------------------------------------------------------
//-----------------     METODOS PARA MODIFICAR(UPDATE) DATOS EN LA BD       --------------------------------
//---------------------------------------------------------------------------------------------------------
    
    public static void modificarAlojamiento(Anfitrion anfitrion){
        Scanner sc = new Scanner(System.in);
        Connection c = ConexionDB.getConection();
        Alojamiento aloj = Sistema.elegirUnAlojamiento(anfitrion);
        if (aloj!=null){
            System.out.println("""
                               Que cambio desea realizar:
                               1. precio
                               2. ubicacion
                               3. Volver
                               """);
            int opcionE = getOpcion(4);
            
            if(opcionE!=4){
                String tipo;
                String cambio;
                
                switch(opcionE){
                    case 1:
                        tipo = "habitaciones";
                        System.out.println("Numero de habitaciones: ");
                        cambio = ""+sc.nextInt();
                        sc.nextLine();
                        try {
                            PreparedStatement stmt = c.prepareStatement("UPDATE alojamiento SET "+tipo+" = '"+cambio+"' WHERE alojamiento_id="+aloj.getAlojaminetoID());
                            stmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.print(e.getMessage());
                        }
                            break;
                    case 2:
                        tipo = "precio_noche";
                        System.out.println("Nuevo precio: ");
                        cambio = ""+sc.nextDouble();
                        sc.nextLine();
                        
                        try {
                            PreparedStatement stmt = c.prepareStatement("UPDATE alojamiento SET "+tipo+"="+cambio+" WHERE alojamiento_id="+aloj.getAlojaminetoID());
                            stmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.print(e.getMessage());
                        }
                        break;

                }
                
                
                System.out.println("Desea realizar otro cambio a este alojamiento?");
                System.out.println("1:Si");
                System.out.println("2:No");
                int opcion2 = getOpcion(2);
                if(opcion2==1){
                    modificarAlojamiento(anfitrion);
                }
            }
        }
    }
        
    
    
    
}
