/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetodosPago;

/**
 *
 * @author vv
 */
public class PagoServicioExterno extends Pago{
    
    protected int numeroCuenta;

    public PagoServicioExterno(int numeroCuenta, int pagoID, String usuarioID,
            String reservaID, double monto, String fecha) {
        super(pagoID, reservaID, monto, fecha);
        this.numeroCuenta = numeroCuenta;
    }
    
    public PagoServicioExterno(int numeroCuenta) {
        super();
        this.numeroCuenta = numeroCuenta;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }
}
