public class Objecte {

    private String nom;
    private String descripcio;
    private boolean agafable;
    private String textUsar;

    public Objecte(String nom, String descripcio, boolean agafable) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.agafable = agafable;
        this.textUsar = "";
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

    public String getTextUsar() {
        return textUsar;
    }

    public void setTextUsar(String textUsar) {
        this.textUsar = textUsar;
    }
}
