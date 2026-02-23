package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    static Connection conn = DataBaseConnection.getConnection();

    public static void addPayment(int idFacture, double montantPaye, double commission) {
        String sql = "INSERT INTO paiements (id_facture, montant_paye, commission_finpay) VALUES (?, ?, ?)";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql, 1);
        ) {
            pstmt.setInt(1, idFacture);
            pstmt.setDouble(2, montantPaye);
            pstmt.setDouble(3, commission);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int idPayment = resultSet.getInt(1);
                        Facture facture = FactureDAO.findFactureById(idFacture);
                        double resteAPayer = Facture.getMontantTotal() - montantPaye;
                        Date datePaiement = facture.getDateFacture();
                        System.out.println("Payment added successfully!");
                        GenerationDunRecuDePaiement(idPayment, idFacture, datePaiement, montantPaye, resteAPayer);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
        }

    }

    private static void GenerationDunRecuDePaiement(int idPayment, int idFacture, Date date, double montantPaye, double resteAPayer) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("recupaiementID_" + idPayment + ".pdf"));
            document.open();
            Font titleFont = new Font(FontFamily.HELVETICA, 18.0F, 1);
            Paragraph title = new Paragraph("Reçu de Paiement", titleFont);
            title.setAlignment(1);
            document.add(title);
            document.add(new Paragraph(" "));
            Font normalFont = new Font(FontFamily.HELVETICA, 12.0F, 0);
            document.add(new Paragraph("Numéro du paiement : " + idPayment, normalFont));
            document.add(new Paragraph("Numéro de la facture : " + idFacture, normalFont));
            document.add(new Paragraph("Date du paiement : " + date.toString(), normalFont));
            document.add(new Paragraph("Montant payé : " + montantPaye + " MAD", normalFont));
            document.add(new Paragraph("Reste à payer : " + resteAPayer + " MAD", normalFont));
            document.add(new Paragraph(" "));
            document.close();
            System.out.println("Done! recupaiementID_" + idPayment + ".pdf created.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getAllPayments() {
        List<Payment> payments = new ArrayList();
        String sql = "SELECT * FROM paiements";

        try (
                Statement stm = conn.createStatement();
                ResultSet resultSet = stm.executeQuery(sql);
        ) {
            while(resultSet.next()) {
                int id_paiement = resultSet.getInt("id_paiement");
                int id_facture = resultSet.getInt("id_facture");
                double montant_paye = resultSet.getDouble("montant_paye");
                double commission_finpay = resultSet.getDouble("commission_finpay");
                Timestamp date_paiement = resultSet.getTimestamp("date_paiement");
                Facture facture = FactureDAO.findFactureById(id_facture);
                Payment payment = new Payment(id_paiement, facture, date_paiement, montant_paye, commission_finpay);
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying payments: " + e.getMessage());
        }

        System.out.println("=========All Payments========= \n");

        for(Payment p : payments) {
            System.out.println("Payment id: " + p.getId());
            System.out.println("Amount operation: " + p.getMontanpaye());
            System.out.println("Date operation: " + String.valueOf(Payment.getDate()));
            System.out.println("Commission= " + p.getCommission());
            System.out.println("-----------------------------------------");
        }

    }

    public static void updatePayment(int idPaiement, double newMontant, double newCommission) {
        String sql = "UPDATE paiements SET montant_paye = ?, commission_finpay = ? WHERE id_paiement = ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setDouble(1, newMontant);
            pstmt.setDouble(2, newCommission);
            pstmt.setInt(3, idPaiement);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error updating payment: " + e.getMessage());
        }

    }

    public static double getTotalMontantPaye() {
        String sql = "SELECT SUM(montant_paye) AS total FROM paiements";

        try {
            try (
                    Statement st = conn.createStatement();
            ) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    if (rs.next()) {
                        double var4 = rs.getDouble("total");
                        return var4;
                    } else {
                        return (double)0.0F;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return (double)0.0F;
        }
    }

    public static double getTotalCommission() {
        String sql = "SELECT SUM(commission_finpay) AS total FROM paiements";

        try {
            try (
                    Statement st = conn.createStatement();
            ) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    if (rs.next()) {
                        double var4 = rs.getDouble("total");
                        return var4;
                    } else {
                        return (double)0.0F;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return (double)0.0F;
        }
    }
}

