/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Resenha;
import SistemaInterno.Alojamiento;
import SistemaInterno.Reserva;
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
       
    @Override
    public String toString() {
        return "Cliente {" +super.toString() + '}';
    }

    
    public void crearReseña(Alojamiento alojamiento){
        //Asignando calificaion a alojamiento
        try{
        Scanner sc = new Scanner(System.in);
        System.out.println("Calificacion para este alojamiento(Ex:4.3):");
        Double calificacion = sc.nextDouble();
        sc.nextLine();
        alojamiento.cambiarCalificaion(calificacion);
        
        //Creando una resenha
        System.out.println("Comentario sobre su estancia:(Dar enter si no desea comentar)");
        String resenha = sc.nextLine();
        ConexionDB.registrarResenha(this.usuarioID, alojamiento.getAlojaminetoID(), resenha, calificacion);
        System.out.println("Se ha actualizado la calificacion de este alojamiento.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("****ERROR AL INGRESAR VALORES. INTENTE DE NUEVO****");
        }
    }

    public void addListaFavoritos(Alojamiento alojamiento){
        ConexionDB.registrarFavorito(alojamiento.getAlojaminetoID(),this.usuarioID);
    }

    
    @Override
    public void menuUsuario() {
        int opcion;
        do{
            System.out.print( """

                      MENU PRINCIPAL
                1. Ver alojaminetos
                2. Mis reservas
                3. Ver mis alojamientos favoritos
                4. Ver mis reseñas realizadas
                5. Cerrar Sesion
                    """);

            opcion = Sistema.getOpcion(5);

            switch (opcion){
                case 1:
                    Sistema.opcionVerAlojamientos(this);
                    break;
                case 2:
                    for(Reserva r:ConexionDB.reservasPorCliente(this.usuarioID)){
                        System.out.println(r.toString());
                }
                    break;
                case 3:
                    Sistema.mostrarAlojamientosFavoritos(this);
                    break;
                case 4:
                    ConexionDB.resenhas(this);
                    break;
                case 5:
                    System.out.println("***SESION CERRADA CON EXITO***\n");
                    break;
            }
        }
        while (opcion!=5);
    }
    public void enviarMensaje(Anfitrion anfitrion){
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriba el mensaje que le quiera mandar a "+anfitrion.getNombre());
        String mensaje = sc.nextLine();
        if(!mensaje.isEmpty() && !mensaje.isBlank()){
            ConexionDB.registrarMensaje(mensaje, anfitrion.usuarioID, this.usuarioID);
            System.out.println("***Mensaje Enviado***\n");
        }
    }
    
}
