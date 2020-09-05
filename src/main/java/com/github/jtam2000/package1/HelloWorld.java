package com.github.jtam2000.package1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class HelloWorld {

    public static void main(String[] args) {

        System.out.printf("%nHello World, it is now => %s%n", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }
}
