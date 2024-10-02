import java.util.ArrayList;
import java.util.List;

public class Etudiant {

    private Identite identite;
    private List<String> matieres;          // Liste des matières
    private List<List<Double>> notesParMatiere;  // Liste des notes pour chaque matière
    private Formation formation;

    public Etudiant(Identite id, Formation formation1) {
        this.identite = id;
        this.formation = formation1;
        this.matieres = new ArrayList<>();
        this.notesParMatiere = new ArrayList<>();
    }

    public void ajouterNote(String nomMatiere, double note) throws Exception {
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20.");
        }
        if (!formation.getMatiere().containsKey(nomMatiere)) {
            throw new IllegalArgumentException("La matière n'existe pas dans la formation.");
        }

        int indexMatiere = matieres.indexOf(nomMatiere);
        if (indexMatiere == -1) {
            // Si la matière n'existe pas encore, on l'ajoute
            matieres.add(nomMatiere);
            List<Double> notes = new ArrayList<>();
            notes.add(note);
            notesParMatiere.add(notes);
        } else {
            // Si la matière existe, on ajoute la note à la liste correspondante
            notesParMatiere.get(indexMatiere).add(note);
        }
    }

    public double calculerMoyenne(String nomMatiere) throws Exception {
        int indexMatiere = matieres.indexOf(nomMatiere);
        if (indexMatiere == -1 || notesParMatiere.get(indexMatiere).isEmpty()) {
            throw new IllegalStateException("Aucune note disponible pour la matière.");
        }

        List<Double> notes = notesParMatiere.get(indexMatiere);
        double somme = 0;
        for (double note : notes) {
            somme += note;
        }
        return somme / notes.size(); // Calcul de la moyenne
    }

    public double calculerMoyenneGenerale() throws Exception {
        double sommePonderee = 0;
        double totalCoefficients = 0;

        for (int i = 0; i < matieres.size(); i++) {
            String nomMatiere = matieres.get(i);
            double coeff = formation.getcoeff(nomMatiere);

            if (!notesParMatiere.get(i).isEmpty()) {
                double moyenneMatiere = calculerMoyenne(nomMatiere);
                sommePonderee += moyenneMatiere * coeff;
                totalCoefficients += coeff;
            }
        }

        if (totalCoefficients == 0) {
            throw new IllegalStateException("Aucune note enregistrée pour calculer la moyenne générale.");
        }

        return sommePonderee / totalCoefficients; // Calcul de la moyenne générale
    }

    public Formation getFormation() {
        return formation;
    }
}
