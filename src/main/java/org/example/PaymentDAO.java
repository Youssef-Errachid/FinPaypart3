package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {


    public static void addPayment(int idFacture, double montantPaye, double commission, Statut status) throws SQLException{
        String sql = "INSERT INTO paiements (id_facture, montant_paye, commission_finpay) VALUES (?, ?, ?)";
        FactureDAO.updateFactureStatut(idFacture,status);

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idFacture);
            pstmt.setDouble(2, montantPaye);
            pstmt.setDouble(3, commission);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
        }
    }

    public static void getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM paiements";

        try (Connection connection = databaseConnection.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(sql)) {

            while (resultSet.next()) {
                int id_paiement = resultSet.getInt("id_paiement");
                int id_facture = resultSet.getInt("id_facture");
                double montant_paye = resultSet.getDouble("montant_paye");
                double commission_finpay = resultSet.getDouble("commission_finpay");
                Timestamp date_paiement = resultSet.getTimestamp("date_paiement");
                Facture facture =  FactureDAO.findFactureById(id_facture);
                Payment payment = new Payment(id_paiement, facture, date_paiement, montant_paye, commission_finpay);
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying payments: " + e.getMessage());
        }

        System.out.println("=========All Payments========= \n");
        for(Payment p: payments){
            System.out.println("Payment id: " + p.getId() );
            System.out.println("Amount operation: " + p.getMontanpaye());
            System.out.println("Date operation: " + p.getDate());
            System.out.println("Commission= "+p.getCommission());
            System.out.println("-----------------------------------------");
        }
    }


    public static void updatePayment(int idPaiement, double newMontant, double newCommission) {
        String sql = "UPDATE paiements SET montant_paye = ?, commission_finpay = ? WHERE id_paiement = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

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
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static double getTotalCommission() {
        String sql = "SELECT SUM(commission_finpay) AS total FROM paiements";
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


}