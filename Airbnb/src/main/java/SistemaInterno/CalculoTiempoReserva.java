/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SistemaInterno;

/**
 *
 * @author vv
 */
public interface CalculoTiempoReserva {
    default int diasEstancia(String fInicio, String fSalida){

        String[] fechaI = fInicio.split("-");
        String[] fechaS = fSalida.split("-");
        int anio = 365*(Integer.parseInt(fechaS[0])-Integer.parseInt(fechaI[0]));
        int mes = 30*(Integer.parseInt(fechaS[1])-Integer.parseInt(fechaI[1]));
        int dia = 1*(Integer.parseInt(fechaS[2])-Integer.parseInt(fechaI[2]));
        if((anio+mes+dia)==0){
            return 1;
        }
        return anio+mes+dia;
    }
}
