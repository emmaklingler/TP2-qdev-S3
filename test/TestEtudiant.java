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
        identite = new Identite("1", "John", "Doe");
        formation = new Formation(1);
        HashMap<String, Double> matieres = new HashMap<>();
        matieres.put("Mathématiques", 12.5);
        matieres.put("Informatique", 15.0);
        formation.ajouterMatiere(matieres);
        etudiant = new Etudiant(identite, formation);
    }

    @Test
    public void testAjouterNoteValide() throws Exception {
        etudiant.ajouterNote("Mathématiques", 15.0);
        assertEquals(15.0, etudiant.calculerMoyenne("Mathématiques"), 0.001); // ajout du delta
    }

    @Test
    public void testAjouterNoteInvalideHorsLimite() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiant.ajouterNote("Mathématiques", 25.0);
        });
        assertEquals("La note doit être entre 0 et 20.", exception.getMessage());
    }

    @Test
    public void testAjouterNoteMatiereInexistante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiant.ajouterNote("Physique", 15.0);
        });
        assertEquals("La matière n'existe pas dans la formation.", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneMatiereInexistante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiant.calculerMoyenne("Physique");
        });
        assertEquals("La matière n'existe pas dans la formation.", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneAucuneNote() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            etudiant.calculerMoyenne("Mathématiques");
        });
        assertEquals("Aucune note disponible pour la matière.", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneValide() throws Exception {
        etudiant.ajouterNote("Mathématiques", 15.0);
        etudiant.ajouterNote("Mathématiques", 10.0);
        assertEquals(12.5, etudiant.calculerMoyenne("Mathématiques"), 0.001); // ajout du delta
    }

    @Test
    public void testCalculerMoyenneGeneraleValide() throws Exception {
        etudiant.ajouterNote("Mathématiques", 15.0);
        etudiant.ajouterNote("Informatique", 14.0);
        etudiant.ajouterNote("Informatique", 16.0);

        double moyenneGenerale = etudiant.calculerMoyenneGenerale();
        assertTrue(moyenneGenerale > 0);
    }

    @Test
    public void testCalculerMoyenneGeneraleSansNote() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            etudiant.calculerMoyenneGenerale();
        });
        assertEquals("Aucune note enregistrée pour calculer la moyenne générale.", exception.getMessage());
    }
}