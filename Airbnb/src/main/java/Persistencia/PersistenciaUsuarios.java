/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Usuarios.Anfitrion;
import Usuarios.Cliente;
import Usuarios.Usuario;
import Util.RealizarConsulta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vv
 */
public class PersistenciaUsuarios implements RealizarConsulta{
    
    public PersistenciaUsuarios(){}
    
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> usuarios= new ArrayList<>();
        String consulta = "SELECT * FROM cliente union SELECT * FROM anfitrion";
        ResultSet rs = realizarConsulta(consulta);
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
}
