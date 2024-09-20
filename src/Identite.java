public class Identite {

    private String NIP;
    private String nom;
    private String prenom;

    public Identite(String NumIdP, String n, String p){
        this.NIP = NumIdP;
        this.nom = n;
        this.prenom = p;
    }

    public String getNIP(){
        return NIP;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }
}
