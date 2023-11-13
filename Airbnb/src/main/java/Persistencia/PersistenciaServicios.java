/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;


import Util.*;
import Util.RealizarConsulta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vv
 */
public class PersistenciaServicios implements RealizarConsulta, MostrarElemento{
    
    public PersistenciaServicios(){}
    
    @Override
    public void mostrar(int alojamientoId) {
        String consulta = "SELECT * FROM servicio_alojamiento WHERE alojamiento_id = " + alojamientoId;
        ResultSet rs = realizarConsulta(consulta);
        int cont = 0;

        try {
            System.out.println("*** SERVICIOS  ***");
            while (rs.next()) {
                System.out.println(rs.getString("servicio"));
                cont++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(cont==0){
                System.out.println("No posee servicios registrados.");
            }
        }
    }
    
    public void guardar(Integer aloj_id,String servicio) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarServicioA (?,?)");
            stmt.setString(1, ""+aloj_id);
            stmt.setString(2, ""+servicio+"");
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
