public class Objecte {

    private String nom;
    private String descripcio;
    private boolean agafable;

    public Objecte(String nom, String descripcio, boolean agafable) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.agafable = agafable;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public boolean isAgafable() {
        return agafable;
    }
}
