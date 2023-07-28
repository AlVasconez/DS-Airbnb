/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.Cliente;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author vv
 */
public class Reserva {
    
    protected String reservaID;
    protected Cliente cliente;
    protected Alojamiento alojamineto;
    protected String alojaminetoID;
    protected int tiempoEstancia;
    protected Double tarifaAirbnb;
    protected ArrayList<Date> fechasDisponibles = new ArrayList<>();

    public Reserva(String reservaID, Cliente cliente, Alojamiento alojamineto, String alojaminetoID, int tiempoEstancia, Double tarifaAirbnb) {
        this.reservaID = reservaID;
        this.cliente = cliente;
        this.alojamineto = alojamineto;
        this.alojaminetoID = alojaminetoID;
        this.tiempoEstancia = tiempoEstancia;
        this.tarifaAirbnb = tarifaAirbnb;
    }
    
    
    
}
