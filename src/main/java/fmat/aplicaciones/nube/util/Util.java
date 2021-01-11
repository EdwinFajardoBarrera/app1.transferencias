package fmat.aplicaciones.nube.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    
    public static String getFormattedDate(Date fecha){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = formatter.format(fecha);
        return date;
    }

}
