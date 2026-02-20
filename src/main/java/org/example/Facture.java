import java.sql.Date;
import java.sql.Timestamp;

public class Facture {
    private int idFacture;
    private Client client;
    private Prestataire prestataire;
    private double montantTotal;
    private Statut statut;
    private Date dateFacture;
    private Timestamp dateCreation;

    public Facture(int idFacture, Client client, Prestataire prestataire,
                   double montantTotal, Statut statut, Date dateFacture, Timestamp dateCreation) {
        this.idFacture = idFacture;
        this.client = client;
        this.prestataire = prestataire;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.dateFacture = dateFacture;
        this.dateCreation = dateCreation;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Facture{id=" + idFacture + ", client=" + client +
                ", prestataire=" + prestataire + ", montant=" + montantTotal +
                ", statut=" + statut + ", dateFacture=" + dateFacture +
                ", dateCreation=" + dateCreation + "}";
    }
}