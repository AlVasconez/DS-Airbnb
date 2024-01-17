/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.Anfitrion;
import Usuarios.Ianfitrion;
import Util.ConexionDB;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


/**
 *
 * @author vv
 */
public class Alojamiento implements AddServicio, AddReglamento{
    
    private int alojaminetoID;
    private Anfitrion anfitrion;
    private double precio;
    private int habitaciones;
    private String ubicacion;
    private double calificacion;
    private double tarifaAirbnb;
    private String nombre;
    public static int alojamientoIDActual = 1000;
    private ArrayList<Double> calificaciones= new ArrayList<>();

    //Contructor que se usa cuando por consola se crea un alojamiento
    public Alojamiento(Anfitrion anfitrion, double precio, int habitaciones, String ubicacion,double tarifaAirbnb,String nombre) {
        this.alojaminetoID = 0;
        this.anfitrion = anfitrion;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.ubicacion = ubicacion;
        this.calificacion = 0;
        this.tarifaAirbnb = tarifaAirbnb;
        this.nombre=nombre;
    }
    
    //Constructor usado al recibir datos de la BD
    public Alojamiento(int alojaminetoID,Anfitrion anfitrion, double precio, int habitaciones, String ubicacion,double tarifaAirbnb,String nombre) {
        this.alojaminetoID = alojaminetoID;
        this.anfitrion = anfitrion;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.ubicacion = ubicacion;
        this.calificacion = 0;
        this.tarifaAirbnb=tarifaAirbnb;
        this.nombre=nombre;
    }

    public int getAlojaminetoID() {
        return alojaminetoID;
    }
    
    public Anfitrion getAnfitrion() {
        return anfitrion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public double getTarifaAirbnb() {
        return tarifaAirbnb;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public String getNombre() {
        return nombre;
    }


    @Override
    public String toString() {
        return  "alojaminetoID=" + alojaminetoID + ", anfitrion=" + anfitrion.getNombre() + ", precio=" + precio + ", habitaciones=" + habitaciones + ", ubicacion=" + ubicacion + ", calificacion=" + calificacion + ", calificaciones=" + calificaciones;
    }

}
