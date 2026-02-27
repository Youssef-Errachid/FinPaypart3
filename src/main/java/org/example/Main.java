package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.YearMonth;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingDouble;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public Main() throws SQLException, FileNotFoundException {
    }

    public static void main(String[] args) throws Exception {
        char again = 'y';
        while (again == 'y' || again == 'Y') {
            principalMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1 -> gestionClients();
                case 2 -> gestionFactures();
                case 3 -> gestionPaiements();
                case 4 -> gestionPrestataires();
                case 5 -> Historique();
                case 6 -> rapportMois();
                case 7 -> calculerTotalPrestataire(1);
                default -> System.out.println("Invalid choice!");
            }
            System.out.println("Retour au menu principal ? y/n");
            again = sc.next().charAt(0);
        }
    }
    private static void Historique() {
        System.out.println("       ===== Historique =====");
        System.out.println("\tTotal payments: " + PaymentDAO.getTotalMontantPaye());
        System.out.println("\tTotal commission: " + PaymentDAO.getTotalCommission());
        System.out.println("\tTotal facture payee: " + FactureDAO.getTotalFacturesPayee());
        System.out.println("\tTotal facture non-payee: " + FactureDAO.getTotalFacturesNonPayee());
    }
    public static void gestionClients() {
        System.out.println("===== Gestion des Clients =====");
        System.out.println("1. Ajouter Client");
        System.out.println("2. Afficher Clients");
        System.out.println("3. Mettre à jour Client");
        System.out.println("4. Supprimer Client");
        System.out.println("5. Rechercher Client par ID");
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
    public static void gestionFactures() throws Exception {
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
            case 6 -> genererPDF();
        }
    }
    private static void searchFacturesByStatut() {
        System.out.println("Enter statut (NON_PAYEE, PARTIELLE, PAYEE):");
        String statut = sc.nextLine();
        FactureDAO.searchFacturesByStatut(statut);
    }
    public static void gestionPaiements() throws SQLException {
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

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addPrestataire();
            case 2 -> displayPrestataires();
            case 3 -> updatePrestataire();
            case 4 -> deletePrestataire();
            case 5 -> rechercherPrestataire();

        }
    }
    public static void addClient(){
        System.out.println("Enter client name:");
        String name = sc.nextLine();
        ClientDAO.addClient(name);
    }
    public static void displayClients(){
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
    public static void deleteClient(){
        System.out.println("Enter client ID:");
        int id = sc.nextInt();
        ClientDAO.deleteClient(id);
    }
    public static void searchClientById(){
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
    public static void addPayment() throws SQLException {
        System.out.println("Enter facture ID:");
        int idFacture = sc.nextInt();
        sc.nextLine();
        double rest = Objects.requireNonNull(FactureDAO.findFactureById(idFacture)).getRestPayer();
        Statut status = Objects.requireNonNull(FactureDAO.findFactureById(idFacture)).getStatut();
        if (status.equals(Statut.PAYEE) || rest==0.0){
            System.out.println("Facture Déja Payée !!");
            return;
        } else if (status.equals(Statut.PARTIELLE)) {
            System.out.println("Reste a Payer : "+rest);
        }
        System.out.println("Entre le type de paiements : ");
        System.out.println("T pour Paiement Total et ");
        System.out.println("P pour Paiement Partielle");
        String t = sc.nextLine().toUpperCase();
        if (t.equals("T")) {
            status = Statut.PAYEE;
        } else if(t.equals("P")) {
            status = Statut.PARTIELLE;
        }else { System.out.println("Input Invalid");}
        System.out.println("Enter montant payé:");
        double montant = sc.nextDouble();
        sc.nextLine();
        double commission = montant*0.02;
        PaymentDAO.addPayment(idFacture, montant, commission,status);
    }
    public static void displayPayments() {
        PaymentDAO.getAllPayments();
    }
    public static void updatePayment() {
        System.out.println("Enter payment ID:");
        int id = sc.nextInt();
        System.out.println("Enter new montant:");
        double montant = sc.nextDouble();
        System.out.println("Enter new commission:");
        double commission = sc.nextDouble();
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
    public static void genererPDF(){
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
            contentStream.showText("Montant net : " + (facture.getMontantTotal() - (facture.getMontantTotal() * 0.02)));
            contentStream.newLine();
            contentStream.showText("Status : " + facture.getStatut());
            contentStream.newLine();
            contentStream.showText("Merci de votre confiance !");
            contentStream.endText();

            contentStream.close();
            document.save("Facture" + facture.getIdFacture() + ".pdf");
            document.close();



//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
//            contentStream.newLineAtOffset(100, 700);
//            contentStream.showText("----FinPay APP----");
//            contentStream.showText("Facture ID : "+facture.getIdFacture());
//            contentStream.showText("Prestataire Nom : "+facture.getPrestataire().getName()+" , Prestataire ID : "+facture.getPrestataire().getId());
//            contentStream.showText("Client Nom : "+facture.getClient().getNom()+" , Prestataire ID : "+facture.getClient().getIdClient());
//            contentStream.showText("Date : "+facture.getDateFacture());
//            contentStream.showText("Commission Appliquée : "+(facture.getMontantTotal()*0.02));
//            contentStream.showText("Facture Montant : "+facture.getMontantTotal());
//            contentStream.showText("Status : "+facture.getStatut());
//            contentStream.endText();
//            contentStream.close();
//            document.save("Facture"+facture.getIdFacture()+".pdf");
            System.out.println("PDF créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void adminExcel() throws SQLException, IOException {
////        List<Facture> factures = FactureDAO.getAllFactures();
////        Map<Objects, Float> prestataireOrder = factures.stream().collect
////                (Collectors.groupingBy(f -> f.getPrestataire(), Collectors.reducing(0, g->g.)));
//
//        Map<Prestataire, Double> PrestataireTotal = factures.stream().collect
//                (Collectors.groupingBy(Facture::getPrestataire, summingDouble(Facture::getMontantTotal)));
//        Map<Prestataire, Long> PrestataireOrders = factures.stream().collect
//                (Collectors.groupingBy(Facture::getPrestataire, counting()));
//
//        Map<Prestataire, Map<YearMonth, Map<Long, Double>>> result = factures.stream()
//                .collect(Collectors.groupingBy(
//                                Facture::getPrestataire,
//                                Collectors.groupingBy(
//                                        f -> YearMonth.from((TemporalAccessor) f.getDateFacture()), Collectors.teeing(counting(), summingDouble(Facture::getMontantTotal), (count, sum) -> "Nombre: " + count + ", Total: " + sum))
//
//                        )
//                );
//
//        Map<Prestataire, Map<YearMonth, Long>> countingM = factures.stream()
//                .collect(Collectors.groupingBy(
//                                Facture::getPrestataire,
//                                Collectors.groupingBy(
//                                        f -> YearMonth.from((TemporalAccessor) f.getDateFacture()), counting())
//                        )
//                );
//        Map<Prestataire, Map<YearMonth, Double>> SumM = factures.stream()
//                .collect(Collectors.groupingBy(
//                                Facture::getPrestataire,
//                                Collectors.groupingBy(
//                                        f -> YearMonth.from((TemporalAccessor) f.getDateFacture()), summingDouble(Facture::getMontantTotal))
//                        )
//                );
//
//
//        Map<Prestataire, Map<YearMonth, Object>> results =
//                factures.stream()
//                        .collect(Collectors.groupingBy(
//                                        Facture::getPrestataire,
//                                        Collectors.groupingBy(
//                                                f -> YearMonth.from((TemporalAccessor) f.getDateFacture()),
//                                                Collectors.teeing(
//                                                        counting(),
//                                                        summingDouble(Facture::getMontantTotal),
//                                                        (count, sum) -> "Nombre: " + count + ", Total: " + sum)
//                                        )
//                                )
//                        );

        String sql = "select prestataires.nom , SUM(factures.montant_total) as Total \n" +
                "from prestataires Join factures  \n" +
                "group by prestataires.nom;";


        String sqll= "SELECT prestataires.nom, prestataires.id_prestataire,\n" +
                "    COUNT(factures.id_facture) AS Nombre_Factures,\n" +
                "    SUM(factures.montant_total) AS Total\n" +
                "FROM prestataires JOIN factures ON factures.id_prestataire = prestataires.id_prestataire\n" +
                "GROUP BY prestataires.nom , prestataires.id_prestataire;";

//        List<Admin> admins = new ArrayList<>();
//
//        try (Connection conn = databaseConnection.getConnection();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(sqll)) {
//            while (rs.next()) {
//                admins.add(new Admin(
//                        rs.getString("nom"),
//                        rs.getInt("id_prestataire"),
//                        rs.getInt("Nombre_Factures"),
//                        rs.getDouble("Total")
//                ));
//            };
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }




//        ResultSet resultSet = FactureDAO.getAllFactures();
        Connection conn = databaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sqll);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colmunCount = metaData.getColumnCount();
        Row headerRow = sheet.createRow(0);
//
//            // 🔹 Création des en-têtes
//            Row headerRow = sheet.createRow(0);
            for(int col = 1; col <= colmunCount; col++) {
                headerRow.createCell(col - 1)
                        .setCellValue(metaData.getColumnName(col));
            }

//            // 🔹 Remplissage des données
            int rowNumber = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNumber++);
                for (int col = 1; col <= colmunCount; col++) {
                    row.createCell(col - 1)
                            .setCellValue(resultSet.getString(col));
                }
            }

//            // 🔹 Ajuster largeur colonnes
            for (int i = 0; i < colmunCount; i++) {
                sheet.autoSizeColumn(i);
            }

//    // 🔹 Écriture du fichier
        String filePath = "";
        FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

//

    }
    public static void excelA() throws SQLException, IOException {

        String sqll = "SELECT prestataires.nom, prestataires.id_prestataire,\n" +
                "    COUNT(factures.id_facture) AS Nombre_Factures,\n" +
                "    SUM(factures.montant_total) AS Total\n" +
                "FROM prestataires JOIN factures ON factures.id_prestataire = prestataires.id_prestataire\n" +
                "GROUP BY prestataires.nom , prestataires.id_prestataire;";

        String sql = "SELECT DATE_FORMAT(factures.date_facture, '%Y-%m') AS mois,\n" +
                "    prestataires.nom,\n" +
                "    prestataires.id_prestataire,\n" +
                "    COUNT(factures.id_facture) AS Nombre_Factures,\n" +
                "    SUM(factures.montant_total) AS Total\n" +
                "FROM prestataires\n" +
                "JOIN factures \n" +
                "    ON factures.id_prestataire = prestataires.id_prestataire\n" +
                "GROUP BY \n" +
                "    DATE_FORMAT(factures.date_facture, '%Y-%m'),\n" +
                "    prestataires.nom,\n" +
                "    prestataires.id_prestataire\n" +
                "ORDER BY mois;\n";

        Connection conn = databaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sqll);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colmunCount = metaData.getColumnCount();
        Row headerRow = sheet.createRow(0);

