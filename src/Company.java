public class Company extends Personatge {

    private boolean despert;
    private Objecte carnet;

    public Company(Zona zonaActual, Objecte carnet) {
        super("Ripley", zonaActual);
        this.despert = false;
        this.carnet = carnet;
    }

    public boolean isDespert() {
        return despert;
    }

    public void despertar() {
        despert = true;
    }

    @Override
    public void moures() {
        if (despert) {
            super.moures();
        }
    }

    @Override
    public String parlar(String frase, Jugador jugador) {
        if (!despert) {
            return "Zzzz... (esta dormint dins la CAPSULA)";
        }

        if (frase.contains("CARNET") || frase.contains("TARJA")) {
            if (carnet == null) {
                return "Ja t'he donat el meu carnet!";
            }
            jugador.getMotxilla().afegir(carnet);
            carnet = null;
            return "Te, el meu CARNET. Obre les portes, pero a iHall no li agradara.";
        }

        if (frase.contains("HOLA")) {
            return "Capita? Per que m'has despertat?";
        }
        return "Tinc gana... Em menjaria uns donuts.";
    }
}
