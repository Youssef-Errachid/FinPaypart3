package com.finpay3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.finpay3.Main.nameOfTheRapport;
import static com.finpay3.Main.rapportMois;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    // this will start before all testes.
    @BeforeAll
    static void initAll() {
        System.out.println("The testes has begun");
    }

    // this will start before each test.
    @BeforeEach
    void message(){
        System.out.println("The test has begun.");
    }

    @Test
    void rapportName(){
        String fileName = nameOfTheRapport("2026-2");
        assertEquals("rapport_2026-2.xlsx",fileName);
    }
    @Test
    void testFileIsCreatedWithCorrectName() {
        String currentMonth = "2026-02";
        try {
            rapportMois();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        File excel = new File("rapport_"+currentMonth+".xlsx");
        excel.delete();

    }
}