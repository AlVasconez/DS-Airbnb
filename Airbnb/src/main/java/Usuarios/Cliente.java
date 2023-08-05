/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Resenha;
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
public class Cliente extends Usuario{

    public Cliente(int usuarioID, String contrasenha, String nombre, String correo, String telefono,String direccionFisica, boolean verificacion) {
        super(usuarioID, contrasenha, nombre, correo, telefono,direccionFisica, verificacion);
    }

    public Cliente(int usuarioID, String contrasenha){
        super(usuarioID, contrasenha);
    }
    public void crearReseña(Alojamiento alojamiento){
        //Asignando calificaion a alojamiento
        Scanner sc = new Scanner(System.in);
        System.out.println("Calificacion para este alojamiento(Ex:4.3):");
        double calificacion = sc.nextInt();
        sc.nextLine();
        alojamiento.cambiarCalificaion(calificacion);
        
        //Creando una resenha
        System.out.println("Comentario sobre su estancia:");
        String resenha = sc.nextLine();
        Resenha r = new Resenha(resenha,alojamiento,calificacion,this);
        
        System.out.println("Se ha actualizado la calificacion de este alojamiento.");
    }

    


    @Override
    public String toString() {
        return "Cliente {" +super.toString() + '}';
    }
    
    
    
    @Override
    public int menuUsuario() {
        Connection c = ConexionDB.getConection();
        Scanner sc = new Scanner(System.in);
        
        System.out.print( """

                  MENU PRINCIPAL
            1. Ver alojaminetos
            2. Mis reservas
            3. Cerrar Sesion
                """);
         
        int opcion = Sistema.getOpcion(3);

        if (opcion==2){
            System.out.print( """
               1. Fechas realizadas
               2. Fechas por reservar
            """);

            System.out.println("Escoja una opcion: ");
            int opcion2 = sc.nextInt();

            if(opcion2==1){
                ConexionDB.mostrarReservasInfo(c, this.getUsuarioID());
            }
        }


        return opcion;
        
    }
    
    
}
