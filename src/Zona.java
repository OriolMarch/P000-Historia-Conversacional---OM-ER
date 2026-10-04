import java.util.ArrayList;
import java.util.HashMap;

public class Zona {

    private int id;
    private String nom;
    private String descripcio;
    private HashMap<String, Porta> sortides;
    private ArrayList<Objecte> objectes;
    private boolean fosca;
    private boolean exterior;

    public Zona(int id, String nom, String descripcio) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.sortides = new HashMap<String, Porta>();
        this.objectes = new ArrayList<>();
        this.fosca = false;
        this.exterior = false;
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

    public boolean isFosca() {
        return fosca;
    }

    public void setFosca(boolean fosca) {
        this.fosca = fosca;
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    public void afegirSortida(String direccio, Porta porta) {
        sortides.put(direccio, porta);
    }

    public Porta getPorta(String direccio) {
        return sortides.get(direccio);
    }

    public Zona getSortida(String direccio) {
        Porta porta = sortides.get(direccio);
        if (porta == null) {
            return null;
        }
        return porta.getAltraZona(this);
    }

    public ArrayList<Zona> getZonesVeines() {
        ArrayList<Zona> veines = new ArrayList<>();
        String[] direccions = {"NORD", "SUD", "EST", "OEST"};

        for (int i = 0; i < direccions.length; i++) {
            Zona zona = getSortida(direccions[i]);
            if (zona != null) {
                veines.add(zona);
            }
        }
        return veines;
    }

    public String getSortidesText() {
        String[] direccions = {"NORD", "SUD", "EST", "OEST"};
        String text = "";

        for (int i = 0; i < direccions.length; i++) {
            Porta porta = sortides.get(direccions[i]);
            if (porta != null) {
                String sortida = direccions[i];
                if (!porta.isOberta()) {
                    sortida = sortida + " [tancada]";
                }

                if (text.equals("")) {
                    text = sortida;
                } else {
                    text = text + ", " + sortida;
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

    public boolean conte(Objecte obj) {
        return objectes.contains(obj);
    }

    public Objecte buscarObjecte(String nom) {
        for (Objecte obj : objectes) {
            if (obj.getNom().equals(nom)) {
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

    public void mostrarDescripcio(boolean hiHaLlum) {
        System.out.println();
        System.out.println("=== " + nom + " ===");

        if (hiHaLlum) {
            System.out.println(descripcio);
            if (!objectes.isEmpty()) {
                System.out.println("Objectes: " + getObjectesText());
            }
        } else {
            System.out.println("Esta tot fosc. No hi veus res.");
        }
        System.out.println("Sortides: " + getSortidesText());
    }
}
