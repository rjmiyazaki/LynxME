package br.com.lynx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LynxMEUtil {

    // Método para validar se o horário está dentro do intervalo permitido
    public static boolean HorarioValido(String startTime, String endTime){
        Boolean result = false;

        if ((startTime == null)  || (startTime.isEmpty()))
            startTime = "06:00";

        if ((endTime == null)  || (endTime.isEmpty()))
            endTime = "23:59";

        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        String nowTime = new SimpleDateFormat("HH:mm").format(new Date().getTime());// Pega hora atual do Sistema

        try {
            if ((parser.parse(nowTime).after(parser.parse(startTime))) && (parser.parse(nowTime).before(parser.parse(endTime)))){
                result = true;
            }
        }
        catch(ParseException ex) {
        }

        return result;
    }
}
