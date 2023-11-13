/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SistemaInterno;



/**
 *
 * @author vv
 */
public interface FacturarReserva {
    
    default void factura(Reserva reserva, String AlojamientoNombre, double montoTotal){
        System.out.println("|---------------------------------|");
        System.out.println("|          RESUMEN RESERVA        |");
        System.out.println("| Alojamiento: "+AlojamientoNombre+"");
        System.out.println("| Reservado desde: "+reserva.getFechaInicio()+" ");
        System.out.println("| Reservado hasta: "+reserva.getFechaSalida()+" ");
        System.out.println("| Monto total: "+montoTotal+"     ");
        System.out.println("|---------------------------------|");
    }
}
