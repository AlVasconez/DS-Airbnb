/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import MetodosPago.*;
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
        if((anio+mes+dia)==0){
            return 1;
        }
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
        for (Usuario u:ConexionDB.usuarios()){
            if((u.getUsuarioID()==(usuario)) &&(u.getContrasenia().equals(contrasenia))){
                return u;
            }
        }
        return null;
    }
    
    private static boolean verificacion(int usuario, String contrasenia){
        for (Usuario u:ConexionDB.usuarios()){
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
        
        for(Alojamiento a:ConexionDB.alojamientos()){
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

        opcionA=getOpcion(ConexionDB.alojamientos().size()+1);
        
        return opcionA;
    }
    
    public static void opcionVerAlojamientos(Cliente cliente){  
        int opcion2;
        try{
            int opcion = ConexionDB.mostrarAlojamiento();
            Alojamiento aloj = ConexionDB.alojamientos().get(opcion-1);
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

    private static void realizarReserva(Cliente cliente, Alojamiento aloj){
        
        System.out.print("Indique desde que fecha desea reservar EX:(MES-DIA -> 08-23): ");
        String fInicio = "2023-"+sc.nextLine();
        System.out.print("Indique hasta que fecha desea reservar EX:(MES-DIA -> 08-25): ");
        String fFinal = "2023-"+sc.nextLine();
        //DESPUES HAY QUE HACER QUE EL MONTO SE TRAIGA DESDE LA BD (ESTE NO SE QUEDA SOLO ES PARA TENER UNA IDEA)
        double montoT = ((diasEstancia(fInicio,fFinal)*aloj.getPrecio())+aloj.getTarifaAirbnb());
        Reserva r = new Reserva(cliente.getUsuarioID(),aloj.getAlojaminetoID(),fInicio,fFinal);
        System.out.println("|---------------------------------|");
        System.out.println("|          RESUMEN RESERVA        |");
        System.out.println("| Alojamiento: "+aloj.getNombre()+"");
        System.out.println("| Reservado desde: "+fInicio+" ");
        System.out.println("| Reservado hasta: "+fFinal+" ");
        System.out.println("| Monto total: "+montoT+"     ");
        System.out.println("|---------------------------------|");
        pagarReserva(r);
        
        
        
        
    }
    
//-------------------   METODOS DE PAGO -------------------------------------------------------

    public static void pagarReserva(Reserva r){
        System.out.println("""
                           Escoja de que forma desea pagar:
                           1. Tarjeta
                           2. Paypal
                           3. Google Pay
                           4. Salir
                           """);
        int opcion = getOpcion(4);
        switch(opcion){
            case 1:
                pagarConTarjeta(r);
                break;
            case 2:
                pagarConPaypal(r);
                break;
            case 3:
                pagarConGooglePay(r);
                break;
            case 4:
                break;
        }
        
    }
    
    private static void pagarConTarjeta(Reserva r){
        System.out.print("Ingrese numero de tarjeta: ");
        int numTarjeta = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese fecha de caducidad EX:(mes-año => 2025-05): ");
        String caducidad = sc.nextLine();
        System.out.print("Ingrese codigo CVV: ");
        int cvv = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese su codigo postal: ");
        int cPostal = sc.nextInt();
        sc.nextLine();

        Tarjeta t = new Tarjeta(numTarjeta,caducidad,cPostal,cvv);
        System.out.print("""
                         1. Confirmar Pago
                         2. Cancelar
                         """);
        int opcion = getOpcion(2);
        if (opcion==1){
            ConexionDB.registrarPagoTarjeta(r, t);
        }
    }
    
    private static void pagarConPaypal(Reserva r){
        System.out.print("Ingrese numero de cuenta: ");
        int numCuentaP = sc.nextInt();
        sc.nextLine();
        Paypal p = new Paypal(numCuentaP);
        System.out.print("""
                         1. Confirmar Pago
                         2. Cancelar
                         """);
        int opcion = getOpcion(2);
        if (opcion==1){
            ConexionDB.registrarPagoPaypal(r, p);
        }
    }
    
    private static void pagarConGooglePay(Reserva r){
        System.out.print("Ingrese numero de cuenta: ");
        int numCuentaGP = sc.nextInt();
        sc.nextLine();
        GooglePay gp = new GooglePay(numCuentaGP);
        System.out.print("""
                         1. Confirmar Pago
                         2. Cancelar
                         """);
        int opcion = getOpcion(2);
        if (opcion==1){
            ConexionDB.registrarPagoGPay(r, gp);
        }
    }
    

 //Ver reseñas del alojamiento (Menu de Alolamientos-case2)---------------------------------------------------
    private static void mostrarReseniasAlojamiento(Alojamiento aloj){
        if(!ConexionDB.resenhas(aloj).isEmpty()){
            System.out.println("\n--------- RESEÑAS DE ESTE ALOJAMIENTO ----------\n");
            for(Resenha r:ConexionDB.resenhas(aloj)){
                r.toString();
            }
        }
        else{
            System.out.println("El alojamiento no posee ninguna reseña realizada");
        }
    }

    
 //Realizar reseña (Menu de Alolamientos-case4)----------------------------------------------------------------
    private static void realizarResenha(Cliente cliente, Alojamiento aloj){
        boolean puede = false;
        for(Reserva r:ConexionDB.reservasPorCliente(cliente.getUsuarioID())){
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
    
    
    
//----------------metodos de la opcion ver mis alojamientos favoritos (Menu Cliente-opcion 3)-----------------------------
    public static void mostrarAlojamientosFavoritos(Cliente cliente){
        ConexionDB.mostrarFavoritos(cliente);
    }
    
//--------------------------------------------------------------------------------------------------------    
//------------------    SECCION DEL MENU USUARIO (TIPO ANFITRION)    -------------------------------------- 
//---------------------------------------------------------------------------------------------------------  
    
//-------Metodos de Ver mis Alojamientos (Menu Anfitrion-Opcion 2)------------------------------------------
    
    public static Alojamiento elegirUnAlojamiento(Anfitrion anfitrion){
        int contador=0;
        Alojamiento aloj =null; 
        ArrayList<Alojamiento> misAlojamientos=new ArrayList<>();
        
        for(Alojamiento a: ConexionDB.alojamientos()){
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
        if (!ConexionDB.resenhas(alojamiento).isEmpty()){
            for(Resenha r:ConexionDB.resenhas(alojamiento)){
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
                               3. Volver
                               """);

            int opcionA = getOpcion(3);

            switch (opcionA){
                case 1:
                    reseñasPublicadas(aloj);
                    break;
                case 2:
                    reservasRealizadas(aloj);
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
                ConexionDB.deleteAlojamiento(aloj); 
            }
        }
    }
    
//---------------------------------------------------------------------------------------------

    public static void verMisReservasCliente(Cliente c) {
        ConexionDB.verMisReservasCliente(c);
    }

    
}