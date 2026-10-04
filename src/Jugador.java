public class Jugador {

    private String nom;
    private Zona zonaActual;
    private Motxilla motxilla;
    private boolean vestitPosat;

    public Jugador(String nom, Zona zonaInicial) {
        this.nom = nom;
        this.zonaActual = zonaInicial;
        this.motxilla = new Motxilla();
        this.vestitPosat = false;
    }

    public String getNom() {
        return nom;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }

    public Motxilla getMotxilla() {
        return motxilla;
    }

    public void moure(Zona novaZona) {
        this.zonaActual = novaZona;
    }

    public void agafar(Objecte obj) {
        zonaActual.treureObjecte(obj);
        motxilla.afegir(obj);
    }

    public void deixar(Objecte obj) {
        motxilla.treure(obj);
        zonaActual.afegirObjecte(obj);
    }

    public boolean portaVestit() {
        return vestitPosat;
    }

    public void posarVestit() {
        vestitPosat = true;
    }

    public void treureVestit() {
        vestitPosat = false;
    }
}
