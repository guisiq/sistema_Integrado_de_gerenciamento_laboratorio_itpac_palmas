package br.com.itpacpalmas.api_sig_lab_itpac;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class teste {

	public static void main(String[] args) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate.now().getDayOfWeek().ordinal();
        DayOfWeek dow = DayOfWeek.from(parser.parse("24/11/2020"));
        var v = dow.values();
        System.out.println(v);
	}

}