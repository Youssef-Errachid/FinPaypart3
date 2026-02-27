package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactureDAOTest {
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