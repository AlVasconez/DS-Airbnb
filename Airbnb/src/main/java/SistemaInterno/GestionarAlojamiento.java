/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SistemaInterno;

import Usuarios.Anfitrion;

/**
 *
 * @author vv
 */
public interface GestionarAlojamiento extends AddServicio, AddReglamento, DetallarAlojamiento{
    void publicar(Anfitrion anfitrion);
    
}
