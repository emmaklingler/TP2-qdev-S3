import java.util.ArrayList;
import java.util.List;

public class Etudiant {

    private Identite identite;
    private List<Resultat> resultats;
    private Formation formation;
    private int nbNotes = 0;

    public Etudiant(Identite id, Formation formation1) {
        this.identite = id;
        this.formation = formation1;
        this.resultats = new ArrayList<>();
    }

    public void ajouterNote(String nomMatiere, double note) throws Exception {
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20.");
        }
        if (!formation.getMatiere().containsKey(nomMatiere)) {
            throw new IllegalArgumentException("La matière n'existe pas dans la formation.");
        }

        Resultat resultat = trouverResultat(nomMatiere);
        if (resultat == null) {
            resultat = new Resultat(nomMatiere);
            resultats.add(resultat);
        }
        resultat.ajouterNote(note);
    }

    public double calculerMoyenne(String nomMatiere) throws Exception {
        if (!formation.getMatiere().containsKey(nomMatiere)) {
            throw new IllegalArgumentException("La matière n'existe pas dans la formation.");
        }

        Resultat resultat = trouverResultat(nomMatiere);
        if (resultat == null || resultat.getNotes().isEmpty()) {
            throw new IllegalStateException("Aucune note disponible pour la matière.");
        }

        List<Double> notes = resultat.getNotes();
        double somme = 0;
        for (double note : notes) {
            somme += note;
        }
        return somme / notes.size();
    }

    public double calculerMoyenneGenerale() throws Exception {
        double sommePonderee = 0;
        double totalCoefficients = 0;

        for (Resultat resultat : resultats) {
            String nomMatiere = resultat.getNomMatiere();
            double coeff = formation.getcoeff(nomMatiere);

            double moyenneMatiere = calculerMoyenne(nomMatiere);
            sommePonderee += moyenneMatiere * coeff;
            totalCoefficients += coeff;
        }

        if (totalCoefficients == 0) {
            throw new IllegalStateException("Aucune note enregistrée pour calculer la moyenne générale.");
        }

        return sommePonderee / totalCoefficients;
    }

    public Formation getFormation() {
        return formation;
    }

    // Méthode pour trouver un résultat par nom de matière
    private Resultat trouverResultat(String nomMatiere) {
        for (Resultat resultat : resultats) {
            if (resultat.getNomMatiere().equals(nomMatiere)) {
                return resultat;
            }
        }
        return null;
    }
}

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