//            Row headerRow = sheet.createRow(0);
        for (int col = 1; col <= colmunCount; col++) {
            headerRow.createCell(col - 1)
                    .setCellValue(metaData.getColumnName(col));
        }

//            // 🔹 Remplissage des données
        int rowNumber = 1;
        while (resultSet.next()) {
            Row row = sheet.createRow(rowNumber++);
            for (int col = 1; col <= colmunCount; col++) {
                row.createCell(col - 1)
                        .setCellValue(resultSet.getString(col));
            }
        }

//     🔹 Ajuster largeur colonnes
        for (int i = 0; i < colmunCount; i++) {
            sheet.autoSizeColumn(i);
        }

//     🔹 Écriture du fichier
        String filePath = "C:\\Users\\enaaj\\OneDrive\\Desktop\\Sprint1\\FinPayM\\rapport.xlsx";
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
    public static void Excel() throws Exception{


        String sql = "SELECT DATE_FORMAT(factures.date_facture, '%Y-%m') AS mois,\n" +
                "    prestataires.nom,\n" +
                "    prestataires.id_prestataire,\n" +
                "    COUNT(factures.id_facture) AS Nombre_Factures,\n" +
                "    SUM(factures.montant_total) AS Total\n" +
                "FROM prestataires\n" +
                "JOIN factures \n" +
                "    ON factures.id_prestataire = prestataires.id_prestataire\n" +
                "GROUP BY \n" +
                "    DATE_FORMAT(factures.date_facture, '%Y-%m'),\n" +
                "    prestataires.nom,\n" +
                "    prestataires.id_prestataire\n" +
                "ORDER BY mois;\n";


        Connection conn = databaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        Workbook workbook = null;
        Sheet sheet = null;

        String currentMonth = "";
        int rowNumber = 0;

        while (resultSet.next()) {

            String month = resultSet.getString("mois");

            // 🔹 Si nouveau mois → sauvegarder ancien fichier
            if (!month.equals(currentMonth)) {

                // Sauvegarder le précédent fichier
                if (workbook != null) {
                    String oldFilePath = "C:\\Users\\enaaj\\OneDrive\\Desktop\\Sprint1\\FinPayM\\rapport_"
                            + currentMonth + ".xlsx";

                    try (FileOutputStream fileOut = new FileOutputStream(oldFilePath)) {
                        workbook.write(fileOut);
                    }
                    workbook.close();
                }

                // 🔹 Nouveau fichier pour le nouveau mois
                currentMonth = month;
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Data");
                rowNumber = 0;

                // Header
                Row headerRow = sheet.createRow(rowNumber++);
                for (int col = 1; col <= columnCount; col++) {
                    headerRow.createCell(col-1)
                            .setCellValue(metaData.getColumnName(col));
                }
            }

            // 🔹 Ajouter ligne
            Row row = sheet.createRow(rowNumber++);
            for (int col = 1; col <= columnCount; col++) {
                row.createCell(col-1)
                        .setCellValue(resultSet.getString(col));
            }
        }

// 🔹 Sauvegarder le dernier mois
        if (workbook != null) {
            String filePath = "C:\\Users\\enaaj\\OneDrive\\Desktop\\Sprint1\\FinPayM\\rapport_"
                    + currentMonth + ".xlsx";

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            workbook.close();
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
                "    prestataires.nom\n" +
                "ORDER BY mois;";

        Connection con = databaseConnection.getConnection();
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
                    String oldFilePath ="C:\\Users\\enaaj\\OneDrive\\Desktop\\Sprint1\\FinPayM\\rapport_"+currentMonth+".xlsx";
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
            String filePath = "C:\\Users\\enaaj\\OneDrive\\Desktop\\Sprint1\\FinPayM\\rapport_"+currentMonth+".xlsx";
            try(FileOutputStream fileOut = new FileOutputStream(filePath)){
                workbook.write(fileOut);
            }workbook.close();
        }
    }
    public static double calculerTotalPrestataire(int id){
        List<Facture> factures = FactureDAO.getAllFactures();
        double total = factures.stream().filter(p-> p.getPrestataire().getId()==id)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        System.out.println(total);
        return total;
    }

    public static void rapportMois(String currentMonth) {
        String sql = "SELECT DATE_FORMAT(f.date_facture, '%Y-%m') AS mois, " +
                "p.nom AS Prestataire, " +
                "COUNT(f.id_facture) AS Nombre_Factures, " +
                "SUM(f.montant_total) AS Total_Généré, " +
                "SUM(f.montant_total) * 0.02 AS Total_Commissions " +
                "FROM prestataires p " +
                "JOIN factures f ON f.id_prestataire = p.id_prestataire " +
                "GROUP BY DATE_FORMAT(f.date_facture, '%Y-%m'), p.nom, p.id_prestataire " +
                "ORDER BY mois;";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Workbook workbook = null;
            Sheet sheet = null;
            int rowNumber = 0;

            while (rs.next()) {
                String mois = rs.getString("mois");

                if (!mois.equals(currentMonth)) {

                    if (workbook != null) {
                        String oldFileName = "rapport_" + currentMonth + ".xlsx";
                        try (FileOutputStream fileOut = new FileOutputStream(oldFileName)) {
                            workbook.write(fileOut);
                        }
                        workbook.close();
                    }

                    currentMonth = mois;
                    workbook = new XSSFWorkbook();
                    sheet = workbook.createSheet("Rapport " + mois);
                    rowNumber = 0;

                    String[] headers = {"Prestataire", "Nombre Factures", "Total Généré", "Total Commissions"};
                    Row headerRow = sheet.createRow(rowNumber++);
                    for (int i = 0; i < headers.length; i++) {
                        headerRow.createCell(i).setCellValue(headers[i]);
                    }
                }

                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(rs.getString("Prestataire"));
                row.createCell(1).setCellValue(rs.getInt("Nombre_Factures"));
                row.createCell(2).setCellValue(rs.getDouble("Total_Généré"));
                row.createCell(3).setCellValue(rs.getDouble("Total_Commissions"));
            }

            if (workbook != null) {
                String fileName = "rapport_" + currentMonth + ".xlsx";
                try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                    workbook.write(fileOut);
                }
                workbook.close();
            }

            System.out.println("Rapports générés avec succès.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}