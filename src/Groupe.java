import java.util.HashSet;
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
}