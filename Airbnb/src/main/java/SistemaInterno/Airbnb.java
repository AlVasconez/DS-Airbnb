/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package SistemaInterno;



/**
 *
 * @author vv
 */
public class Airbnb {
    

    public static void main(String[] args) {
        
        //El programa no se detiene a menos que se pare de ejecutar.
        //Permite cambiar de cuentas dentro del mismo proceso.
        boolean inicio = true;
        while(inicio = true){
            Sistema.menuInicio();
            int opcion=0;
            do{
                opcion =Sistema.menuPrincipal();
                System.out.println("");
                //ingresar metodos en cada caso
                switch(opcion){
                    case 1:
                        System.out.println("opcion1");
                        break;
                    case 2:
                        System.out.println("opcion2");
                        break;
                    case 3:
                        System.out.println("opcion3");
                        break;

                }
            }
            while(opcion!=3);
        }
    }
}
