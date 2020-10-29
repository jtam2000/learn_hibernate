package com.github.jtam2000.testjpa.testunimanytomanymapping;

import com.github.jtam2000.jpa.relationships.manytomany.Stamp;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestStamp {

    @Test
    public void test_generateFaceValue() {

        for (int i = 0; i < 100; i++) {
            double fv = Stamp.generateTwoDecimalFaceValue();
            System.out.println(fv);
            assertTrue("value should be between 0.01 and 0.99", fv >= 0.01 && fv <= 0.99);
        }

    }

    @Test
    public void test_printStampValue() {

        for (int i = 0; i < 10; i++) {
            System.out.println(Stamp.randomDefinitiveStamp("Hong_Kong"));
        }

    }

}
