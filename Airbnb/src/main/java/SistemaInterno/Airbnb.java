/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package SistemaInterno;

import Util.ConexionDB;
import java.sql.Connection;



/**
 *
 * @author vv
 */
public class Airbnb {
    

    public static void main(String[] args) {
        Connection c = ConexionDB.getConection();
        //El programa no se detiene a menos que se pare de ejecutar.
        //Permite cambiar de cuentas dentro del mismo proceso.
        while(true){
            Sistema.menuInicio();
            int opcion=0;
            do{
                opcion =Sistema.menuPrincipal();
                System.out.println("");
                //ingresar metodos en cada caso
                switch(opcion){
                    case 1:
                        ConexionDB.mostrarAlojamientoInfo(c);
                        break;
                    case 2:
                        System.out.println("opcion2");
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
