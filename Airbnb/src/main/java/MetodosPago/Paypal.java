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
public class Paypal extends PagoServicioExterno{
    
    public Paypal(int numeroCuenta, int pagoID, String usuarioID, String reservaID, double monto, String fecha) {
        super(numeroCuenta, pagoID, usuarioID, reservaID, monto, fecha);
        this.numeroCuenta = numeroCuenta;
    }
    
    public Paypal(int numeroCuenta) {
        super(numeroCuenta);
    }
    
    
}
