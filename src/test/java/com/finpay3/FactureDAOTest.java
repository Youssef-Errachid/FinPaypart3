package com.finpay3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.finpay3.FactureDAO.factureName;
import static com.finpay3.FactureDAO.facturePDF;
import static org.junit.jupiter.api.Assertions.*;

class FactureDAOTest {

    @BeforeAll
    static void initAll() {
        System.out.println("The testes has begun");
    }

    @BeforeEach
    void message(){
        System.out.println("The test has begun.");
    }

    @Test
    void fileIsCreatedWithCorrectName(){
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