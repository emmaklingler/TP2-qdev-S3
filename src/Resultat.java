import java.util.ArrayList;
import java.util.List;

class Resultat {
    private String nomMatiere;
    private List<Double> notes;

    public Resultat(String nomMatiere) {
        this.nomMatiere = nomMatiere;
        this.notes = new ArrayList<>();
    }

    public void ajouterNote(double note) {
        notes.add(note);
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public List<Double> getNotes() {
        return notes;
    }
}
