import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class TestEtudiant {

    private Etudiant etudiant;
    private Formation formation;
    private Identite identite;

    @BeforeEach
    public void setUp() {
        identite = new Identite("1", "Emma", "Klingler");
        formation = new Formation(1); // initialisation de la formation avec un identifiant

        // Ajout des matières avec leurs coefficients
        HashMap<String, Double> matieres = new HashMap<>();
        matieres.put("Mathématiques", 12.5);
        matieres.put("Informatique", 15.0);
        formation.ajouterMatiere(matieres); // Assurez-vous que cette méthode existe dans la classe Formation

        etudiant = new Etudiant(identite, formation);
    }

    @Test
    public void testAjouterNoteValide() throws Exception {
        etudiant.ajouterNote("Mathématiques", 15.0);
        assertEquals(15.0, etudiant.calculerMoyenne("Mathématiques"), 0.001); // delta pour comparer les doubles
    }

    @Test
    public void testAjouterNoteInvalideHorsLimite() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiant.ajouterNote("Mathématiques", 25.0); // note en dehors de la plage 0-20
        });
        assertEquals("La note doit être entre 0 et 20.", exception.getMessage());
    }

    @Test
    public void testAjouterNoteMatiereInexistante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiant.ajouterNote("Physique", 15.0); // matière non présente dans la formation
        });
        assertEquals("La matière n'existe pas dans la formation.", exception.getMessage());
    }


    @Test
    public void testCalculerMoyenneAucuneNote() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            etudiant.calculerMoyenne("Mathématiques"); // aucune note n'a été ajoutée
        });
        assertEquals("Aucune note disponible pour la matière.", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneValide() throws Exception {
        etudiant.ajouterNote("Mathématiques", 15.0);
        etudiant.ajouterNote("Mathématiques", 10.0);
        assertEquals(12.5, etudiant.calculerMoyenne("Mathématiques"), 0.001); // delta pour les doubles
    }

    @Test
    public void testCalculerMoyenneGeneraleValide() throws Exception {
        etudiant.ajouterNote("Mathématiques", 15.0);
        etudiant.ajouterNote("Informatique", 14.0);
        etudiant.ajouterNote("Informatique", 16.0);

        double moyenneGenerale = etudiant.calculerMoyenneGenerale();

        // Comparer la moyenne générale calculée avec la valeur attendue (vérifie aussi les coefficients)
        double sommePonderee = (15.0 * 12.5 + (14.0 + 16.0) / 2 * 15.0);
        double totalCoeff = 12.5 + 15.0;
        double expectedMoyenneGenerale = sommePonderee / totalCoeff;

        assertEquals(expectedMoyenneGenerale, moyenneGenerale, 0.001); // delta pour comparer les doubles
    }

    @Test
    public void testCalculerMoyenneGeneraleSansNote() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            etudiant.calculerMoyenneGenerale(); // aucune note n'a été ajoutée
        });
        assertEquals("Aucune note enregistrée pour calculer la moyenne générale.", exception.getMessage());
    }
}
