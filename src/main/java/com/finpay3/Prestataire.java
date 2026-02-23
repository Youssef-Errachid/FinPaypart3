package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.List;

public class Prestataire {
    private int id;
    private String name;
    private List<Facture> factures;

    public Prestataire(int id, String name) {
        this.id = id;
        this.name = name;
        this.factures = new ArrayList();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Facture> getFactures() {
        return this.factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    public String toString() {
        return "Prestataire{id=" + this.id + ", name='" + this.name + "'}";
    }
}

