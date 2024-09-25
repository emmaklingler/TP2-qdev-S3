import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Etudiant {

    private Identite identite;
    private Map<String, List<Double>> resultats;
    private Formation formation;
    private int nbNotes = 0;

    public Etudiant(Identite id, Formation formation1) {
        this.identite = id;
        this.formation = formation1;
        this.resultats = new HashMap<>();
    }

    public void ajouterNote(String nomMatiere, double note) throws Exception {
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20.");
        }
        if (!formation.getMatiere().containsKey(nomMatiere)) {
            throw new IllegalArgumentException("La matière n'existe pas dans la formation.");
        }
        resultats.putIfAbsent(nomMatiere, new ArrayList<>());
        resultats.get(nomMatiere).add(note);
    }


    public double calculerMoyenne(String nomMatiere) throws Exception {
        if (!formation.getMatiere().containsKey(nomMatiere)) {
            throw new IllegalArgumentException("La matière n'existe pas dans la formation.");
        }
        if (!resultats.containsKey(nomMatiere) || resultats.get(nomMatiere).isEmpty()) {
            throw new IllegalStateException("Aucune note disponible pour la matière.");
        }
        List<Double> notes = resultats.get(nomMatiere);
        double somme = 0;
        for (double note : notes) {
            somme += note;
        }
        return somme / notes.size(); // Calcul de la moyenne
    }

    public double calculerMoyenneGenerale() throws Exception {
        double sommePonderee = 0;
        double totalCoefficients = 0;


        for (Map.Entry<String, Double> entry : formation.getMatiere().entrySet()) {
            String nomMatiere = entry.getKey();
            double coeff = formation.getcoeff(nomMatiere);

            if (resultats.containsKey(nomMatiere)) {
                double moyenneMatiere = calculerMoyenne(nomMatiere);
                sommePonderee += moyenneMatiere * coeff;
                totalCoefficients += coeff;
            }
        }

        if (totalCoefficients == 0) {
            throw new IllegalStateException("Aucune note enregistrée pour calculer la moyenne générale.");
        }

        return sommePonderee / totalCoefficients; 
    }

    public Formation getFormation() {
        return formation;
    }
}
