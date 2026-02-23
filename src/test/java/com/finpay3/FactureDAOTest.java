package com.finpay3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.finpay3.FactureDAO.factureName;
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

}