package ru.magicvolley.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class DateStringService {

    public String getDateString(LocalDate dateStart, LocalDate dateEnd) {
        String monthStartString = getMounthString(Objects.nonNull(dateStart) ? dateStart.getMonthValue() : 0);
        String monthEndString = getMounthString(Objects.nonNull(dateStart) ? dateEnd.getMonthValue() : 0);

        if (monthStartString.equals(monthEndString)) {
            return getMonth(dateStart) + "-" + getMonth(dateEnd) + " " + monthStartString;
        } else {
            return getMonth(dateStart) + " " + monthStartString + " - " + getMonth(dateEnd) + " " + monthEndString;
        }
    }

    public int getMonth(LocalDate date) {
        return Objects.nonNull(date) ? date.getDayOfMonth() : 0;
    }

    public String getMounthString(int month) {
        return switch (month) {
            case 1 -> "января";
            case 2 -> "февраля";
            case 3 -> "марта";
            case 4 -> "апряля";
            case 5 -> "мая";
            case 6 -> "июня";
            case 7 -> "июля";
            case 8 -> "августа";
            case 9 -> "сентября";
            case 10 -> "октября";
            case 11 -> "ноября";
            case 12 -> "декабря";
            default -> "";
        };
    }

}
