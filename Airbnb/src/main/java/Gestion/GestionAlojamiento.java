/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion;

import Persistencia.PersistenciaAlojamiento;
import Persistencia.PersistenciaReglas;
import Persistencia.PersistenciaServicios;
import SistemaInterno.Alojamiento;
import SistemaInterno.GestionarAlojamiento;
import Usuarios.Anfitrion;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class GestionAlojamiento implements GestionarAlojamiento {
    
    
    

    @Override
    public void publicar(Anfitrion anfitrion) {
        Scanner sc = new Scanner(System.in);
        
        try{
        System.out.println("------ INGRESO DE DATOS DE ALOJAMIENTO ----\n");
        System.out.print("-Pais: ");
        String pais = sc.nextLine();
        
        System.out.print("-Ciudad: ");
        String ciudad = sc.nextLine();
        
        System.out.print("-Nombre del Alojamiento: ");
        String nombreA = sc.nextLine();
        
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
        
        
        Alojamiento a = new Alojamiento(Alojamiento.alojamientoIDActual+1,anfitrion,costo,habitaciones,direccion,tarifaAirbnb, nombreA);
        addUnServicio(a.getAlojaminetoID());
        addUnReglamento(a.getAlojaminetoID());
        PersistenciaAlojamiento aloja = new PersistenciaAlojamiento();
        aloja.guardar(a);
        System.out.println("\n-----------   SE HA PUBLICADO EXITOSAMENTE SU ALOJAMIENTO ---------\n");
        }
        catch(Exception e){
            System.out.println("********Ingreso de datos erroneo********");
        }
    }
    
    @Override
    public void detallarElemento(Alojamiento alojamiento){
        System.out.printf("\n %s\n- Anfitrion: %s\n- Costo: %s x noche\n- Habitaciones: %d\n- Calificacion: %s estrellas\n- Ubicacion: %s\n",alojamiento.getNombre().toUpperCase(),alojamiento.getAnfitrion().getNombre(),alojamiento.getPrecio(),alojamiento.getHabitaciones(),alojamiento.getCalificacion(),alojamiento.getUbicacion());
        
        PersistenciaReglas reg = new PersistenciaReglas();
        reg.mostrar(alojamiento.getAlojaminetoID());
        PersistenciaServicios serv = new PersistenciaServicios();
        serv.mostrar(alojamiento.getAlojaminetoID());
    }
    
    
}
