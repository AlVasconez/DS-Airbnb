/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class Sistema {
    
    public static ArrayList<Alojamiento> alojamientos = new ArrayList<>();
    public static ArrayList<Usuario> usuarios = new ArrayList<>();

    
    private static boolean verificacion(String usuario, String contrasenha){
        Usuario u1 = new Cliente("12","12","12","12","12",12,false);
        usuarios.add(u1);

        return usuarios.contains(new Cliente(usuario,contrasenha));
    }
       
    private static boolean inicioSesion(){
        Scanner sc = new Scanner(System.in);
        System.out.print( """
                           AIRBNB
                    Menu de inicio de sesion
                """);
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasenha = sc.nextLine();
        return verificacion(usuario, contrasenha);
    }
    
    public static void menuInicio(){
        boolean verificacion;
        do{
            verificacion = inicioSesion();
            if(!verificacion){
                System.out.println("Usuario o contraseña erroneos.");
            }
        }while(!verificacion);
    }
    
    public static int menuPrincipal(){
        
        System.out.print( """
                          MENU PRINCIPAL
                    1. Ver alojaminetos
                    2. Publicar alojamiento
                    3. Cerrar Sesion
                """);
        
        Scanner sc = new Scanner(System.in);
        int opcion=7;
        do{
            try{
                System.out.print("Escoja una opcion: ");
                opcion = sc.nextInt();
            }
            catch(Exception e){
                System.out.println("****ESO NO ES UN NÚMERO*****");
            }
            finally{
                sc.nextLine();
            }
            if(opcion>3){
                System.out.println("****ESCOJA UNA DE LAS OPCIONES MOSTRADAS*****\n");
            }
        }while(opcion<1 || opcion>3);

        return opcion;
    }
    
}
