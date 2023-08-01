/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.*;
import java.util.ArrayList;
import java.util.Scanner;
import Util.ConexionDB;

/**
 *
 * @author vv
 */
public class Sistema {
    
    public static ArrayList<Alojamiento> alojamientos = ConexionDB.crearListaAlojamiento();
    public static ArrayList<Usuario> usuarios = ConexionDB.crearListaUsuario();

    
    public static boolean verificacion(String usuario, String contrasenha){
        Usuario u1 = new Cliente("12","12");
        usuarios.add(u1);

        return usuarios.contains(new Cliente(usuario,contrasenha));
    }
    
    public static Usuario getUsuario(String usuario, String contrasenha){
        for (Usuario u:usuarios){
            if((u.getUsuarioID().equals(usuario)) &&(u.getContrasenha().equals(contrasenha))){
                return u;
            }
        }
        return null;
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
    
     public static int verAlojamientos(){
        
        
//        Anfitrion u2 = new Anfitrion("13","13","13","13","13",13,true);
//        usuarios.add(u2);
//        Alojamiento a1 = new Alojamiento(u2,12,4,"qwerty");
//        alojamientos.add(a1);
        
        int opcionA=0;
        Scanner sc = new Scanner(System.in);
        int contador = 1;
        
        for(Alojamiento a:alojamientos){
            try{
                System.out.print(contador+")  ");
                a.enlistarAlojamiento();
            }
            catch(Exception e){
                System.out.print(e.getMessage());
            }
            contador++;           
        }
        System.out.println(contador+")    Volver");

        do{
            try{
                System.out.print("Escoja una opcion: ");
                opcionA = sc.nextInt();
            }
            catch(Exception e){
            System.out.println("****IBGRESE EL NUMERO DEL ALOJAMIENTO DE LA LISTA*****\n");
            }
            finally{
                sc.nextLine();
            }
            if(opcionA>contador || opcionA==0){
                System.out.println("****INTENTE DE NUEVO*****\n");
            }
        }
        while(opcionA>contador || opcionA<1);
        
        return opcionA;
    }
     
    public static void enlistarAlojamientos(){
        
    }
    
    
    
    public static void menuAlojaminetos(){  
        Scanner sc = new Scanner(System.in);
        int opcion = verAlojamientos();
        
        switch (opcion){
            case 1 :
                verAlojamientos();
                break;
            case 2 :
                break;
            case 3 :
                break;
        }
    }
    
    
    
    
    public static void publicarAlojamiento(Anfitrion anfitrion){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("------ INGRESO DE DATOS DE ALOJAMIENTO ----");
        System.out.println("-Pais: ");
        String pais = sc.nextLine();
        
        System.out.println("-Ciudad: ");
        String ciudad = sc.nextLine();  
        
        System.out.println("-Costo: ");
        int costo = sc.nextInt();
        sc.nextLine();
        
        System.out.println("-Num. habutaciones: ");
        int habitaciones = sc.nextInt();
        sc.nextLine();
        
        String direccion = ciudad+"-"+pais;
        
        Alojamiento a = new Alojamiento(anfitrion,costo,habitaciones,direccion);
        
        a.addServicios();
        a.addReglamento();
    }
    
    
    
}
