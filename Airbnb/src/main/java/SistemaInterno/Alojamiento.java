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
    private ArrayList<String> servicios = new ArrayList<>();
    private ArrayList<String> reglamento = new ArrayList<>();

    
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

    public int getAlojaminetoID() {
        return alojaminetoID;
    }

    public void setServicios(String servicio) {
        this.servicios.add(servicio);
    }

    public void setReglamento(String regla) {
        this.reglamento.add(regla);
    }

    @Override
    public String toString() {
        return "Alojamiento{" + "alojaminetoID=" + alojaminetoID + ", anfitrion=" + anfitrion.getAnfitrion() + ", precio=" + precio + ", habitaciones=" + habitaciones + ", ubicacion=" + ubicacion + ", calificacion=" + calificacion + ", calificaciones=" + calificaciones + ", servicios=" + servicios + ", reglamento=" + reglamento + '}';
    }
    
 
    public void enlistarAlojamiento(){
        System.out.printf("Lugar en: %s\n    Precio: %s x noche\n    Calificacion: %s estrellas\n",this.ubicacion,this.precio,this.calificacion);
    }
    
    
    public void detallarAlojamiento(){
        System.out.printf("\n %s\n-Costo: %s x noche\n-Habitaciones: %d\n-Calificacion: %s estrellas",this.ubicacion.toUpperCase(),this.precio,this.habitaciones,this.calificacion);
    }
    
    
//---------------------------------------------------------------------------------------------------
    //Metodo que cambia la calificacion del alojamineto
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
    

    
    
//-------------------------------------------------------------------------------------------
    //Metodos de la opcion publicar alojamientos (ANFITRION)
    public void addServicio(){
        Scanner sc = new Scanner(System.in);
        int opcion=0;
        try{
            System.out.println("Servicio que posee su alojamiento: ");
            String servicio = sc.nextLine();
            this.servicios.add(servicio);

            System.out.println("Desea agregar otro servicio?\n1.si\n2.no");
            opcion =Sistema.getOpcion(2);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            if(opcion==1){
                this.addServicio();
            }
        }
    }
    
    public void addReglamento(){
        Scanner sc = new Scanner(System.in);
        int opcion=0;

        try{
            System.out.println("Regla a agregar: ");
            String regla = sc.nextLine();
            this.reglamento.add(regla);

            System.out.println("Desea agregar otra regla?\n1.si\n2.no");
            opcion =Sistema.getOpcion(2);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            if(opcion==1){
                this.addReglamento();
            }
        }
    }

    
}
