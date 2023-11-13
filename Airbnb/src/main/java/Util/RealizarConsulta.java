/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vv
 */
public interface RealizarConsulta {
   
    default ResultSet realizarConsulta( String consulta) {
        Connection con = ConexionDB.getConection();
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = con.prepareStatement(consulta);
            rs = pstmt.executeQuery();
        } 
        catch (SQLException e) {
            System.out.println(e.getSQLState()+e.getMessage());
        }
        
        return rs;
    }
}
