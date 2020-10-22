package com.github.jtam2000.testjpa.testunimanytoonemapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import com.github.jtam2000.jpa.relationships.manytoone.PostalCountry;
import org.junit.Test;

import java.time.LocalDate;

import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.CHINA;
import static org.junit.Assert.assertEquals;

public class TestPostageStamp {


    private static final double ASSERT_DOUBLE_TOLERANCE = 0.0001;
    protected PostageStamp stamp;
    protected PostalCountry country;
    protected double faceValue;
    protected String title;
    protected LocalDate issueDate;

    protected void createOnePostageStamp(PostalCountry whichCountry) {

        country = whichCountry;
        faceValue = 0.80D;
        title = "Celebrating Chinese New Year - Year of the Pig 2019";
        issueDate = LocalDate.of(2019, 2, 18);
        stamp = new PostageStamp(country, faceValue, title, issueDate);
    }

    @Test
    public void test_createOnePostageStamp() {

        //given=@Before

        //when
        createOnePostageStamp((new PostalCountry(CHINA)));

        //then
        assertAllPostageAttributes();
    }

    private void assertAllPostageAttributes() {

        assertEquals("country is set correctly", country, stamp.getCountry());
        assertEquals("face value is set correctly", faceValue, stamp.getFaceValue(), ASSERT_DOUBLE_TOLERANCE);
        assertEquals("title is set correctly", title, stamp.getTitle());
        assertEquals("issue date is set correctly", issueDate, stamp.getIssueDate());
        System.out.println(stamp);
    }

}
