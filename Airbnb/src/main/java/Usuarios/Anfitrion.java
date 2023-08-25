/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Alojamiento;
import SistemaInterno.Sistema;
import Util.ConexionDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author vv
 */
public class Anfitrion extends Usuario {

    public Anfitrion(Integer usuarioID, String contrasenha, String nombre, String correo, String telefono, String direccionFisica, boolean verificacion) {
        super(usuarioID, contrasenha, nombre, correo, telefono,direccionFisica, verificacion);
    }
    
    @Override
    public String toString() {
        return "Anfitrion {" +super.toString() + '}';
    }
    
//-------------------------------------------------------------------------------------------------------------    

    @Override
    public void menuUsuario() {
        int opcion;
        do{
            System.out.print( """
                              MENU PRINCIPAL
                        1. Publicar alojamiento
                        2. Ver mis alojamientos
                        3. Editar mis alojamientos
                        4. Eliminar mi alojamiento
                        5. Eliminar una reserva
                        6. Cerrar Sesion
                    """);

            opcion = Sistema.getOpcion(6);

            switch(opcion){
                case 1:
                    publicarAlojamiento();
                    break;
                case 2:
                    Sistema.misAlojamientosA(this);
                    break;
                case 3:
                    ConexionDB.modificarAlojamiento(this);
                    break;
                case 4:
                    Sistema.eliminarAlojamiento(this);
                    break;
                case 5:
                    Sistema.eliminarAlojamiento(this);
                    break;
                case 6:
                    System.out.println("***SESION CERRADA CON EXITO***\n");
                    break;
                    
            }
        }
        while(opcion!=6);
    }
    


//---------------------------------------------------------------------------------------
    //Metodo de la opcion publicar alojamientos (ANFITRION)
    public void publicarAlojamiento(){
        Scanner sc = new Scanner(System.in);
        
        try{
        System.out.println("------ INGRESO DE DATOS DE ALOJAMIENTO ----\n");
        System.out.print("-Pais: ");
        String pais = sc.nextLine();
        
        System.out.print("-Ciudad: ");
        String ciudad = sc.nextLine();  
        
        System.out.print("-Costo: ");
        double costo = Double.parseDouble(sc.nextLine());
        //sc.nextLine();
        
        System.out.print("-Numero habitaciones: ");
        int habitaciones = sc.nextInt();
        sc.nextLine();
        
        String direccion = ciudad+"-"+pais;
        double tarifaAirbnb;
        
        do{
            tarifaAirbnb = (Math.random()*20)+10;
        }while(tarifaAirbnb>(costo/2));
        
        
        Alojamiento a = new Alojamiento(this,costo,habitaciones,direccion,tarifaAirbnb);
        a.addUnServicio(a.getAlojaminetoID());
        a.addUnReglamento(a.getAlojaminetoID());
        ConexionDB.registrarAlojamiento(a);
        
        System.out.println("\n-----------   SE HA PUBLICADO EXITOSAMENTE SU ALOJAMIENTO ---------\n");
        }
        catch(Exception e){
            System.out.println("********Ingreso de datos erroneo********");
        }
    }
    
}
