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
public class Paypal extends Pago{
    
    protected int numeroCuenta;

    public Paypal(int numeroCuenta, String pagoID, String usuarioID, String reservaID, double monto, Date fecha) {
        super(pagoID, usuarioID, reservaID, monto, fecha);
        this.numeroCuenta = numeroCuenta;
    }
    
    
}
