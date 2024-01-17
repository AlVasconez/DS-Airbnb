/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import Persistencia.PersistenciaFavoritos;
import SistemaInterno.Alojamiento;
/**
 *
 * @author usuario
 */
public class ListaFav {

        
    public ListaFav(){}    
    
    // principio respondabilidad unica 
    public void addListaFavoritos(Alojamiento alojamiento,Usuario usuario){
        PersistenciaFavoritos pFavs = new PersistenciaFavoritos();
            pFavs.guardar(alojamiento.getAlojaminetoID(),usuario.getUsuarioID());
    }
}
