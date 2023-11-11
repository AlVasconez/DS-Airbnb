/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import SistemaInterno.Alojamiento;
import Util.ConexionDB;
import Usuarios.Usuario;

/**
 *
 * @author usuario
 */
public class ListaFav {

        
    public ListaFav(){}    
    
    // principio respondabilidad unica 
    public void addListaFavoritos(Alojamiento alojamiento,Usuario usuario){
        ConexionDB.registrarFavorito(alojamiento.getAlojaminetoID(),usuario.getUsuarioID());
    }
}
