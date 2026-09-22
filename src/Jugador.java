public class Jugador {

    private String nom;
    private Zona zonaActual;

    public Jugador(String nom, Zona zonaInicial) {
        this.nom = nom;
        this.zonaActual = zonaInicial;
    }

    public String getNom() {
        return nom;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }

    public void moure(Zona novaZona) {
        this.zonaActual = novaZona;
    }
}
