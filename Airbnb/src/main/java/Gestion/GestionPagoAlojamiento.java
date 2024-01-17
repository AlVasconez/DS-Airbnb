/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion;

import MetodosPago.PagarConServicioExterno;
import MetodosPago.PagarConTarjeta;
import SistemaInterno.Alojamiento;
import SistemaInterno.CalculoTiempoReserva;
import SistemaInterno.FacturarReserva;
import SistemaInterno.Reserva;
import static SistemaInterno.Sistema.getOpcion;
import Usuarios.Cliente;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class GestionPagoAlojamiento implements FacturarReserva, PagarConTarjeta, PagarConServicioExterno, CalculoTiempoReserva{
    
    public void realizarReserva(Cliente cliente, Alojamiento aloj){
        Scanner sc = new Scanner(System.in);
        System.out.print("Indique desde que fecha desea reservar EX:(MES-DIA -> 08-23): ");
        String fInicio = "2023-"+sc.nextLine();
        System.out.print("Indique hasta que fecha desea reservar EX:(MES-DIA -> 08-25): ");
        String fFinal = "2023-"+sc.nextLine();
        double montoT = ((diasEstancia(fInicio,fFinal)*aloj.getPrecio())+aloj.getTarifaAirbnb());
        Reserva r = new Reserva(cliente.getUsuarioID(),aloj.getAlojaminetoID(),fInicio,fFinal);
        factura(r, aloj.getNombre(), montoT);
        pagarReserva(r);
    }
    
    private void pagarReserva(Reserva reserva){
        System.out.println("""
                           Escoja de que forma desea pagar:
                           1. Tarjeta
                           2. Paypal
                           3. Google Pay
                           4. Salir
                           """);
        int opcion = getOpcion(4);
        switch(opcion){
            case 1:
                pagarConTarjeta(reserva);
                break;
            case 2:
                pagoConServicioExterno( reserva, "Paypal");
                break;
            case 3:
                pagoConServicioExterno( reserva, "GooglePay");
                break;
            case 4:
                break;
        }
        
    }

    
}
