/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SistemaInterno;

import Persistencia.PersistenciaReglas;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public interface AddReglamento {
    default void addUnReglamento(int id){
        Scanner sc = new Scanner(System.in);
        int opcion=0;

        try{
            System.out.println("Regla a agregar: ");
            String regla = sc.nextLine();
            if (!regla.isEmpty() && !regla.isBlank()){
                PersistenciaReglas pr = new PersistenciaReglas();
                pr.guardar(id,regla);
            }
            System.out.println("Desea agregar otra regla?\n1.si\n2.no");
            opcion =Sistema.getOpcion(2);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            if(opcion==1){
                this.addUnReglamento(id);
            }
        }
    }
}
