/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author vv
 */
public class Reserva {
    
    private int reservaID;
    private int clienteID;
    private int alojaminetoID;
    private String fechaInicio;
    private String fechaSalida;
    private int tiempoEstancia;
    private static int reservaIDPred = 9013;
    
    

    public Reserva(int reservaID, int clienteID, int alojaminetoID, String fechaInicio, String fechaSalida) {
        this.reservaID = reservaID;
        this.clienteID = clienteID;
        this.alojaminetoID = alojaminetoID;
        this.fechaInicio = fechaInicio;
        this.fechaSalida = fechaSalida;
        this.tiempoEstancia = Sistema.diasEstancia(fechaInicio, fechaSalida);
    }
    
    public Reserva(int clienteID, int alojaminetoID, String fechaInicio, String fechaSalida) {
        this.reservaID = reservaIDPred;
        this.clienteID = clienteID;
        this.alojaminetoID = alojaminetoID;
        this.fechaInicio = fechaInicio;
        this.fechaSalida = fechaSalida;
        this.tiempoEstancia = Sistema.diasEstancia(fechaInicio, fechaSalida);
        this.reservaIDPred++;
    }

    @Override
    public String toString() {
        return "- ReservaID= " + reservaID + "\n- ClienteID= " + clienteID + "\n- AlojaminetoID= " + alojaminetoID + "\n- FechaInicio= " + fechaInicio + "\n- FechaSalida= " + fechaSalida + "\n- TiempoEstancia= " + tiempoEstancia +"dias\n";
    }

    public int getReservaID() {
        return reservaID;
    }

    public int getClienteID() {
        return clienteID;
    }

    public int getAlojaminetoID() {
        return alojaminetoID;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public int getTiempoEstancia() {
        return tiempoEstancia;
    }

    
    
    
}
