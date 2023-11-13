/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Util.ConexionDB;
import Util.RealizarConsulta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author vv
 */
public class PersistenciaMensaje implements RealizarConsulta{
    
    public void guardar(String mensaje, int anfitrion_id,int cliente_id) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarMensaje(?,?,?)");
            stmt.setString(1, ""+mensaje);
            stmt.setString(2, ""+anfitrion_id);
            stmt.setString(3, ""+cliente_id);
            stmt.executeUpdate();
            System.out.println("** REGISTRADO CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
