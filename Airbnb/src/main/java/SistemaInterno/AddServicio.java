/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SistemaInterno;

import Persistencia.PersistenciaServicios;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public interface AddServicio {
    
    default void addUnServicio(int id){
        Scanner sc = new Scanner(System.in);
        int opcion=0;
        try{
            System.out.println("Servicio que posee su alojamiento: ");
            String servicio =sc.nextLine();
            if (!servicio.isEmpty() && !servicio.isBlank()){
                PersistenciaServicios serv = new PersistenciaServicios();
                serv.guardar(id,servicio);
            }
            System.out.println("Desea agregar otro servicio?\n1.si\n2.no");
            opcion =Sistema.getOpcion(2);
        }
        catch(Exception e){
            System.out.println("");
        }
        finally{
            if(opcion==1){
                this.addUnServicio(id);
            }
        }
    }
}
