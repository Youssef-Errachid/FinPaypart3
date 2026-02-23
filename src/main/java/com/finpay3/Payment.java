package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Date;

public class Payment {
    private int id;
    private static Date date;
    private double montanpaye;
    private double commission;
    private Facture facture;

    public Payment(int paymentID, Facture facture, Date date, double montanpaye, double commission) {
        this.id = paymentID;
        Payment.date = date;
        this.montanpaye = montanpaye;
        this.commission = commission;
        this.facture = facture;
    }

    public Facture getFacture() {
        return this.facture;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Payment.date = date;
    }

    public double getMontanpaye() {
        return this.montanpaye;
    }

    public void setMontanpaye(double montanpaye) {
        this.montanpaye = montanpaye;
    }

    public double getCommission() {
        return this.commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String toString() {
        int var10000 = this.id;
        return "Payment{paymentID=" + var10000 + ", date=" + String.valueOf(date) + ", montanpaye=" + this.montanpaye + ", commission=" + this.commission + "}";
    }
}

