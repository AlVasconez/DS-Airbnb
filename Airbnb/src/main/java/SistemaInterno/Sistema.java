/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Gestion.GestionAlojamiento;
import Gestion.GestionPagoAlojamiento;
import Persistencia.PersistenciaAlojamiento;
import Persistencia.PersistenciaFavoritos;
import Persistencia.PersistenciaResenias;
import Persistencia.PersistenciaReserva;
import Persistencia.PersistenciaUsuarios;
import Usuarios.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author vv
 */
public class Sistema {
    static Scanner sc = new Scanner(System.in);

//---------------------------------------------------------------------------------------------------
//  Metodo usado para recibir un numero por consola (PARA NO REPETIRLO A CADA RATO)
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
            if(opcion>maxValor || esNumero==false || opcion<=0){
                System.out.println("Ingrese una opción correcta.\n");
                opcion = getOpcion(maxValor);
            }
        }
        return opcion;
    }
   
//-------------------------------------------------------------------------------------------------
//------------------    SECCION DE INGRESO DE USUARIO    ------------------------------------------
//-------------------------------------------------------------------------------------------------
//  Metodos para obtener datos de ingreso (IdUsuario y contraseña) al iniciar sesion
//  un usuario y luego verificarlo. 
  
    private static Usuario getUsuario(int usuario, String contrasenia){
        PersistenciaUsuarios pUsuarios = new PersistenciaUsuarios();
        for (Usuario u:pUsuarios.listarUsuarios()){
            if((u.getUsuarioID()==(usuario)) &&(u.getContrasenia().equals(contrasenia))){
                return u;
            }
        }
        return null;
    }
    
    private static boolean verificacion(int usuario, String contrasenia){
        PersistenciaUsuarios pUsuarios = new PersistenciaUsuarios();
        for (Usuario u:pUsuarios.listarUsuarios()){
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
//------------------------------------------------------------------------------------------------------        
//------------------    SECCION DEL MENU USUARIO (TIPO CLIENTE)   --------------------------------------
//------------------------------------------------------------------------------------------------------
    
//-----------metodos de la opcion ver alojamientos (Menu Cliente-opcion 1)------------------------------
    
   
    public static void opcionVerAlojamientos(Cliente cliente){  
        int opcion2;
        try{
            PersistenciaAlojamiento pAloj = new PersistenciaAlojamiento();
            int opcion = pAloj.mostrarTodo();
            Alojamiento aloj = pAloj.listarAlojamientos().get(opcion-1);
            GestionAlojamiento gAloj = new GestionAlojamiento();
            gAloj.detallarElemento(aloj);
            do{
            //  MENU OPCIONES DE ALOJAMIENTO
            System.out.println("""
                               \n¿Que desea realizar?
                               1. Reservar alojamiento
                               2. Ver reseñas del alojamiento
                               3. Agregar a Favoritos
                               4. Realizar Reseña (Disponible solo si ha realizado una reserva previa a este alojamiento)
                               5. Volver   
                               """);
            opcion2 = getOpcion(5);
            
                switch (opcion2){
                    case 1 :
                        GestionPagoAlojamiento gpAlog = new GestionPagoAlojamiento();
                        gpAlog.realizarReserva(cliente,aloj);
                        break; 
                    case 2 :
                        PersistenciaReserva pRes = new PersistenciaReserva();
                        pRes.listarReservasPorAlojamiento(aloj.getAlojaminetoID());
                        break;
                    case 3 :
                        PersistenciaFavoritos pfav = new PersistenciaFavoritos();
                        pfav.guardar(cliente.getUsuarioID(), aloj.getAlojaminetoID());
                        break;
                    case 4 :
                        realizarResenha(cliente, aloj);
                        break;
                }
            }
            while(opcion2<5 && opcion>1);
        }
        catch(Exception e){}
    }
    
 
   
 //Realizar reseña (Menu de Alolamientos-case4)----------------------------------------------------------------
    private static void realizarResenha(Cliente cliente, Alojamiento aloj){
        boolean puede = false;
        PersistenciaReserva pRes = new PersistenciaReserva();
        for(Reserva r:pRes.listarReservasPorUsuario(cliente.getUsuarioID())){
            if(r.getAlojaminetoID()==(aloj.getAlojaminetoID())){
                puede=true;
            }
        }
        if (puede){
            cliente.crearReseña(aloj);
        }           
        else{
            System.out.println("No ha reservado este alojamiento. No puede realizar reseña");
        }
    }

//--------------------------------------------------------------------------------------------------------    
//------------------    SECCION DEL MENU USUARIO (TIPO ANFITRION)    -------------------------------------- 
//---------------------------------------------------------------------------------------------------------  
    
//-------Metodos de Ver mis Alojamientos (Menu Anfitrion-Opcion 2)------------------------------------------
    
    public static Alojamiento elegirUnAlojamiento(Anfitrion anfitrion){
        int contador=0;
        Alojamiento aloj =null; 
        ArrayList<Alojamiento> misAlojamientos=new ArrayList<>();
        PersistenciaAlojamiento pAloj = new PersistenciaAlojamiento();
        
        for(Alojamiento a: pAloj.listarAlojamientos()){
            if(a.getAnfitrion().equals(anfitrion)){
                contador++;
                System.out.print(contador+")  ");
                GestionAlojamiento gAloj = new GestionAlojamiento();
                gAloj.detallarElemento(a);
                misAlojamientos.add(a);
            }
        } 
        if(contador>0){
            System.out.print("Indique cual de sus alojamientos desea ver");
            int alojSeleccionado = getOpcion(contador);
            aloj = misAlojamientos.get(alojSeleccionado-1);
        }
        return aloj;
    }

   
    public static void misAlojamientosA(Anfitrion anfitrion){
        Alojamiento aloj = elegirUnAlojamiento(anfitrion);
        if (aloj!=null){
            System.out.println("""
                               1. Reseñas publicadas
                               2. Reservas realizadas
                               3. Volver
                               """);

            int opcionA = getOpcion(3);

            switch (opcionA){
                case 1:
                    PersistenciaResenias pResenias = new PersistenciaResenias();
                    pResenias.mostrar(aloj);
                    break;
                case 2:
                    PersistenciaReserva pReservas = new PersistenciaReserva();
                    pReservas.mostrar(aloj.getAlojaminetoID());
                    break;
            }
        }
    }
    
    public static void eliminarAlojamiento(Anfitrion anfitrion){
        Alojamiento aloj = elegirUnAlojamiento(anfitrion);
        if (aloj!=null){
            System.out.println("Esta seguro de eliminarlo permanentemente");
            System.out.println("1:Si");
            System.out.println("2:No");
            int opcionE = getOpcion(2);
            if(opcionE==1){
                PersistenciaAlojamiento pAloj = new PersistenciaAlojamiento();
                pAloj.delete(aloj); 
            }
        }
    }

    
}