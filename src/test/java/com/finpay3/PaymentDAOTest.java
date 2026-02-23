package com.finpay3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}