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
    public int menuUsuario() {
        Connection c = ConexionDB.getConection();
        
        System.out.print( """
                          MENU PRINCIPAL
                    1. Publicar alojamiento
                    2. Ver alojamientos
                    3. Cerrar Sesion
                """);
         
        int opcion = Sistema.getOpcion(3);
        
        if (opcion==1){
            this.publicarAlojamiento();
        }
        return opcion;
    }
    
     @Override
    public String toString() {
        return "Anfitrion {" +super.toString() + '}';
    }

    public String getAnfitrion() {
        return "id: "+usuarioID+" nombre: "+nombre;
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
        double costo = sc.nextDouble();
        sc.nextLine();
        
        System.out.print("-Numero habitaciones: ");
        int habitaciones = sc.nextInt();
        sc.nextLine();
        
        String direccion = ciudad+"-"+pais;
        
        Alojamiento a = new Alojamiento(this,costo,habitaciones,direccion);
        
        a.addServicio();
        a.addReglamento();
        Sistema.alojamientos.add(a);
        System.out.println("\n-----------   SE HA PUBLICADO EXITOSAMENTE SU ALOJAMIENTO ---------\n");
        }
        catch(Exception e){
            System.out.println("********Ingreso de datos erroneo********");
        }
    }
    
}
