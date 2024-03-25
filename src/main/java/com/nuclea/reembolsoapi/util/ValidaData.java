package com.nuclea.reembolsoapi.util;

import java.time.LocalDateTime;

public class ValidaData {

    public static boolean validarDiaMes(int diaInicio, int diaFim, LocalDateTime data){

        LocalDateTime inicioIntervalo = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), diaInicio, 0, 0);
        LocalDateTime fimIntervalo = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), diaFim, 23, 59);

        LocalDateTime dataValidacao = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), data.getHour(), data.getMinute());


        boolean dentroDoIntervalo = dataValidacao.isAfter(inicioIntervalo) && dataValidacao.isBefore(fimIntervalo);

        if (dentroDoIntervalo) {
            System.out.println("A data está dentro do intervalo.");
            return true;
        } else {
            System.out.println("A data está fora do intervalo.");
            return false;
        }

    }
}
