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

    // Méthode pour trier les étudiants par leur moyenne générale
    public List<Etudiant> triParMerite() {
        List<Etudiant> etudiantsListe = new ArrayList<>(etudiants);
        Collections.sort(etudiantsListe, new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant e1, Etudiant e2) {
                try {
                    // Compare les moyennes générales des étudiants
                    double moyenneE1 = e1.calculerMoyenneGenerale();
                    double moyenneE2 = e2.calculerMoyenneGenerale();
                    return Double.compare(moyenneE1, moyenneE2); // Tri croissant
                } catch (Exception e) {
                    // Gérer le cas où l'une des moyennes ne peut pas être calculée
                    return 0; // Si l'une ne peut pas être calculée, les considérer égaux
                }
            }
        });
        return etudiantsListe;
    }


    public double calculerMoyenneMatiere(String nomMatiere) throws Exception {
        // Implémente la logique de calcul de la moyenne pour une matière ici
        double sommeMoyenne = 0;
        double somme = 0;
        int count = 0;

        for (Etudiant etudiant : etudiants) {
            try {
                sommeMoyenne += etudiant.calculerMoyenne(nomMatiere);
                double moyenne = etudiant.calculerMoyenne(nomMatiere);
                somme += moyenne;
                count++;
            } catch (IllegalStateException e) {
            }
        }
        if (count == 0) {
            throw new IllegalStateException("Aucune note disponible pour la matière " + nomMatiere);
        }

        return sommeMoyenne / count;
    }

    public double calculerMoyenneGenerale() throws Exception {
        double sommePonderee = 0;
        double totalCoefficients = 0;

        for (Etudiant etudiant : etudiants) {
            if (!etudiant.getFormation().getMatiere().isEmpty()) {
                double moyenneEtudiant = etudiant.calculerMoyenneGenerale();
                double coeff = etudiant.getFormation().getcoeff("Math"); // Exemple, remplacer par la logique nécessaire
                sommePonderee += moyenneEtudiant * coeff;
                totalCoefficients += coeff;
            }
        }

        if (totalCoefficients == 0) {
            throw new IllegalStateException("Aucune note enregistrée pour calculer la moyenne générale.");
        }

        return sommePonderee / totalCoefficients;
    }
}
