/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author user
 */
///PURA FRUTA
/// EXCEPCION MEXICANA
public class ExcepcionIndexador {

    public String obtenerExcepcion(int i) {
        switch (i) {
            case 1:
                return "El documento seleccionado ya se encuentra cargado";
            default:
                return "";
        }

    }
}
