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

/**
 *
 * @author vv
 */
public class PersistenciaFavoritos implements RealizarConsulta, MostrarElemento {
    
    public PersistenciaFavoritos(){}
    
    @Override
    public void mostrar(int clienteId) {
        String consulta = "SELECT * FROM lista_favorito WHERE cliente_id = " + clienteId;
        ResultSet rs = realizarConsulta(consulta);
        PersistenciaAlojamiento aloj = new PersistenciaAlojamiento();
        try {
            while (rs.next()) {
                aloj.mostrar(rs.getInt("alojamiento_id"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void guardar(int aloj_id,int cliente_id) {
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarAlojamientoFavorito(?,?)");
            stmt.setString(1, ""+aloj_id);
            stmt.setString(2, ""+cliente_id);
            stmt.executeUpdate();
            System.out.println("** REGISTRADO CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
