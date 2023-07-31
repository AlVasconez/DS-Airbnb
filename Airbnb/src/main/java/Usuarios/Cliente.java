/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Resenha;
import SistemaInterno.Alojamiento;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class Cliente extends Usuario{

    public Cliente(String usuarioID, String contrasenha, String nombre, String apellido, String correo, int telefono, boolean verificacion) {
        super(usuarioID, contrasenha, nombre, apellido, correo, telefono, verificacion);
    }

    public Cliente(String usuarioID, String contrasenha){
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
    
        
        
    
}
