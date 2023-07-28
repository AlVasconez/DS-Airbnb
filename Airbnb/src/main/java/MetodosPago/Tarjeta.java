/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetodosPago;

import java.util.Date;

/**
 *
 * @author vv
 */
public class Tarjeta extends Pago{
    
    protected int numeroTarjeta;
    protected Date caducidad;
    protected int codigoPostal;
    protected int codigocvv;

    public Tarjeta(int numeroTarjeta, Date caducidad, int codigoPostal, int codigocvv, String pagoID, String usuarioID, String reservaID, double monto, Date fecha) {
        super(pagoID, usuarioID, reservaID, monto, fecha);
        this.numeroTarjeta = numeroTarjeta;
        this.caducidad = caducidad;
        this.codigoPostal = codigoPostal;
        this.codigocvv = codigocvv;
    }
    
    
}
