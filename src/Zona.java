import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Zona {

    private int id;
    private String nom;
    private String descripcio;
    private HashMap<String, Zona> sortides;
    private List<Objecte> objectes;

    public Zona(int id, String nom, String descripcio) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.sortides = new HashMap<String, Zona>();
        this.objectes = new ArrayList<>();
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

    public void afegirObjecte(Objecte obj) {
        objectes.add(obj);
    }

    public void treureObjecte(Objecte obj) {
        objectes.remove(obj);
    }

    public Objecte buscarObjecte(String nom) {
        for (Objecte obj : objectes) {
            if (obj.getNom().equalsIgnoreCase(nom)) {
                return obj;
            }
        }
        return null;
    }

    public String getObjectesText() {
        String text = "";

        for (Objecte obj : objectes) {
            if (text.equals("")) {
                text = obj.getNom();
            } else {
                text = text + ", " + obj.getNom();
            }
        }
        return text;
    }

    public void mostrarDescripcio() {
        System.out.println();
        System.out.println("=== " + nom + " ===");
        System.out.println(descripcio);

        if (!objectes.isEmpty()) {
            System.out.println("Objectes: " + getObjectesText());
        }
        System.out.println("Sortides: " + getSortidesText());
    }
}
