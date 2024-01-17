/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import MetodosPago.*;
import SistemaInterno.Reserva;
import Util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author vv
 */
public class PersistenciaPagos {
    
    public PersistenciaPagos(){}
    
    public void guardar(PagoServicioExterno pago, Reserva r){
        Connection c = ConexionDB.getConection();
        PreparedStatement stmt=null;
        try {
        if(pago instanceof GooglePay){
            stmt = c.prepareStatement("call InsertarPagoGooglePay(?,?,?,?,?)");
        }
        else if(pago instanceof Paypal){
            stmt = c.prepareStatement("call InsertarPagoPaypal(?,?,?,?,?)");
        }
        
            stmt.setInt(1, r.getClienteID());
            stmt.setInt(2, r.getAlojaminetoID());
            stmt.setString(3, r.getFechaInicio());
            stmt.setString(4, r.getFechaSalida());
            stmt.setInt(5, pago.getNumeroCuenta());
            stmt.executeUpdate();
            System.out.println("**SE HA REALIZADO SU RESERVA CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void guardar(Tarjeta pago, Reserva r){
        Connection c = ConexionDB.getConection();
        try {
            PreparedStatement stmt = c.prepareStatement("call InsertarPagoTarjeta(?,?,?,?,?,?,?,?)");
            stmt.setString(1, ""+r.getClienteID());
            stmt.setString(2, ""+r.getAlojaminetoID());
            stmt.setString(3, r.getFechaInicio());
            stmt.setString(4, r.getFechaSalida());
            stmt.setString(5, ""+pago.getNumeroTarjeta());
            stmt.setString(6, ""+pago.getCaducidad());
            stmt.setString(7, ""+pago.getCodigoPostal());
            stmt.setString(8, ""+pago.getCodigocvv());
            stmt.executeUpdate();
            System.out.println("**SE HA REALIZADO SU RESERVA CORRECTAMENTE**");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
