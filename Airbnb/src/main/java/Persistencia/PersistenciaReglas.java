/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

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
public class PersistenciaReglas implements RealizarConsulta, MostrarElemento{
    
    public PersistenciaReglas(){}
    
    @Override
    public void mostrar(int alojamientoId) {
        String consulta = "SELECT * FROM regla_alojamiento WHERE alojamiento_id = " + alojamientoId;
        ResultSet rs = realizarConsulta(consulta);
        int cont =0;
        try {
            System.out.println("*** REGLAS  ***");
            while (rs.next()) {
                System.out.println(rs.getString("regla"));
                cont++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(cont==0){
                System.out.println("No posee reglas registradas.");
            }
        }
    }
    
    public void guardar(int aloj_id,String regla) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarReglaA(?,?)");
            stmt.setString(1, ""+aloj_id);
            stmt.setString(2, ""+regla+"");
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
