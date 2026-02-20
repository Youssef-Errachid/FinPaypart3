public class Client {
    private int idClient;
    private String nom;

    public Client(int idClient, String nom) {
        this.idClient = idClient;
        this.nom = nom;
    }

    public int getIdClient() { return idClient; }
    public String getNom() { return nom; }

    @Override
    public String toString() {
        return "Client{id=" + idClient + ", nom='" + nom + "'}";
    }
}