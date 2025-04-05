package utils;

import java.time.LocalTime;

public class Utils {

    public static int horaParaSegundo(LocalTime hora) {
        int allInSeconds = hora.getHour() * 60;
        allInSeconds += hora.getMinute();
        return allInSeconds;
    }

    public static LocalTime segundoParaHoras(int segundos) {
        int hora = segundos / 60;
        int segundosFinal = segundos - hora;
        return LocalTime.of(hora, segundosFinal);
    }
}
