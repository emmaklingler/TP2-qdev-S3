import java.util.HashMap;

public class Formation {
    private int ID;
    private HashMap<String, Float> matiere;

    public Formation(int ID) {
        this.ID = ID;
        this.matiere = new HashMap<>();
    }


    public void ajouterMatiere(HashMap<String, Float> matierep) {
        for (String key : matierep.keySet()) {
            if (!this.matiere.containsKey(key)) {
                this.matiere.put(key, matierep.get(key));
            }
        }
    }



    public void supprimerMatiere(HashMap<String, Float>matierep) {
        for (String key : matierep.keySet()) {
            if (this.matiere.containsKey(key)) {
                this.matiere.remove(matierep);
            }else{ System.out.println("cette matiere n'est pas présente dans la liste");}
        }

    }


    public Float getcoeff(String matiereName) {

        return this.matiere.get(matiereName);
    }
    public HashMap<String, Float> getMatiere() {

        return this.matiere;
    }
}
