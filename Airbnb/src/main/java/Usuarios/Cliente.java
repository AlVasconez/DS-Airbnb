/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import Persistencia.PersistenciaAlojamiento;
import Persistencia.PersistenciaFavoritos;
import Persistencia.PersistenciaResenias;
import Persistencia.PersistenciaReserva;
import SistemaInterno.Alojamiento;
import SistemaInterno.Sistema;
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

        //Creando una resenha
        System.out.println("Comentario sobre su estancia:(Dar enter si no desea comentar)");
        String resenha = sc.nextLine();
        PersistenciaResenias pRes = new PersistenciaResenias();
        pRes.guardar(this.usuarioID, alojamiento.getAlojaminetoID(), resenha, calificacion);
        System.out.println("Se ha actualizado la calificacion de este alojamiento.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("****ERROR AL INGRESAR VALORES. INTENTE DE NUEVO****");
        }
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
                    PersistenciaReserva pReservas = new PersistenciaReserva();
                    pReservas.mostrar(this.usuarioID);
                    break;
                case 3:
                    PersistenciaFavoritos pAloj = new PersistenciaFavoritos();
                    pAloj.mostrar(this.usuarioID);
                    break;
                case 4:
                    PersistenciaResenias pRes = new PersistenciaResenias();
                    pRes.mostrar(this);
                    break;
                case 5:
                    System.out.println("***SESION CERRADA CON EXITO***\n");
                    break;
            }
        }
        while (opcion!=5);
    }
    
    
}
