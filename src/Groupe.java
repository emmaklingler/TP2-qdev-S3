import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Groupe {
    private String nom;
    private Formation formation;
    private Set<Etudiant> etudiants;

    public Groupe(String nom, Formation formation) {
        this.nom = nom;
        this.formation = formation;
        this.etudiants = new HashSet<>();
    }

    public String getNom() {
        return nom;
    }

    public Formation getFormation() {
        return formation;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public boolean ajouterEtudiant(Etudiant etudiant) {
        if (etudiant.getFormation().equals(this.formation)) {
            return etudiants.add(etudiant);
        } else {
            throw new IllegalArgumentException("L'étudiant n'appartient pas à la même formation que le groupe.");
        }
    }

    public boolean supprimerEtudiant(Etudiant etudiant) {
        return etudiants.remove(etudiant);
    }

    public List<Etudiant> triAlpha() {
        List<Etudiant> etudiantsListe = new ArrayList<>(etudiants);
        Collections.sort(etudiantsListe, Comparator.comparing(e -> e.getIdentite().getNom()));
        return etudiantsListe;
    }

    public List<Etudiant> triAntiAlpha() {
        List<Etudiant> etudiantsListe = new ArrayList<>(etudiants);
        Collections.sort(etudiantsListe, (e1, e2) -> e2.getIdentite().getNom().compareTo(e1.getIdentite().getNom()));
        return etudiantsListe;
    }

    // Méthode pour calculer la moyenne d'un groupe pour une matière
    public double calculerMoyenneMatiere(String nomMatiere) throws Exception {
        double sommeMoyenne = 0;
        int count = 0;

        for (Etudiant etudiant : etudiants) {
            try {
                sommeMoyenne += etudiant.calculerMoyenne(nomMatiere);
                count++;
            } catch (Exception e) {
                // Ignorer les étudiants sans notes pour cette matière
            }
        }

        if (count == 0) {
            throw new IllegalStateException("Aucune note disponible pour la matière " + nomMatiere);
        }

        return sommeMoyenne / count; // Retourne la moyenne du groupe pour la matière
    }

    // Méthode pour calculer la moyenne générale du groupe
    public double calculerMoyenneGenerale() throws Exception {
        double sommeMoyenne = 0;
        int count = 0;

        for (Etudiant etudiant : etudiants) {
            try {
                sommeMoyenne += etudiant.calculerMoyenneGenerale();
                count++;
            } catch (Exception e) {
                // Ignorer les étudiants sans notes
            }
        }

        if (count == 0) {
            throw new IllegalStateException("Aucune note disponible pour calculer la moyenne générale.");
        }

        return sommeMoyenne / count; // Retourne la moyenne générale du groupe
    }
}
