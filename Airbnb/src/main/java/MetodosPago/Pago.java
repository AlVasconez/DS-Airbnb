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
public abstract class Pago {
    
    protected String pagoID;
    protected String usuarioID;
    protected String reservaID;
    protected double monto;
    protected Date fecha;

    public Pago(String pagoID, String usuarioID, String reservaID, double monto, Date fecha) {
        this.pagoID = pagoID;
        this.usuarioID = usuarioID;
        this.reservaID = reservaID;
        this.monto = monto;
        this.fecha = fecha;
    }
    
    
    
}
