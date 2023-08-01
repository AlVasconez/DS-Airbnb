/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.Anfitrion;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class Alojamiento {
    
    private static int numAlojamiento =1000;
    protected int alojaminetoID;
    protected Anfitrion anfitrion;
    protected double precio;
    protected int habitaciones;
    protected String ubicacion;
    protected double calificacion;
 
    private ArrayList<Double> calificaciones= new ArrayList<>();
    protected ArrayList<String> servicios = new ArrayList<>();
    protected ArrayList<String> reglamento = new ArrayList<>();

    
    public Alojamiento(Anfitrion anfitrion, double precio, int habitaciones, String ubicacion) {
        this.alojaminetoID = numAlojamiento;
        this.anfitrion = anfitrion;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.ubicacion = ubicacion;
        this.calificacion = 0;
        numAlojamiento++;
    }
    
    public Alojamiento(int alojaminetoID,Anfitrion anfitrion, double precio, int habitaciones, String ubicacion) {
        this.alojaminetoID = alojaminetoID;
        this.anfitrion = anfitrion;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.ubicacion = ubicacion;
        this.calificacion = 0;
        numAlojamiento++;
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
    
    public void enlistarAlojamiento(){
        System.out.printf("Lugar en: %s\n    Precio: %s x noche\n    Calificacion: %s estrellas\n",this.ubicacion,this.precio,this.calificacion);
    }
    
    
    public void detallarAlojamiento(){
        System.out.printf("\n %s\n-Costo: %s x noche\n-Habitaciones: %d\n-Calificacion: %s estrellas",this.ubicacion.toUpperCase(),this.precio,this.habitaciones,this.calificacion);
    }
    
    
    public void addServicios(){
        Scanner sc = new Scanner(System.in);
        boolean continuar=true;
        
        do{
            System.out.println("Servicio que posee su alojamiento:");
            String servicio = sc.nextLine();
            this.servicios.add(servicio);
            String continuarCliente= "s";
            do{
                System.out.println("Desea agregar otro servicio? (s/n)");
                 continuarCliente = sc.nextLine().toUpperCase();
                 if(!"S".equals(continuarCliente) || !"N".equals(continuarCliente)){
                     System.out.print(("***Responda con s o n***"));
                 }
            }
            while(!"S".equals(continuarCliente) || !"N".equals(continuarCliente) );
            
            if (continuarCliente=="N"){
                continuar=false;
            }
        }
        while(continuar==true);
    }
    
    
    public void addReglamento(){
        Scanner sc = new Scanner(System.in);
        boolean continuar=true;
        
        do{
            System.out.println("Escriba la regla a agregar: ");
            String regla = sc.nextLine();
            this.reglamento.add(regla);
            String continuarCliente= "s";
            do{
                System.out.println("Desea agregar otra regla? (s/n)");
                 continuarCliente = sc.nextLine().toUpperCase();
                 if(!"S".equals(continuarCliente) || !"N".equals(continuarCliente)){
                     System.out.print(("***Responda con s o n***"));
                 }
            }
            while(!"S".equals(continuarCliente) || !"N".equals(continuarCliente) );
            
            if (continuarCliente=="N"){
                continuar=false;
            }
        }
        while(continuar==true);
    }
}
