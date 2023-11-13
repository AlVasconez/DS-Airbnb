/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MetodosPago;

import Persistencia.PersistenciaPagos;
import SistemaInterno.Reserva;
import static SistemaInterno.Sistema.getOpcion;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public interface PagarConTarjeta {
    
    default void pagarConTarjeta(Reserva reserva){
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese numero de tarjeta: ");
        int numTarjeta = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese fecha de caducidad EX:(mes-año => 2025-05): ");
        String caducidad = sc.nextLine();
        System.out.print("Ingrese codigo CVV: ");
        int cvv = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese su codigo postal: ");
        int cPostal = sc.nextInt();
        sc.nextLine();

        Tarjeta t = new Tarjeta(numTarjeta,caducidad,cPostal,cvv);
        System.out.print("""
                         1. Confirmar Pago
                         2. Cancelar
                         """);
        int opcion = getOpcion(2);
        if (opcion==1){
            PersistenciaPagos pago = new PersistenciaPagos();
            pago.guardar(t,reserva);
        }
    }

    
}
