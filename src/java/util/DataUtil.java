/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Julliano
 */
public class DataUtil {
    public static void listaFormatar(){
        int[] array = new int[10];
        for(int i=0;i<=9;i++){
            System.out.printf("{%5d} %7d\n",i,array[i]);
        }
    }
    
    
}
