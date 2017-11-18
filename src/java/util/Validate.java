/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author mi
 */
public class Validate {

    public Validate() {
    }
    
    public Boolean validaString(String param){
        return !(param.trim().isEmpty());
    }
    
    /*public Boolean validaInteger(String param){
        try{
            Integer i = Integer(param);
        }
    }*/
}
