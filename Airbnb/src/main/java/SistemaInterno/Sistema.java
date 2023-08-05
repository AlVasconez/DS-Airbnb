/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.*;
import java.util.ArrayList;
import java.util.Scanner;
import Util.ConexionDB;
import java.sql.Connection;

/**
 *
 * @author vv
 */
public class Sistema {
    
    public static ArrayList<Alojamiento> alojamientos = ConexionDB.crearListaAlojamiento();
    public static ArrayList<Usuario> usuarios = ConexionDB.crearListaUsuario();
    static Scanner sc = new Scanner(System.in);


//---------------------------------------------------------------------------------------------------
//  Metodo para recibir un numero por consola (PARA NO REPETIRLO A CADA RATO)
//  El parametro es el maximo de opciones que el usuario puede ingresar, fuera de ese
//  rango o si no es un numero entra en un bucle hasta recibir una opcion que cumpla
    public static int getOpcion(int maxValor){
        int opcion=0;
        boolean esNumero=true;
        
        try{
            System.out.print("Escoja una opcion: ");
            opcion = sc.nextInt();
        }
        catch(Exception e){
            esNumero=false;
            //System.out.println("****INGRESE UN NÚMERO*****");
        }
        finally{
            sc.nextLine();
            if(opcion>maxValor || esNumero==false){
                System.out.println("Ingrese una opción correcta.\n");
                opcion = getOpcion(maxValor);
            }
        }
        return opcion;
    }
           
//-----------------------------------------------------------------------------------------------------------
//  Metodos para obtener datos de ingreso (IdUsuario y contraseña) al iniciar sesion
//  un usuario y luego verificarlo. 
  
    private static Usuario getUsuario(int usuario, String contrasenia){
        for (Usuario u:usuarios){
            if((u.getUsuarioID()==(usuario)) &&(u.getContrasenia().equals(contrasenia))){
                return u;
            }
        }
        return null;
    }
    
    private static boolean verificacion(int usuario, String contrasenia){
        for (Usuario u:usuarios){
            if((u.getUsuarioID()==(usuario)) &&(u.getContrasenia().equals(contrasenia))){
                return true;
            }
        }
        return false;
        //return usuarios.contains(new Cliente(usuario,contrasenia));
    }
    
//  Retorna el objeto tipo Usuario que ingresó al sistema. El metodo entra en un
//  bucle hasta que pueda retornar un usuario que si este registrado.
    public static Usuario menuIngreso(){
        int usuarioId = 0;
        String contrasenia;
        Usuario usuario;
        
        System.out.print( """
                                   AIRBNB
                            Menu de inicio de sesion
                        """);
        
        try{
        System.out.print("IdUsuario: ");
        usuarioId = sc.nextInt();
        }
        catch(Exception e){}
        
        sc.nextLine();
        System.out.print("Contraseña: ");
        contrasenia = sc.nextLine();

        if(Sistema.verificacion(usuarioId, contrasenia)){
            usuario = getUsuario(usuarioId, contrasenia);
        }
        else{
            System.out.println("IdUsuario y/o contraseña erroneos.\n");
            usuario = menuIngreso();
        }
        
        return usuario;
    }
        
//-----------------------------------------------------------------------------------------------------
  
    public static int verAlojamientos(){    
        int opcionA;
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

        opcionA=getOpcion(alojamientos.size()+1);
        
        return opcionA;
    }
     
    public static void menuAlojaminetos(){  
        int opcion = verAlojamientos();
        
        switch (opcion){
            case 1 :
                
                break;
            case 2 :
                break;
            case 3 :
                break;
        }
    }
    
   
    
}