/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaInterno;

import Usuarios.Cliente;
import java.util.ArrayList;

/**
 *
 * @author vv
 */
public class Resenha {
    protected String contenido;
    protected Alojamiento alojamiento;
    protected double calificacion;
    protected Cliente cliente;

    public Resenha(String contenido, Alojamiento alojamiento, double calificacion, Cliente cliente) {
        this.contenido = contenido;
        this.alojamiento = alojamiento;
        this.calificacion = calificacion;
        this.cliente = cliente;
        
    }
    
    @Override
    public String toString(){
        return "- Calificacion: "+this.calificacion+" / Comentario:"+this.contenido;
    }
    
}
