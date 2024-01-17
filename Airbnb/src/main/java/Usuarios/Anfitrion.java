/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Alojamiento;
import SistemaInterno.Sistema;
import Util.ConexionDB;
import Gestion.GestionAlojamiento;
import Persistencia.PersistenciaAlojamiento;


/**
 *
 * @author vv
 */
public class Anfitrion  extends Usuario {

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
                        5. Cerrar Sesion
                    """);

            opcion = Sistema.getOpcion(5);

            switch(opcion){
                case 1:
                    GestionAlojamiento gAloj = new GestionAlojamiento();
                    gAloj.publicar(this);
                    break;
                case 2:
                    Sistema.misAlojamientosA(this);
                    break;
                case 3:
                    PersistenciaAlojamiento pAloj = new PersistenciaAlojamiento();
                    pAloj.modificar(this);
                    break;
                case 4:
                    Sistema.eliminarAlojamiento(this);
                    break;
                case 5:
                    System.out.println("***SESION CERRADA CON EXITO***\n");
                    break;
                    
            }
        }
        while(opcion!=5);
    }

}
