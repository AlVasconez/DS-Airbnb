/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Alojamiento;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public abstract class Usuario {
    
    protected String usuarioID;
    protected String contrasenha;
    protected String nombre;
    protected String correo;
    protected String telefono;
    protected String direccionFisica;
    protected boolean verificacion;

    
    public Usuario(Integer usuarioID, String contrasenha, String nombre, String correo, String telefono,String direccionFisica, boolean verificacion) {
        this.usuarioID =usuarioID.toString();
        this.contrasenha = contrasenha;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccionFisica= direccionFisica;
        this.verificacion = verificacion;
    }

    public Usuario(String usuarioID, String contrasenha) {
        this.usuarioID = usuarioID;
        this.contrasenha = contrasenha;
    }
    
    public void enviarMensaje(Usuario receptor){
        Scanner sc = new Scanner(System.in);
        System.out.println("Mensaje para "+receptor.nombre+":");
        Mensaje m = new Mensaje(sc.nextLine(),this,receptor);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        
        return other.usuarioID.equals(this.usuarioID) && other.contrasenha.equals(this.contrasenha);
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public String getContrasenha() {
        return contrasenha;
    }
    
    
    
}
