import java.util.ArrayList;

public class Motxilla {

    private ArrayList<Objecte> objectes;

    public Motxilla() {
        this.objectes = new ArrayList<>();
    }

    public void afegir(Objecte obj) {
        objectes.add(obj);
    }

    public void treure(Objecte obj) {
        objectes.remove(obj);
    }

    public boolean conte(Objecte obj) {
        return objectes.contains(obj);
    }

    public Objecte buscar(String nom) {
        for (Objecte obj : objectes) {
            if (obj.getNom().equals(nom)) {
                return obj;
            }
        }
        return null;
    }

    public void mostrarContingut() {
        System.out.println();
        System.out.println("--- MOTXILLA ---");

        if (objectes.isEmpty()) {
            System.out.println(" La motxilla es buida.");
        }

        for (Objecte obj : objectes) {
            System.out.println(" " + obj.getNom() + ": " + obj.getDescripcio());
        }
    }
}
