/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetodosPago;

import SistemaInterno.Reserva;
import static SistemaInterno.Sistema.getOpcion;
import Util.ConexionDB;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class Tarjeta extends Pago implements MetodoPago{
    
    protected int numeroTarjeta;
    protected String caducidad;
    protected int codigoPostal;
    protected int codigocvv;

    public Tarjeta(int numeroTarjeta, String caducidad, int codigoPostal, int codigocvv, int pagoID, String reservaID, double monto, String fecha) {
        super(pagoID, reservaID, monto, fecha);
        this.numeroTarjeta = numeroTarjeta;
        this.caducidad = caducidad;
        this.codigoPostal = codigoPostal;
        this.codigocvv = codigocvv;
    }
    
    public Tarjeta(int numeroTarjeta, String caducidad, int codigoPostal, int codigocvv) {
        super( );
        this.numeroTarjeta = numeroTarjeta;
        this.caducidad = caducidad;
        this.codigoPostal = codigoPostal;
        this.codigocvv = codigocvv;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public int getCodigocvv() {
        return codigocvv;
    }

    @Override
    public void pagar(Reserva reserva) {
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
            ConexionDB.registrarPagoTarjeta(reserva, t);
        }    }

  
}
