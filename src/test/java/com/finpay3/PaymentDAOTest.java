package com.finpay3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.Date;

import static com.finpay3.PaymentDAO.GenerationDunRecuDePaiement;
import static com.finpay3.PaymentDAO.calculateCommission;
import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {
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

    // this is the test of commission calculation
    @Test
    void calculationOfCommissionTest(){
        double commission = calculateCommission(1000);
        assertEquals(20,commission,"Calculation doesn't work");
    }

    //this will test the function that create the name of recu document
    @Test
    void nameOfRecuDocument(){
        String nameOfTheDocument = PaymentDAO.nameOfRecuDocument(1);
        assertEquals("recu_1.pdf",nameOfTheDocument);
    }

    @Test
    public void testFileIsCreatedWithCorrectName() {
        int idPayment = 7;

        GenerationDunRecuDePaiement(7, 3, new Date(2026-02-24), 1000.0, 500.0);

        File file = new File("recu_" + idPayment + ".pdf");
        assertTrue(file.exists());
        file.delete();

    }

}