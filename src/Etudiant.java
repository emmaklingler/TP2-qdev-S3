import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Etudiant {
    private Identite identite;
    private HashMap<String, List<Double>> notesParMatiere; // map of subject names and their respective grades
    private Formation formation;

    public Etudiant(Identite id, Formation formation) {
        this.identite = id;
        this.formation = formation;
        this.notesParMatiere = new HashMap<>();
    }

    public void ajouterNote(String nomMatiere, double note) throws Exception {
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20.");
        }
        if (!formation.getMatiere().containsKey(nomMatiere)) {
            throw new IllegalArgumentException("La matière n'existe pas dans la formation.");
        }

        notesParMatiere.computeIfAbsent(nomMatiere, k -> new ArrayList<>()).add(note);
    }

    public double calculerMoyenne(String nomMatiere) throws Exception {
        if (!notesParMatiere.containsKey(nomMatiere) || notesParMatiere.get(nomMatiere).isEmpty()) {
            throw new IllegalStateException("Aucune note disponible pour la matière.");
        }

        List<Double> notes = notesParMatiere.get(nomMatiere);
        double somme = notes.stream().mapToDouble(Double::doubleValue).sum();
        return somme / notes.size();
    }

    public double calculerMoyenneGenerale() throws Exception {
        double somme = 0;
        double poidsTotal = 0;

        for (String matiere : formation.getMatiere().keySet()) {
            try {
                double coef = formation.getMatiere().get(matiere);
                double moyenne = calculerMoyenne(matiere);
                somme += moyenne * coef;
                poidsTotal += coef;
            } catch (IllegalStateException e) {
                // Ignore les matières sans notes
            }
        }

        if (poidsTotal == 0) {
            throw new IllegalStateException("Aucune note disponible pour calculer la moyenne générale.");
        }

        return Math.round((somme / poidsTotal) * 100.0) / 100.0; // Arrondi à 2 décimales
    }


    public Formation getFormation() {
        return formation;
    }

    public Identite getIdentite() {
        return identite;
    }
}
