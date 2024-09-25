import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TestFormation {

    private Formation formation;

    @BeforeEach
    void setUp() {
        formation = new Formation(1);
        HashMap<String, Double> matieres = new HashMap<>();
        matieres.put("Mathématiques", 12.5);
        matieres.put("Physique", 15.0);
        formation.ajouterMatiere(matieres);
    }

    @Test
    void testAjouterNouvelleMatiere() {
        HashMap<String, Double> nouvelleMatiere = new HashMap<>();
        nouvelleMatiere.put("Informatique", 17.0);

        formation.ajouterMatiere(nouvelleMatiere);

        assertTrue(formation.getMatiere().containsKey("Informatique"));
        assertEquals(17.0, formation.getMatiere().get("Informatique"));
    }

    @Test
    void testAjouterMatiereExistanteNeModifiePas() {
        HashMap<String, Double> matiereExistante = new HashMap<>();
        matiereExistante.put("Mathématiques", 18.0);

        formation.ajouterMatiere(matiereExistante);

        assertTrue(formation.getMatiere().containsKey("Mathématiques"));
        assertEquals(12.5, formation.getMatiere().get("Mathématiques"));
    }

    @Test
    void testAjouterPlusieursMatieres() {
        HashMap<String, Double> nouvellesMatieres = new HashMap<>();
        nouvellesMatieres.put("Chimie", 14.0);
        nouvellesMatieres.put("Biologie", 13.5);

        formation.ajouterMatiere(nouvellesMatieres);

        assertTrue(formation.getMatiere().containsKey("Chimie"));
        assertTrue(formation.getMatiere().containsKey("Biologie"));
        assertEquals(14.0, formation.getMatiere().get("Chimie"));
        assertEquals(13.5, formation.getMatiere().get("Biologie"));
    }

}