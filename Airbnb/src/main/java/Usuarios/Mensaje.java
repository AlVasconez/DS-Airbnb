/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import Util.ConexionDB;
import java.util.Scanner;


/**
 *
 * @author vv
 */
public class Mensaje {
    protected String contenido;
    protected Anfitrion anfitrion;
    protected Cliente cliente;
    protected Usuario usuario;
    

    public Mensaje(String contenido, Anfitrion anfitrion, Cliente cliente) {
        this.contenido = contenido;
        this.anfitrion = anfitrion;
        this.cliente = cliente;
    }
   
    // principio rensponsabilidad unica cliente mensaje
    public void enviarMensaje(Anfitrion anfitrion){
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriba el mensaje que le quiera mandar a "+anfitrion.getNombre());
        String mensaje = sc.nextLine();
        if(!mensaje.isEmpty() && !mensaje.isBlank()){
            ConexionDB.registrarMensaje(mensaje, anfitrion.usuarioID, usuario.getUsuarioID());
            System.out.println("***Mensaje Enviado***\n");
        }
    }
    
    // PRU anfitrion mensaje
    
    public void enviarMensaje(Cliente cliente){
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriba el mensaje que le quiera mandar a "+cliente.getNombre());
        String mensaje = sc.nextLine();
        if(!mensaje.isEmpty() && !mensaje.isBlank()){
            ConexionDB.registrarMensaje(mensaje, anfitrion.getUsuarioID(), cliente.usuarioID);
            System.out.println("***Mensaje Enviado***\n");
        }
    }
    
    
    
    
}
