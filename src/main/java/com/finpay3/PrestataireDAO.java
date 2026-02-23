package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PrestataireDAO {
    static Connection conn = DataBaseConnection.getConnection();

    public static void save(String name) {
        String sql = "INSERT INTO prestataires (nom) VALUES (?)";

        try {
            try (
                    PreparedStatement ps = conn.prepareStatement(sql);
            ) {
                ps.setString(1, name);
                ps.executeUpdate();
                findByName(name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void findAll() {
        List<Prestataire> prestataires = new ArrayList();
        String sql = "SELECT * FROM prestataires";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
                Prestataire p = new Prestataire(rs.getInt("id_prestataire"), rs.getString("nom"));
                prestataires.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("=========All Prestataires========= \n");

        for(Prestataire p : prestataires) {
            System.out.println("Prestataire id: " + p.getId());
            System.out.println("Prestataire name: " + p.getName());
            System.out.println("-----------------------------------------");
        }

    }

    public static Prestataire findById(int id) {
        String sql = "SELECT * FROM prestataires WHERE id_prestataire=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("=============Prestataire=============");
                System.out.println("prestataire id: " + rs.getInt("id_prestataire"));
                System.out.println("prestataire name: " + rs.getString("nom"));

                return new Prestataire(
                        rs.getInt("id_prestataire"),
                        rs.getString("nom")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void findByName(String name) {
        String sql = "SELECT * FROM prestataires WHERE nom=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("=============Prestataire added =============");
                System.out.println("prestataire id: " + rs.getInt("id_prestataire"));
                System.out.println("prestataire name: " + rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void update(int id, String name) {
        String sql = "UPDATE prestataires SET nom = ? WHERE id_prestataire =?";

        try {
            try (
                    PreparedStatement ps = conn.prepareStatement(sql);
            ) {
                ps.setString(1, name);
                ps.setInt(2, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM prestataires WHERE id_prestataire=?";

        try {
            try (
                    PreparedStatement ps = conn.prepareStatement(sql);
            ) {
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("prestataire supprimé.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void genererExcelPrestataire(int id) {
        String fileName = "facturesprestatairemois.xlsx";
        String sql = "SELECT f.id_facture, f.date_facture, c.nom AS client_nom, f.montant_total, f.statut FROM factures f JOIN clients c ON f.id_client = c.id_client WHERE f.id_prestataire = ?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
                Workbook workbook = new XSSFWorkbook();
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Sheet sheet = workbook.createSheet("Mes Factures");
            String[] headers = new String[]{"ID", "Date", "Client", "Montant", "Statut"};
            Row headerRow = sheet.createRow(0);

            for(int i = 0; i < headers.length; ++i) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            double totalFacture = (double)0.0F;
            double totalPaye = (double)0.0F;
            double totalEnAttente = (double)0.0F;

            while(rs.next()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue((double)rs.getInt("id_facture"));
                row.createCell(1).setCellValue(rs.getDate("date_facture").toString());
                row.createCell(2).setCellValue(rs.getString("client_nom"));
                double montant = rs.getDouble("montant_total");
                String statut = rs.getString("statut");
                row.createCell(3).setCellValue(montant);
                row.createCell(4).setCellValue(statut);
                totalFacture += montant;
                if ("PAYEE".equalsIgnoreCase(statut)) {
                    totalPaye += montant;
                } else {
                    totalEnAttente += montant;
                }
            }

            ++rowIdx;
            Row r1 = sheet.createRow(rowIdx++);
            r1.createCell(2).setCellValue("TOTAL FACTURÉ :");
            r1.createCell(3).setCellValue(totalFacture);
            Row r2 = sheet.createRow(rowIdx++);
            r2.createCell(2).setCellValue("TOTAL PAYÉ :");
            r2.createCell(3).setCellValue(totalPaye);
            Row r3 = sheet.createRow(rowIdx++);
            r3.createCell(2).setCellValue("TOTAL EN ATTENTE :");
            r3.createCell(3).setCellValue(totalEnAttente);

            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }

            System.out.println(" Le fichier " + fileName + " a été créé à la racine du projet.");
        } catch (IOException | SQLException e) {
            ((Exception)e).printStackTrace();
        }

    }
}

