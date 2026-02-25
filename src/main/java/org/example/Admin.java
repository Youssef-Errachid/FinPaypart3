package org.example;

public class Admin {
    private String nom;
    private int id_p;
    private int nomberFacture;
    private Double total;

    public Admin(String nom, int id_p, int nomberFacture, Double total) {
        this.nom = nom;
        this.id_p = id_p;
        this.nomberFacture = nomberFacture;
        this.total = total;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNomberFacture() {
        return nomberFacture;
    }

    public void setNomberFacture(int nomberFacture) {
        this.nomberFacture = nomberFacture;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
