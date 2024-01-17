/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Usuarios;

/**
 *
 * @author usuario
 */
public interface Ianfitrion {
    public Anfitrion getAnfitrion();
    // para quitar dependencias entre la clase alojamiento e anfitrion se creo esta interfas de la cual dependeran ambos en vez de entre ellos
    public String getNombre();
}
