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
public interface PagarConServicioExterno {
    
    default void pagoConServicioExterno(Reserva reserva, String tipoApp){
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese numero de cuenta: ");
        int numCuentaP = sc.nextInt();
        sc.nextLine();
        PagoServicioExterno p = null;
        if(tipoApp=="Paypal"){
            p = new Paypal(numCuentaP);
        }
        else if(tipoApp=="Paypal"){
            p = new GooglePay(numCuentaP);
        }
        System.out.print("""
                         1. Confirmar Pago
                         2. Cancelar
                         """);
        int opcion = getOpcion(2);
        if (opcion==1){
            PersistenciaPagos pago = new PersistenciaPagos();
            pago.guardar(p,reserva);
        }
    }
}
