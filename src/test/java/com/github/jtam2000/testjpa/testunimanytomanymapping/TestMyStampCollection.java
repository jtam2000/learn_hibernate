package com.github.jtam2000.testjpa.testunimanytomanymapping;

import com.github.jtam2000.jpa.relationships.manytomany.MyStampCollection;
import org.junit.Test;

import java.time.LocalDate;

public class TestMyStampCollection {

    @Test
    public void test_printRandomCollection() {

        for (int i = 0; i < 10; i++) {

            MyStampCollection collection = new MyStampCollection("first stamp collection",
                    "Jason", LocalDate.now());
            System.out.println(collection);

            collection = MyStampCollection.randomCollection("Public Collection " + (i+1));
            System.out.println(collection);
        }
    }

}
