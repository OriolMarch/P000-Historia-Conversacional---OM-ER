import java.util.ArrayList;
import java.util.List;

public class Motxilla {

    private List<Objecte> objectes;

    public Motxilla() {
        this.objectes = new ArrayList<>();
    }

    public void afegir(Objecte obj) {
        objectes.add(obj);
    }

    public void treure(Objecte obj) {
        objectes.remove(obj);
    }

    public Objecte buscar(String nom) {
        for (Objecte obj : objectes) {
            if (obj.getNom().equalsIgnoreCase(nom)) {
                return obj;
            }
        }
        return null;
    }

    public boolean esBuida() {
        return objectes.isEmpty();
    }

    public void mostrarContingut() {
        System.out.println();
        System.out.println("--- MOTXILLA ---");

        if (esBuida()) {
            System.out.println(" La motxilla es buida.");
            return;
        }

        for (Objecte obj : objectes) {
            System.out.println(" " + obj.getNom() + ": " + obj.getDescripcio());
        }
    }
}
