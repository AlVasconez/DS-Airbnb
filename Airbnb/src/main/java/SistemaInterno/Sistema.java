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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author vv
 */
public class Sistema {
    
    public static ArrayList<Alojamiento> alojamientos = ConexionDB.crearListaAlojamiento();
    public static ArrayList<Usuario> usuarios = ConexionDB.crearListaUsuario();
    public static ArrayList<Reserva> reservas = new ArrayList<>();
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
    
    public static int diasEstancia(String fInicio, String fSalida){

        String[] fechaI = fInicio.split("-");
        String[] fechaS = fSalida.split("-");
        int anio = 365*(Integer.parseInt(fechaS[0])-Integer.parseInt(fechaI[0]));
        int mes = 30*(Integer.parseInt(fechaS[1])-Integer.parseInt(fechaI[1]));
        int dia = 1*(Integer.parseInt(fechaS[2])-Integer.parseInt(fechaI[2]));
        return anio+mes+dia;
    }
    
    public static Date txtaDate(String fecha){
            
            //Crear Variable tipo Date para asignarlo en la clase Revision

            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            
            Date fechaSolici = null;
            try {
            // instancia de util.Calendar
                Calendar cal = Calendar.getInstance();
            // Parsing date
              cal.setTime(formato.parse(fecha));
              fechaSolici = cal.getTime();
            }
            // Catch block to handle the exceptions
            catch (ParseException except) {

            // Excepcion usando printStackTrace() 
              except.printStackTrace();
            }
            
            return fechaSolici;
    }

//-------------------------------------------------------------------------------------------------
//------------------    SECCION DE INGRESO DE USUARIO    ------------------------------------------
//-------------------------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------------------------------        
//------------------    SECCION DEL MENU USUARIO (TIPO CLIENTE)   --------------------------------------
//------------------------------------------------------------------------------------------------------
    
//-----------metodos de la opcion ver alojamientos (Menu Cliente-opcion 1)------------------------------
    
