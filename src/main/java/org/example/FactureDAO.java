import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {
    public static void addFacture(int idClient, int idPrestataire, double montant, Statut statut, Date dateFacture) {
        String sql = "INSERT INTO factures (id_client, id_prestataire, montant_total, statut, date_facture) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idClient);
            ps.setInt(2, idPrestataire);
            ps.setDouble(3, montant);
            ps.setString(4, statut.name());
            ps.setDate(5, dateFacture);
            ps.executeUpdate();
            System.out.println("Facture added successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void getAllFactures() {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures";
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                factures.add(new Facture(
                        rs.getInt("id_facture"),
                        null, null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("=========All Factures========= \n");
        for (Facture f : factures) {
            System.out.println("Facture id: " + f.getIdFacture());
            System.out.println("Amount operation: " + f.getMontantTotal());
            System.out.println("Creation date: " + f.getDateCreation());
            System.out.println("Invoice date: " + f.getDateFacture());
            System.out.println("Status Payment: " + f.getStatut());
            System.out.println("-----------------------------------------");
        }
    }

    public static void updateFactureStatut(int id, Statut newStatut) {
        String sql = "UPDATE factures SET statut=? WHERE id_facture=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatut.name());
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Facture statut updated successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void deleteFacture(int id) {
        String sql = "DELETE FROM factures WHERE id_facture=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Facture deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchFacturesByStatut(String statut) {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures WHERE statut=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                factures.add(new Facture(
                        rs.getInt("id_facture"), null, null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation") ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("=========Factures========= \n");
        for (Facture f : factures) {
            System.out.println("facture id: " +f.getIdFacture());
            System.out.println("montant total: " +f.getMontantTotal());
            System.out.println("facture status: " +f.getStatut());
            System.out.println("Date creation: " +f.getDateCreation());
            System.out.println("-----------------------------------------");
        }
    }
    public static Facture findFactureById(int id) {
        String sql = "SELECT * FROM factures WHERE id_facture=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Facture(
                        rs.getInt("id_facture"),
                        null,
                        null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static double getTotalFacturesPayee() {
        String sql = "SELECT SUM(montant_total) AS total FROM factures WHERE statut='PAYEE'";
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

    public static double getTotalFacturesNonPayee() {
        String sql = "SELECT SUM(montant_total) AS total FROM factures WHERE statut='NON_PAYEE'";
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