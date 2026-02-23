package com.finpay3;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Facture {
    private int idFacture;
    private Client client;
    private Prestataire prestataire;
    private static double montantTotal;
    private Statut statut;
    private Date dateFacture;
    private Timestamp dateCreation;

    public Facture(int idFacture, Client client, Prestataire prestataire, double montantTotal, Statut statut, Date dateFacture, Timestamp dateCreation) {
        this.idFacture = idFacture;
        this.client = client;
        this.prestataire = prestataire;
        Facture.montantTotal = montantTotal;
        this.statut = statut;
        this.dateFacture = dateFacture;
        this.dateCreation = dateCreation;
    }

    public int getIdFacture() {
        return this.idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public static double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        Facture.montantTotal = montantTotal;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Date getDateFacture() {
        return this.dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Timestamp getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String toString() {
        int var10000 = this.idFacture;
        return "Facture{id=" + var10000 + ", client=" + String.valueOf(this.client) + ", prestataire=" + String.valueOf(this.prestataire) + ", montant=" + montantTotal + ", statut=" + String.valueOf(this.statut) + ", dateFacture=" + String.valueOf(this.dateFacture) + ", dateCreation=" + String.valueOf(this.dateCreation) + "}";
    }

}