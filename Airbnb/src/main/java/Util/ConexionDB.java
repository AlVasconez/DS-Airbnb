/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
