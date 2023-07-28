/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.Anfitrion;
import java.util.ArrayList;

/**
 *
 * @author vv
 */
public class Alojamiento {
    
    protected String alojaminetoID;
    protected Anfitrion anfitrion;
    protected double precio;
    protected int habitaciones;
    protected String ubicacion;
    protected double calificacion;
    private ArrayList<Double> calificaciones= new ArrayList<>();
    protected ArrayList<String> servicios = new ArrayList<>();
    protected ArrayList<String> reglamento = new ArrayList<>();

    public Alojamiento(String alojaminetoID, Anfitrion anfitrion, double precio, int habitaciones, String ubicacion) {
        this.alojaminetoID = alojaminetoID;
        this.anfitrion = anfitrion;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.ubicacion = ubicacion;
        this.calificacion = 0;
    }
    
    
    
    public void cambiarCalificaion(double nuevaCalificacion){  
        this.calificaciones.add(nuevaCalificacion);
        double calificacion=0;
        int size =0;
        
        for(Double d:this.calificaciones){
            calificacion+=d;
            size++;
        }
        
        double calificaionFinal = (calificacion)/(size);
        this.calificacion=calificaionFinal;
    }
    
    
    
    
}
