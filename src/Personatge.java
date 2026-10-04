import java.util.ArrayList;
import java.util.Random;

public abstract class Personatge {

    protected String nom;
    protected Zona zonaActual;
    protected Random random;

    public Personatge(String nom, Zona zonaActual) {
        this.nom = nom;
        this.zonaActual = zonaActual;
        this.random = new Random();
    }

    public String getNom() {
        return nom;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }

    public void setZonaActual(Zona zonaActual) {
        this.zonaActual = zonaActual;
    }

    // Va a una zona del costat a l'atzar (mai a fora de la nau)
    public void moures() {
        ArrayList<Zona> veines = new ArrayList<>();

        for (Zona zona : zonaActual.getZonesVeines()) {
            if (!zona.isExterior()) {
                veines.add(zona);
            }
        }

        zonaActual = veines.get(random.nextInt(veines.size()));
    }

    public abstract String parlar(String frase, Jugador jugador);
}
