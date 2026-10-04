public class Malien extends Personatge {

    private boolean distret;

    public Malien(Zona zonaActual) {
        super("Malien", zonaActual);
        this.distret = false;
    }

    public boolean isDistret() {
        return distret;
    }

    public void distreure() {
        distret = true;
    }

    // Pot atacar si no s'ha distret amb els donuts i no s'ha ficat dins d'en Bond
    public boolean potAtacar() {
        return !distret && zonaActual != null;
    }

    @Override
    public void moures() {
        if (potAtacar()) {
            super.moures();
        }
    }

    @Override
    public String parlar(String frase, Jugador jugador) {
        if (distret) {
            return "Nyam, nyam... (esta menjant donuts i no et fa cas)";
        }
        return "GRRRSSSSS!";
    }
}
