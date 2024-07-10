package com.example.demomv;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Util {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-DD");
}
