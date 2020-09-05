package com.github.jtam2000.package2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class HelloWorldTwo {

    public static void main(String[] args) {
        System.out.printf("%nHello World - Second Application in Same Project %nApp Date => %s%n",
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
    }
}
