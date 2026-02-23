package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Client {
    private int idClient;
    private String nom;

    public Client(int idClient, String nom) {
        this.idClient = idClient;
        this.nom = nom;
    }

    public int getIdClient() {
        return this.idClient;
    }

    public String getNom() {
        return this.nom;
    }

    public String toString() {
        return "Client{id=" + this.idClient + ", nom='" + this.nom + "'}";
    }
}

