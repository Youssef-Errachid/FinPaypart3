package org.example;

import java.util.ArrayList;
import java.util.List;

public class Prestataire {
    private int id;
    private String name;
    private List<Facture> factures;

    public Prestataire(int id, String name) {
        this.id = id;
        this.name = name;
        this.factures = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    @Override
    public String toString() {
        return "Prestataire{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}