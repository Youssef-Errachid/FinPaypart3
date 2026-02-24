package com.finpay3;

import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.Document;
import javax.swing.text.FieldView;

import java.io.File;
import java.util.Scanner;

import static com.finpay3.FactureDAO.factureName;
import static com.finpay3.FactureDAO.facturePDF;
import static com.finpay3.Main.sc;
import static org.junit.jupiter.api.Assertions.*;

class FactureDAOTest {
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
    void facturenametest(){
        String result = factureName(1);
        assertEquals("facture_1.pdf",result);
    }

    @Test
    void FileIsCreatedWithCorrectName(){
        int id = 1;
        facturePDF(id);
        File document = new File("Facture"+id+ ".pdf");
        assertTrue(document.exists());
    }

    @AfterAll
    static void cleanUp(){
        System.out.println("Clean Up");
        new File("Facture1.pdf").delete();
    }

}