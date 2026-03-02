package com.finpay3;

import org.junit.jupiter.api.*;

import java.io.File;

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
    @BeforeEach
    void setUp(){
        System.out.println("Setting up...");
    }
    @Test
    void Paid_Test(){
        assertEquals(Statut.PAYEE, FactureDAO.updateFactureStatut(1,Statut.PAYEE) );
    }
    @Test
    void InpaidTest(){
        assertEquals(Statut.NON_PAYEE, FactureDAO.updateFactureStatut(1, Statut.NON_PAYEE));
    }
    @Test
    void partielleTest(){
        assertEquals(Statut.PARTIELLE, FactureDAO.updateFactureStatut(1,Statut.PARTIELLE));
    }

    @Test
    void calculerTotalPrestataire(){
        assertEquals(4500, Main.calculerTotalPrestataire(1));

    }
    @Test
    @DisplayName("Test du List des factures d'un prestataire est vide ! ")
    void testerlisteVide() {
        assertFalse(FactureDAO.findFacturePrestataire(1).isEmpty());
    }

    @Test
    @DisplayName("Test du prestataire a plusiers factures")
    void AvoirPlusieursFacturePrestataire (){
        assertTrue(FactureDAO.findFacturePrestataire(1).size() >= 1 );
    }

}