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
    
    protected int pagoID;
    protected String reservaID;
    protected double monto;
    protected String fecha;

    public Pago(int pagoID, String reservaID, double monto, String fecha) {
        this.pagoID = pagoID;
        this.reservaID = reservaID;
        this.monto = monto;
        this.fecha = fecha;
    }
    
    public Pago() {

    }

    public int getPagoID() {
        return pagoID;
    }

    public void setPagoID(int pagoID) {
        this.pagoID = pagoID;
    }

    public String getReservaID() {
        return reservaID;
    }

    public void setReservaID(String reservaID) {
        this.reservaID = reservaID;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
}
