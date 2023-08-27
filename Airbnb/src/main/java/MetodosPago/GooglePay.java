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
public class GooglePay extends Pago{
    
    protected int numeroCuenta;

    public GooglePay(int numeroCuenta, int pagoID, String usuarioID, String reservaID, double monto, String fecha) {
        super(pagoID, reservaID, monto, fecha);
        this.numeroCuenta = numeroCuenta;
    }
    
    public GooglePay(int numeroCuenta) {
        super();
        this.numeroCuenta = numeroCuenta;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }
    
    
}
