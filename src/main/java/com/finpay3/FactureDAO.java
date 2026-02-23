package com.finpay3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
