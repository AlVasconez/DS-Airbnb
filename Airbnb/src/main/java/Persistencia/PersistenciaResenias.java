/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import SistemaInterno.Alojamiento;
import SistemaInterno.Resenia;
import Usuarios.Cliente;
import Usuarios.Usuario;
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
public class PersistenciaResenias implements RealizarConsulta{
    
    public PersistenciaResenias(){}
    
    public void mostrar(Cliente cliente) {
        String consulta = "SELECT * FROM vistainformacionresenia WHERE usuario_id = " + cliente.getUsuarioID();
        ResultSet rs = realizarConsulta(consulta);
        int cont=0;

        try {
            while (rs.next()) {
                System.out.println("- "+rs.getString("nombre_alojamiento"));
                System.out.println("    "+rs.getString("comentario"));
                System.out.println("    "+rs.getDouble("calificacion"));
                cont++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(cont==0){
                System.out.println("***No hay reseñas***");
            }
        }
    }
    
    public void mostrar(Alojamiento alojamiento) {
        String consulta = "SELECT * FROM vistainformacionresenia WHERE alojamiento_id = " + alojamiento.getAlojaminetoID();
        ResultSet rs = realizarConsulta(consulta);
        int cont=0;

        try {
            while (rs.next()) {
                System.out.println("- "+rs.getString("nombre_cliente"));
                System.out.println("    "+rs.getString("comentario"));
                System.out.println("    "+rs.getDouble("calificacion"));
                cont++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(cont==0){
                System.out.println("***No hay reseñas***");
            }
        }
    }
    
    public double calificacion(int alojamientoId) {
        String consulta = "SELECT * FROM resenia WHERE alojamiento_id = " + alojamientoId;
        ResultSet rs = realizarConsulta(consulta);
        double calificacion=0;
        try {
            while (rs.next()) {
                calificacion =calificacion+ rs.getDouble("calificacion");               
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return calificacion;
    }
    
    public void guardar(int cliente_id,int aloj_id,String comentario,double calificacion) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarResenia(?,?,?,?)");
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
    
}
