package com.finpay3;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FactureDAO {
    static Connection conn = DataBaseConnection.getConnection();

    public static void addFacture(int idClient, int idPrestataire, double montant, Statut statut, Date dateFacture) {
        String sql = "INSERT INTO factures (id_client, id_prestataire, montant_total, statut, date_facture) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, idClient);
            ps.setInt(2, idPrestataire);
            ps.setDouble(3, montant);
            ps.setString(4, statut.name());
            ps.setDate(5, dateFacture);
            ps.executeUpdate();
            System.out.println("Facture added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getAllFactures() {
        List<Facture> factures = new ArrayList();
        String sql = "SELECT * FROM factures";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
        ) {
            while(rs.next()) {
                factures.add(new Facture(rs.getInt("id_facture"), (Client)null, (Prestataire)null, rs.getDouble("montant_total"), Statut.valueOf(rs.getString("statut")), rs.getDate("date_facture"), rs.getTimestamp("date_creation")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("=========All Factures========= \n");

        for(Facture f : factures) {
            System.out.println("Facture id: " + f.getIdFacture());
            System.out.println("Amount operation: " + Facture.getMontantTotal());
            System.out.println("Creation date: " + String.valueOf(f.getDateCreation()));
            System.out.println("Invoice date: " + String.valueOf(f.getDateFacture()));
            System.out.println("Status Payment: " + String.valueOf(f.getStatut()));
            System.out.println("-----------------------------------------");
        }

    }

    public static void updateFactureStatut(int id, Statut newStatut) {
        String sql = "UPDATE factures SET statut=? WHERE id_facture=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, newStatut.name());
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Facture statut updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteFacture(int id) {
        String sql = "DELETE FROM factures WHERE id_facture=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Facture deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void searchFacturesByStatut(String statut) {
        List<Facture> factures = new ArrayList();
        String sql = "SELECT * FROM factures WHERE statut=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                factures.add(new Facture(rs.getInt("id_facture"), (Client)null, (Prestataire)null, rs.getDouble("montant_total"), Statut.valueOf(rs.getString("statut")), rs.getDate("date_facture"), rs.getTimestamp("date_creation")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("=========Factures========= \n");

        for(Facture f : factures) {
            System.out.println("facture id: " + f.getIdFacture());
            System.out.println("montant total: " + Facture.getMontantTotal());
            System.out.println("facture status: " + String.valueOf(f.getStatut()));
            System.out.println("Date creation: " + String.valueOf(f.getDateCreation()));
            System.out.println("-----------------------------------------");
        }

    }

    public static Facture findFactureById(int id) {
        String sql = "SELECT * FROM factures WHERE id_facture=?";

        try {
            Facture var5;
            try (
                    Connection conn = DataBaseConnection.getConnection()) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        return null;
                    }

                    var5 = new Facture(rs.getInt("id_facture"),
                            ClientDAO.findClientById(rs.getInt("id_client")),
                            PrestataireDAO.findById(rs.getInt("id_prestataire")),
                            rs.getDouble("montant_total"),
                            Statut.valueOf(rs.getString("statut")),
                            rs.getDate("date_facture"),
                            rs.getTimestamp("date_creation"));
                }
            }

            return var5;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getTotalFacturesPayee() {
        String sql = "SELECT SUM(montant_total) AS total FROM factures WHERE statut='PAYEE'";

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

    public static double getTotalFacturesNonPayee() {
        String sql = "SELECT SUM(montant_total) AS total FROM factures WHERE statut='NON_PAYEE'";

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
    public static String factureName(int id){
        return "facture_"+ id + ".pdf";
    }
    public static void facturePDF(int id){
        Facture facture = FactureDAO.findFactureById(id);
        System.out.println(facture);
        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float y = 700;
            float leading = 20f;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 22);
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("----FinPay APP----");

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(leading);

            contentStream.newLine();
            contentStream.showText("Facture ID : " + facture.getIdFacture());
            contentStream.newLine();
            contentStream.showText("Date : " + facture.getDateFacture());
            contentStream.newLine();
            contentStream.showText("Client : " + facture.getClient().getNom() + " (ID: " + facture.getClient().getIdClient() + ")");
            contentStream.newLine();
            contentStream.showText("Prestataire : " + facture.getPrestataire().getName() + " (ID: " + facture.getPrestataire().getId() + ")");
            contentStream.newLine();
            contentStream.showText("Montant total : " + facture.getMontantTotal());
            contentStream.newLine();
            contentStream.showText("Commission : " + (facture.getMontantTotal() * 0.02));
            contentStream.newLine();
            contentStream.showText("Status : " + facture.getStatut());
            contentStream.newLine();
            contentStream.showText("Merci de votre confiance !");
            contentStream.endText();

            contentStream.close();
//            document.save(factureName(facture.getIdFacture()));
            document.save("Facture"+ facture.getIdFacture() + ".pdf");
            document.close();

            System.out.println("PDF créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
