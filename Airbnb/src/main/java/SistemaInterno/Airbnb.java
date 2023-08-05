/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package SistemaInterno;

import Usuarios.*;
import Util.ConexionDB;
import java.sql.Connection;
import java.util.Scanner;



/**
 *
 * @author vv
 */
public class Airbnb {
    

    public static void main(String[] args) {
        Connection c = ConexionDB.getConection();
        Scanner sc = new Scanner(System.in);

        while(true){
            int opcion;
            Usuario usuario;
                         
            usuario = Sistema.menuIngreso();
            do{            
            opcion =usuario.menuUsuario();
                    
                switch(opcion){
                    case 1:

                        break;
                    case 2:
                        Sistema.verAlojamientos();
                        break;
                    case 3:
                        System.out.println("opcion3");
                        break;
                    default:
                        System.out.println("Ingrese una opción correcta.");
                }
            }
            while(opcion!=3);
        }
    }
}
