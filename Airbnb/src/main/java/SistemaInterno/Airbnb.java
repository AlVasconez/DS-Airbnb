/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package SistemaInterno;

import Usuarios.Usuario;
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
        //El programa no se detiene a menos que se pare de ejecutar.
        //Permite cambiar de cuentas dentro del mismo proceso.
        while(true){
            int opcion=0;
            boolean ingreso=false;
            Usuario user = null;
            
            do{
                Scanner sc = new Scanner(System.in);
                System.out.print( """
                                   AIRBNB
                            Menu de inicio de sesion
                        """);
                System.out.print("Usuario: ");
                String usuario = sc.nextLine();
                System.out.print("Contraseña: ");
                String contrasenha = sc.nextLine();
                
                ingreso= Sistema.verificacion(usuario, contrasenha);
                user = Sistema.getUsuario(usuario, contrasenha);
                
            }while(ingreso==false);
            do{
            opcion =Sistema.menuPrincipal();
                    //ingresar metodos en cada caso
                switch(opcion){
                    case 1:
                        // ConexionDB.mostrarAlojamientoInfo(c);
                        Sistema.verAlojamientos();
                        break;
                    case 2:
                        ConexionDB.crearListaUsuario();
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
