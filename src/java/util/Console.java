package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Console {
	
    public static String scanString(Object out)
    {
            System.out.print(out);
            Scanner scanner = new Scanner (System.in);
            return(scanner.nextLine());
    }

    public static int scanInt(Object out)
    {
            System.out.print(out);
            Scanner scanner = new Scanner (System.in);
            return(scanner.nextInt());		
    }

    public static double scanDouble(Object out)
    {
            System.out.print(out);
            Scanner scanner = new Scanner (System.in);
            return(scanner.nextDouble());		
    }

    public static float scanFloat(Object out)
    {
            System.out.print(out);
            Scanner scanner = new Scanner (System.in);
            return(scanner.nextFloat());		
    }

    public static boolean scanBoolean(Object out)
    {
            System.out.print(out);
            Scanner scanner = new Scanner (System.in);
            return(scanner.nextBoolean());		
    }

    public static char scanChar(Object out)
    {
            System.out.print(out);
            Scanner scanner = new Scanner (System.in);
            return(scanner.next().charAt(0));				
    }

    public static Date scanDate(Object out)
    {
        String dt = Console.scanString(out);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataUsuario = new Date();
        try {
             dataUsuario = sdf.parse(dt.toString());
        } catch (Exception e) {
            System.out.println("Data Inválida!");
            scanDate(out);
        }
        return(dataUsuario);
    }
    
    public static String formataData(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return(sdf.format(data));
    }
    
    public static Date scanHora(Object out)
    {
        String hr = Console.scanString(out);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date horaUsuario = new Date();
        try {
             horaUsuario = sdf.parse(hr.toString());
        } catch (Exception e) {
            System.out.println("Hora Inválida!");
            scanHora(out);
        }
        return(horaUsuario);
    }
    public static String formataHora(Date hora){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return(sdf.format(hora));
    }
}
