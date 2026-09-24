import java.util.HashMap;

public class Zona {

    private int id;
    private String nom;
    private String descripcio;
    private HashMap<String, Zona> sortides;

    public Zona(int id, String nom, String descripcio) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.sortides = new HashMap<String, Zona>();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void afegirSortida(String direccio, Zona desti) {
        sortides.put(direccio, desti);
    }

    public Zona getSortida(String direccio) {
        return sortides.get(direccio);
    }

    public String getSortidesText() {
        String[] direccions = {"NORD", "SUD", "EST", "OEST"};
        String text = "";

        for (int i = 0; i < direccions.length; i++) {
            if (sortides.get(direccions[i]) != null) {
                if (text.equals("")) {
                    text = direccions[i];
                } else {
                    text = text + ", " + direccions[i];
                }
            }
        }

        if (text.equals("")) {
            text = "cap";
        }
        return text;
    }

    public void mostrarDescripcio() {
        System.out.println();
        System.out.println("=== " + nom + " ===");
        System.out.println(descripcio);
        System.out.println("Sortides: " + getSortidesText());
    }
}
