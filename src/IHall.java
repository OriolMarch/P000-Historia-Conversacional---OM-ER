public class IHall extends Personatge {

    private Zona[] zones;
    private Objecte llanterna;
    private Malien malien;
    private boolean enfadada;

    public IHall(Zona[] zones, Objecte llanterna, Malien malien) {
        super("iHall", null);
        this.zones = zones;
        this.llanterna = llanterna;
        this.malien = malien;
        this.enfadada = false;
    }

    // S'enfada quan en Bond fa servir el carnet de la Ripley
    public void enfadar() {
        enfadada = true;
    }

    @Override
    public String parlar(String frase, Jugador jugador) {
        if (frase.contains("PORTA") || frase.contains("OBRE")) {
            return obrirPorta(frase, jugador);
        }
        if (frase.contains("LLANTERNA")) {
            return onEsLaLlanterna(jugador);
        }
        if (frase.contains("MALIEN")) {
            return onEsElMalien();
        }
        if (frase.contains("TARJA")) {
            return "La seva tarja? Sempre la deixa a l'Oficina Principal. Miri als calaixos.";
        }
        if (frase.contains("HOLA")) {
            return "Hola, capita. En que el puc ajudar?";
        }
        return "No l'entenc. Pregunti'm per la LLANTERNA, pel MALIEN o que li obri una PORTA.";
    }

    private String obrirPorta(String frase, Jugador jugador) {
        // OEST va abans que EST perque "OEST" tambe conte "EST"
        String direccio = "";
        if (frase.contains("NORD")) {
            direccio = "NORD";
        } else if (frase.contains("SUD")) {
            direccio = "SUD";
        } else if (frase.contains("OEST")) {
            direccio = "OEST";
        } else if (frase.contains("EST")) {
            direccio = "EST";
        }

        if (direccio.equals("")) {
            return "Quina porta? Digui'm la direccio (NORD, SUD, EST o OEST).";
        }

        Porta porta = jugador.getZonaActual().getPorta(direccio);
        if (porta == null) {
            return "Aqui no hi ha cap porta cap al " + direccio + ".";
        }
        if (porta.isOberta()) {
            return "Ja es oberta, capita.";
        }

        // iHall no sempre fa cas: obre 7 de cada 10 vegades (3 si esta enfadada)
        int vegades = 7;
        if (enfadada) {
            vegades = 3;
        }

        if (random.nextInt(10) < vegades) {
            porta.obrir();
            return "Porta oberta.";
        }
        if (enfadada) {
            return "Que li obri la Ripley, que te el seu carnet.";
        }
        return "Ara no em ve de gust. Torni-ho a provar.";
    }

    private String onEsLaLlanterna(Jugador jugador) {
        if (jugador.getMotxilla().conte(llanterna)) {
            return "La porta voste a la motxilla, capita!";
        }

        Zona real = zones[0];
        for (int i = 0; i < zones.length; i++) {
            if (zones[i].conte(llanterna)) {
                real = zones[i];
            }
        }

        // La meitat de les vegades diu una zona equivocada
        if (random.nextInt(2) == 0) {
            Zona falsa = zones[random.nextInt(zones.length)];
            while (falsa == real) {
                falsa = zones[random.nextInt(zones.length)];
            }
            return "La llanterna es a: " + falsa.getNom() + ".";
        }
        return "La llanterna es a: " + real.getNom() + ".";
    }

    private String onEsElMalien() {
        if (malien.getZonaActual() == null) {
            return "Em sap greu, capita... el Malien es dins seu.";
        }
        if (malien.isDistret()) {
            return "Es a " + malien.getZonaActual().getNom() + " menjant donuts. Ja no molesta.";
        }
        return "El Malien es a: " + malien.getZonaActual().getNom() + ".";
    }
}
