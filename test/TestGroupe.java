import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestGroupe {

    private Groupe groupe;
    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Formation formation1;

    @BeforeEach
    public void setUp() throws Exception {
        Identite identite1 = new Identite("1", "John", "Doe");
        Identite identite2 = new Identite("2", "Jane", "Smith");


        formation1 = new Formation(1);
        HashMap<String, Double> matieres = new HashMap<>();
        matieres.put("Mathématiques", 12.5);
        matieres.put("Informatique", 15.0);
        matieres.put("Physique", 10.0);
        formation1.ajouterMatiere(matieres);


        etudiant1 = new Etudiant(identite1, formation1);
        etudiant2 = new Etudiant(identite2, formation1);

        etudiant1.ajouterNote("Mathématiques", 15.0);
        etudiant1.ajouterNote("Physique", 12.0);
        etudiant1.ajouterNote("Informatique", 14.0);
        etudiant2.ajouterNote("Mathématiques", 18.0);
        etudiant2.ajouterNote("Physique", 14.0);
        etudiant2.ajouterNote("Informatique", 16.0);

        groupe = new Groupe("Groupe A", formation1);
        groupe.ajouterEtudiant(etudiant1);
        groupe.ajouterEtudiant(etudiant2);
    }


    @Test
    public void testCalculerMoyenneMatiere() throws Exception {
        double moyenneMath = groupe.calculerMoyenneMatiere("Mathématiques");
        assertEquals(16.5, moyenneMath, 0.001, "La moyenne du groupe en Mathématiques devrait être 16.5");

        double moyenneInfo = groupe.calculerMoyenneMatiere("Informatique");
        assertEquals(15.0, moyenneInfo, 0.001, "La moyenne du groupe en Informatique devrait être 15.0");
    }


    @Test
    public void testCalculerMoyenneMatiereInexistante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groupe.calculerMoyenneMatiere("Histoire");
        });
        assertEquals("La matière Histoire n'existe pas dans la formation.", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneGroupeSansNotesMatiere() throws Exception {
        Groupe groupeSansNotes = new Groupe("Groupe B", formation1);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            groupeSansNotes.calculerMoyenneMatiere("Mathématiques");
        });
        assertEquals("Aucune note disponible pour la matière Mathématiques", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneGeneraleGroupeSansNotes() {
        Groupe groupeVide = new Groupe("Groupe Vide", formation1);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            groupeVide.calculerMoyenneGenerale();
        });
        assertEquals("Aucune note disponible pour calculer la moyenne générale.", exception.getMessage());
    }



    @Test
    public void testMoyenneMatiereSansNotes() {
        Groupe groupeSansNotes = new Groupe("Groupe C", formation1);
        Identite identite = new Identite("3", "Alice", "Brown");
        Etudiant etudiant = new Etudiant(identite, formation1);
        groupeSansNotes.ajouterEtudiant(etudiant); // Ajout d'un étudiant sans notes


        Exception exception = assertThrows(IllegalStateException.class, () -> {
            groupeSansNotes.calculerMoyenneMatiere("Mathématiques");
        });
        assertEquals("Aucune note disponible pour la matière Mathématiques", exception.getMessage());
    }

    @Test
    public void testCalculerMoyenneGenerale() throws Exception {

        double moyenneGenerale = groupe.calculerMoyenneGenerale();
        assertEquals(14.97, moyenneGenerale, 0.001, "La moyenne générale du groupe devrait être 15.0.");
    }

    @Test
    public void testCalculerMoyenneGSansNotes() {
        Groupe groupeVide = new Groupe("Groupe Vide", formation1);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            groupeVide.calculerMoyenneGenerale();
        });
        assertEquals("Aucune note disponible pour calculer la moyenne générale.", exception.getMessage());
    }

}
