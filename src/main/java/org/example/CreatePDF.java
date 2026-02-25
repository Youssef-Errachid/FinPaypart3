package org.example;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class CreatePDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream =
                    new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Bonjour, ceci est mon premier PDF !");
            contentStream.endText();

            contentStream.close();

            document.save("mon_fichier.pdf");
            System.out.println("PDF créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
