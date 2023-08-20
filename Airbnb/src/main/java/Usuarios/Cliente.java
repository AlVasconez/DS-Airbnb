/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Resenha;
import SistemaInterno.Alojamiento;
import SistemaInterno.Reserva;
import SistemaInterno.Sistema;
import static SistemaInterno.Sistema.reservas;
import Util.ConexionDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class Cliente extends Usuario{
    
    private static ArrayList<Alojamiento> listaFavoritos = new ArrayList<>();

    public Cliente(int usuarioID, String contrasenha, String nombre, String correo, String telefono,String direccionFisica, boolean verificacion) {
        super(usuarioID, contrasenha, nombre, correo, telefono,direccionFisica, verificacion);
    }
    
    public ArrayList<Alojamiento> getFavoritos(){
        return listaFavoritos;
    }
    
    @Override
    public String toString() {
        return "Cliente {" +super.toString() + '}';
    }

    
    public void crearReseña(Alojamiento alojamiento){
        //Asignando calificaion a alojamiento
        Scanner sc = new Scanner(System.in);
        System.out.println("Calificacion para este alojamiento(Ex:4.3):");
        double calificacion = sc.nextInt();
        sc.nextLine();
        alojamiento.cambiarCalificaion(calificacion);
        
        //Creando una resenha
        System.out.println("Comentario sobre su estancia:(Dar enter si no desea comentar)");
        String resenha = sc.nextLine();
        Resenha r = new Resenha(resenha,alojamiento,calificacion,this);
        alojamiento.addUnaResenha(r);
        System.out.println("Se ha actualizado la calificacion de este alojamiento.");
    }

    public void addListaFavoritos(Alojamiento alojamiento){
        listaFavoritos.add(alojamiento);
        System.out.println("Agregado a favoritos");
    }

    
    @Override
    public void menuUsuario() {
        Connection c = ConexionDB.getConection();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do{
            System.out.print( """

                      MENU PRINCIPAL
                1. Ver alojaminetos
                2. Mis reservas
                3. Ver mis alojamientos favoritos
                4. Eliminar reservas
                5. Cerrar Sesion
                    """);

            opcion = Sistema.getOpcion(4);

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
                    Sistema.mostrarAlojamientosFavoritos(this);
                    System.out.println("Ingrese Ide de reserva a eliminar...");
                    Scanner scr=new Scanner(System.in);
                    int idReserva= scr.nextInt();
                    for (Reserva reserva : reservas) {
                        if (reserva.getReservaID() == idReserva) {
                            this.eliminarReserva(reserva);  
                    }
                    
                    }
                    break;
                case 5:
                    System.out.println("***SESION CERRADA CON EXITO***\n");
                    break;
            }
        }
        while (opcion!=5);
    }
    @Override
public void eliminarReserva(Reserva reserva) {
    
    for (int i = 0; i < Sistema.reservas.size(); i++) {
        Reserva r = Sistema.reservas.get(i);
        if (r.getClienteID() == this.usuarioID && r.getReservaID() == reserva.getReservaID()) {
            Sistema.reservas.remove(i);
            System.out.println("Reserva eliminada con éxito.");
            ConexionDB.eliminarReserva(reserva.getReservaID());
            
            return; 
        }
    }
    
    
    System.out.println("No se encontró la reserva para eliminar.");
} 
    
}
