package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner sc;

    public static void main(String[] args) throws Exception {
        for(char again = 'y'; again == 'y' || again == 'Y'; again = sc.next().charAt(0)) {
            principalMenu();
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> gestionClients();
                case 2 -> gestionFactures();
                case 3 -> gestionPaiements();
                case 4 -> gestionPrestataires();
                case 5 -> Historique();
                case 6 -> rapportMois();
                default -> System.out.println("Invalid choice!");
            }

            System.out.println("Retour au menu principal ? y/n");
        }

    }

    private static void Historique() {
        System.out.println("===== Historique =====");
        System.out.println("\tTotal payments: " + PaymentDAO.getTotalMontantPaye());
        System.out.println("\tTotal commission: " + PaymentDAO.getTotalCommission());
        System.out.println("\tTotal facture payee: " + FactureDAO.getTotalFacturesPayee());
        System.out.println("\tTotal facture non-payee: " + FactureDAO.getTotalFacturesNonPayee());
    }

    public static void gestionClients() {
        System.out.println("===== Gestion des Clients =====");
        System.out.println("1. Ajouter Client");
        System.out.println("2. Afficher Clients");
        System.out.println("3. Metre à jour Client");
        System.out.println("4. Supprimer Client");
        System.out.println("5. Recherche Client par ID");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addClient();
            case 2 -> displayClients();
            case 3 -> updateClient();
            case 4 -> deleteClient();
            case 5 -> searchClientById();
        }

    }

    public static void principalMenu() {
        System.out.println("===== Menu Principal FinPay =====");
        System.out.println("1. Gestion des Clients");
        System.out.println("2. Gestion des Factures");
        System.out.println("3. Gestion des Paiements");
        System.out.println("4. Gestion des Prestataires");
        System.out.println("5. Affichage de l'Historique");
        System.out.println("6. Generer Rapport Excel Monsuel ");
    }

    public static void gestionFactures() {
        System.out.println("===== Gestion des Factures =====");
        System.out.println("1. Ajouter Facture");
        System.out.println("2. Afficher Factures");
        System.out.println("3. Mettre à jour Statut Facture");
        System.out.println("4. Supprimer Facture");
        System.out.println("5. Rechercher Factures par Statut");
        System.out.println("6. Generer PDF d'une Facture ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addFacture();
            case 2 -> displayFactures();
            case 3 -> updateFacture();
            case 4 -> deleteFacture();
            case 5 -> searchFacturesByStatut();
            case 6 -> facturePDF();
        }

    }

    private static void searchFacturesByStatut() {
        System.out.println("Enter statut (NON_PAYEE, PARTIELLE, PAYEE):");
        String statut = sc.nextLine();
        FactureDAO.searchFacturesByStatut(statut);
    }

    public static void gestionPaiements() {
        System.out.println("===== Gestion des Paiements =====");
        System.out.println("1. Ajouter Paiement");
        System.out.println("2. Afficher Paiements");
        System.out.println("3. Mettre à jour Paiement");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addPayment();
            case 2 -> displayPayments();
            case 3 -> updatePayment();
        }

    }

    public static void gestionPrestataires() {
        System.out.println("===== Gestion des Prestataires =====");
        System.out.println("1. Ajouter Prestataire");
        System.out.println("2. Afficher Prestataires");
        System.out.println("3. Mettre à jour Prestataire");
        System.out.println("4. Supprimer Prestataire");
        System.out.println("5. Rechercher Prestataire");
        System.out.println("6. Exporter Factures (EXCEL)");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addPrestataire();
            case 2 -> displayPrestataires();
            case 3 -> updatePrestataire();
            case 4 -> deletePrestataire();
            case 5 -> rechercherPrestataire();
            case 6 -> extraireExcelPrestataire();
        }

    }

    public static void addClient() {
        System.out.println("Enter client name:");
        String name = sc.nextLine();
        ClientDAO.addClient(name);
    }

    public static void displayClients() {
        ClientDAO.getAllClients();
    }

    public static void updateClient() {
        System.out.println("Enter client ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new name:");
        String name = sc.nextLine();
        ClientDAO.updateClient(id, name);
    }

    public static void deleteClient() {
        System.out.println("Enter client ID:");
        int id = sc.nextInt();
        ClientDAO.deleteClient(id);
    }

    public static void searchClientById() {
        System.out.println("Enter client ID:");
        int id = sc.nextInt();
        ClientDAO.findClientById(id);
    }

    public static void addFacture() {
        System.out.println("Enter client ID:");
        int idClient = sc.nextInt();
        System.out.println("Enter prestataire ID:");
        int idPrestataire = sc.nextInt();
        System.out.println("Enter montant:");
        double montant = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter facture date (yyyy-mm-dd):");
        String dateStr = sc.nextLine();
        Date dateFacture = Date.valueOf(dateStr);
        FactureDAO.addFacture(idClient, idPrestataire, montant, Statut.NON_PAYEE, dateFacture);
    }

    public static void displayFactures() {
        FactureDAO.getAllFactures();
    }

    public static void updateFacture() {
        System.out.println("Enter facture ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new statut (NON_PAYEE, PARTIELLE, PAYEE):");
        String statut = sc.nextLine();
        FactureDAO.updateFactureStatut(id, Statut.valueOf(statut));
    }

    public static void deleteFacture() {
        System.out.println("Enter facture ID:");
        int id = sc.nextInt();
        FactureDAO.deleteFacture(id);
    }

    public static void addPayment() {
        System.out.println("Enter facture ID:");
        int idFacture = sc.nextInt();
        System.out.println("Enter montant payé:");
        double montant = sc.nextDouble();
        double commission = montant * 0.02;
        PaymentDAO.addPayment(idFacture, montant, commission);
    }

    public static void displayPayments() {
        PaymentDAO.getAllPayments();
    }

    public static void updatePayment() {
        System.out.println("Enter payment ID:");
        int id = sc.nextInt();
        System.out.println("Enter new montant:");
        double montant = sc.nextDouble();
        double commission = montant * 0.02;
        PaymentDAO.updatePayment(id, montant, commission);
    }

    public static void addPrestataire() {
        System.out.println("Enter prestataire name:");
        String name = sc.nextLine();
        PrestataireDAO.save(name);
    }

    public static void displayPrestataires() {
        PrestataireDAO.findAll();
    }

    public static void updatePrestataire() {
        System.out.println("Enter prestataire ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new name:");
        String name = sc.nextLine();
        PrestataireDAO.update(id, name);
    }

    public static void deletePrestataire() {
        System.out.println("Enter prestataire ID:");
        int id = sc.nextInt();
        PrestataireDAO.delete(id);
    }

    private static void rechercherPrestataire() {
        System.out.println("Enter prestataire ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        PrestataireDAO.findById(id);
    }

    public static void extraireExcelPrestataire() {
        System.out.println("entrez l'id du Prestataire pour l'export:");
        int id = sc.nextInt();
        PrestataireDAO.genererExcelPrestataire(id);
    }

    static {
        sc = new Scanner(System.in);
    }
    public static void facturePDF(){
        System.out.println("Entre ID facture : ");
        int id = sc.nextInt();
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
            document.save("Facture" + facture.getIdFacture() + ".pdf");
            document.close();

            System.out.println("PDF créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void rapportMois () throws Exception{
        String sql = "SELECT DATE_FORMAT(factures.date_facture, '%Y-%m') AS mois,\n" +
                "    prestataires.nom AS Prestataire,\n" +
                "    COUNT(factures.id_facture) AS Nombre_Factures,\n" +
                "    SUM(factures.montant_total) AS Total_Généré,\n" +
                "\tSUM(factures.montant_total)*0.02 AS Total_Commissions\n" +
                "FROM prestataires\n" +
                "JOIN factures \n" +
                "    ON factures.id_prestataire = prestataires.id_prestataire\n" +
                "GROUP BY \n" +
                "    DATE_FORMAT(factures.date_facture, '%Y-%m'),\n" +
                "    prestataires.nom,\n" +
                "    prestataires.id_prestataire\n" +
                "ORDER BY mois;\n";

        Connection con = DataBaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Workbook workbook = null;
        Sheet sheet = null;
        String currentMonth = "";
        int rowNomber = 0;

        while (resultSet.next()){
            String month = resultSet.getString("mois");

            if(!month.equals(currentMonth)){
                if(workbook != null){
                    String oldFilePath ="C:\\Users\\enaa\\Desktop\\FinPay3\\rapport_"+currentMonth+".xlsx";
                    try(FileOutputStream fileOut = new FileOutputStream(oldFilePath)) {
                        workbook.write(fileOut);
                    }
                    workbook.close();
                }
                currentMonth = month;
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Data");
                rowNomber = 0;
                int colExcel = 0;

                Row rowHeader = sheet.createRow(rowNomber++);
                for (int col =2 ; col<= columnCount ; col++ ){
                    rowHeader.createCell(colExcel++).setCellValue(metaData.getColumnName(col));
                }
            }
            int colExcel = 0;
            Row row = sheet.createRow(rowNomber++);
            for (int col = 2; col <= columnCount; col++) {
                row.createCell(colExcel++).setCellValue(resultSet.getString(col));
            }
        }
        if (workbook !=null){
            String filePath = "C:\\Users\\enaa\\Desktop\\FinPay3\\rapport_"+currentMonth+".xlsx";
            try(FileOutputStream fileOut = new FileOutputStream(filePath)){
                workbook.write(fileOut);
            }workbook.close();
        }
    }
}