    private static int verTodosLosAlojamientos(){    
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
    
    public static void opcionVerAlojamientos(Cliente cliente){  
        int opcion2;
        try{
            int opcion = verTodosLosAlojamientos();
            Alojamiento aloj = alojamientos.get(opcion-1);
            aloj.detallarAlojamientoSeleccionado();
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
                        realizarReserva(cliente,aloj);
                        break; 
                    case 2 :
                        mostrarReseniasAlojamiento(aloj);
                        break;
                    case 3 :
                        cliente.addListaFavoritos(aloj);
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
    
    
 //Reservar Alojamiento (Menu de Alolamientos-case 1)---------------------------------------------------------
    //  Aun falta terminar
    private static void realizarReserva(Cliente cliente, Alojamiento aloj){
        Random r = new Random(); 
        int numero1 = r.nextInt((3 - 1) + 1) + 1;
        int numero2 = r.nextInt((3 - 1) + 1) + 1;
        System.out.println(calendarioAgosto(numero1));
        System.out.println(calendarioSeptiembre(numero2));
        System.out.print("Indique desde que fecha desea reservar EX:(MES-DIA -> 08-23): ");
        String fInicio = "2023-"+sc.nextLine();
        System.out.print("Indique hasta que fecha desea reservar EX:(MES-DIA -> 08-25): ");
        String fFinal = "2023-"+sc.nextLine();
        
        //cambiar tarifaAirbnb de reserva a alojamiento
        //cambiar clienteId y alojamientoId en atributos reserva por sus clases 
        Reserva reserva = new Reserva(cliente.getUsuarioID(),aloj.getAlojaminetoID(),fInicio,fFinal);
        //metodo para escribirlo en la BD
        ConexionDB.registrarReserva(reserva);
        
        System.out.println("******  SE HA REGISTRADO SU RESERVA *******");
    }
    
    private static String calendarioAgosto(int numero){

        if(numero==1){
            return        ("""
                           |---------------------------------------|
                           |              agosto 2023              |
                           |---------------------------------------|
                           | 1 | 2 | x | x | x | 6 | 7 | 8 | 9 | 10|
                           |---------------------------------------|
                           | 11| 12| 13| 14| x | x | x | x | x | 20|
                           |---------------------------------------|
                           | 21| 22| 23| 24| 25| 26| 27| 28| 29| 30|
                           |---------------------------------------|
                           """);
        }
        if(numero==2){
        return            ("""
                           |---------------------------------------|
                           |              agosto 2023              |
                           |---------------------------------------|
                           | 1 | x | 3 | 4 | 5 | x | 7 | 8 | x | 10|
                           |---------------------------------------|
                           | 11| 12| x | x | x | 16| 17| x | 19| 20|
                           |---------------------------------------|
                           | 21| 22| 23| 24| 25| x | x | x | 29| 30|
                           |---------------------------------------|
                           """);
        }
        if(numero==3){
        return            ("""
                           |---------------------------------------|
                           |              agosto 2023              |
                           |---------------------------------------|
                           | x | x | x | x | x | x | 7 | 8 | 9 | 10|
                           |---------------------------------------|
                           | 11| 12| 13| x | 15| 16| x | x | 19| 20|
                           |---------------------------------------|
                           | 21| 22| 23| x | x | 26| 27| 28| 29| 30|
                           |---------------------------------------|
                           """);
        }
        return"";
    }

    private static String calendarioSeptiembre(int numero){

        if(numero==1){
            return        ("""
                           |---------------------------------------|
                           |            septiembre 2023            |
                           |---------------------------------------|
                           | 1 | 2 | x | 4 | x | x | 7 | 8 | 9 | 10|
                           |---------------------------------------|
                           | 11| 12| x | 14| 15| x | x | 18| 19| 20|
                           |---------------------------------------|
                           | 21| 22| 23| 24| 25| 26| x | x | x | x |
                           |---------------------------------------|
                           """);
        }
        if(numero==2){
        return            ("""
                           |---------------------------------------|
                           |            septiembre 2023            |
                           |---------------------------------------|
                           | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10|
                           |---------------------------------------|
                           | 11| 12| x | x | 15| 16| 17| 18| 19| 20|
                           |---------------------------------------|
                           | 21| 22| 23| 24| 25| x | 27| 28| 29| 30|
                           |---------------------------------------|
                           """);
        }
        if(numero==3){
        return            ("""
                           |---------------------------------------|
                           |            septiembre 2023            |
                           |---------------------------------------|
                           | 1 | 2 | 3 | x | x | x | x | 8 | 9 | 10|
                           |---------------------------------------|
                           | 11| 12| 13| x | 15| x | 17| 18| 19| 20|
                           |---------------------------------------|
                           | 21| 22| 23| 24| 25| 26| 27| 28| 29| 30|
                           |---------------------------------------|
                           """);
        }
        return"";
    }
    
    
 //Ver reseñas del alojamiento (Menu de Alolamientos-case2)---------------------------------------------------
    private static void mostrarReseniasAlojamiento(Alojamiento aloj){
        if(!aloj.getResenhas().isEmpty()){
            System.out.println("--------- RESEÑAS ----------------\n");
            for(Resenha r:aloj.getResenhas()){
                r.toString();
            }
        }
        else{
            System.out.println("El alojamiento no posee ninguna reseña realizada");
        }
    }
    
 //Agregar a favoritos (Menu de Alolamientos-case3)----------------------------------------------------------
  //    Esta opcion es un metodo de la clase CLiente   
    
 //Realizar reseña (Menu de Alolamientos-case4)----------------------------------------------------------------
    private static void realizarResenha(Cliente cliente, Alojamiento aloj){
        if(reservacionPrevia(cliente, aloj)){
            cliente.crearReseña(aloj);
        }
        else{
            System.out.println("No ha reservado este alojamiento. No puede realizar reseña");
        }
    }
    
    private static boolean reservacionPrevia(Cliente cliente, Alojamiento aloj){
        for(Reserva r:reservas){
            if (r.getClienteID()==(cliente.getUsuarioID())&& r.getAlojaminetoID()==(aloj.getAlojaminetoID())){
                return true;
            }
        }
        return false;
    }
    
    
//----------------metodos de la opcion ver mis alojamientos favoritos (Menu Cliente-opcion 3)-----------------------------
    public static void mostrarAlojamientosFavoritos(Cliente cliente){
        if(!cliente.getFavoritos().isEmpty())
            for(Alojamiento aloj:cliente.getFavoritos()){
                aloj.detallarAlojamientoSeleccionado();
            }
        else{
            System.out.println("No ha agregado ningun alojamiento aun\n");
        }
    }
    
//--------------------------------------------------------------------------------------------------------    
//------------------    SECCION DEL MENU USUARIO (TIPO ANFITRION)    -------------------------------------- 
//---------------------------------------------------------------------------------------------------------  
    
//-------Metodos de Ver mis Alojamientos (Menu Anfitrion-Opcion 2)------------------------------------------
    
    private static Alojamiento elegirUnAlojamiento(Anfitrion anfitrion){
        int contador=0;
        Alojamiento aloj =null; 
        ArrayList<Alojamiento> misAlojamientos=new ArrayList<>();
        
        for(Alojamiento a: alojamientos){
            if(a.getAnfitrion().equals(anfitrion)){
                contador++;
                System.out.print(contador+")  ");
                a.enlistarAlojamiento();
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
    
    private static void reseñasPublicadas(Alojamiento alojamiento){
            
        if (!alojamiento.getResenhas().isEmpty()){
            for(Resenha r:alojamiento.getResenhas()){
                System.out.println(r.toString());
            }
        }
        else{
            System.out.println("***Este alojamiento aun no ha recibido reseñas****\n");
        }
    }
    
    private static void reservasRealizadas(Alojamiento alojamiento){
        ArrayList<Reserva> reservasAloj = ConexionDB.reservasPorAlojamiento(alojamiento.getAlojaminetoID());
            
        if (!reservasAloj.isEmpty()){
            for(Reserva r:reservasAloj){
                System.out.println(r.toString());
            }
        }
        else{
            System.out.println("***Este alojamiento aun no posee reservas realizadas****\n");
        }
    }
    
   
    public static void misAlojamientosA(Anfitrion anfitrion){
        Alojamiento aloj = elegirUnAlojamiento(anfitrion);
        if (aloj!=null){
            System.out.println("""
                               1. Reseñas publicadas
                               2. Reservas realizadas
                               3. Eliminar reserva
                               4. Volver
                               """);

            int opcionA = getOpcion(4);

            switch (opcionA){
                case 1:
                    reseñasPublicadas(aloj);
                    break;
                case 2:
                    reservasRealizadas(aloj);
                    break;
                case 3:
                    reservasRealizadas(aloj);
                    System.out.println("Ingrese Ide de reserva a eliminar...");
                    Scanner scr=new Scanner(System.in);
                    int idReserva= scr.nextInt();
                    for (Reserva reserva : reservas) {
                        if (reserva.getReservaID() == idReserva) {
                            anfitrion.eliminarReserva(reserva);  
                    }
                    
                    }
                    break;
                
                 
                    
            }
        }
    }
    
}
