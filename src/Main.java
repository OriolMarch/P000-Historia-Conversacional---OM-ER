import java.util.Scanner;

public class Main {

        private Jugador jugador;
        private Zona[] zones;
        private boolean finalitzat;
        private Scanner teclat;

        public static void main(String[] args) {
                Main joc = new Main();
                joc.iniciar();
        }

        public void iniciar() {
                teclat = new Scanner(System.in);
                finalitzat = false;

                crearMon();
                mostrarIntroduccio();
                mostrarDescripcioZona();

                while (!comprovarFinalJoc()) {
                        System.out.print("\n> ");
                        String ordre = teclat.nextLine();
                        processarOrdre(ordre);
                }

                System.out.println();
                System.out.println("Fins aviat, capita Bond!");
                teclat.close();
        }

        public boolean comprovarFinalJoc() {
                return finalitzat;
        }

        public void crearMon() {
                Zona dormitoris = new Zona(1, "Dormitoris",
                                "Files de capsules d'hibernacio amb llums blaves. La teva encara es oberta i calenta.");
                Zona passadis = new Zona(2, "Passadis Central",
                                "Un passadis llarg que travessa tota la nau. Els fluorescents parpellegen.");
                Zona oficina = new Zona(3, "Oficina Principal",
                                "Escriptoris plens de papers i informes. Un dels calaixos et sona d'alguna cosa.");
                Zona comandaments = new Zona(4, "Sala de Comandaments",
                                "Pantalles, botons i una gran placa vermella per engegar motors.");
                Zona vestuaris = new Zona(5, "Vestuaris",
                                "Armariets oberts i bancs. Hi ha un vestit espacial penjat d'un ganxo.");
                Zona infermeria = new Zona(6, "Infermeria",
                                "Lliteres fetes i una farmaciola buida. Fa una olor estranya, com de peix.");
                Zona taller = new Zona(7, "Taller",
                                "El taller de manteniment, ple d'eines ben endrecades.");
                Zona cuina = new Zona(8, "Cuina",
                                "Una cuina petita amb olor de cafe cremat.");
                Zona magatzem = new Zona(9, "Magatzem",
                                "Caixes apilades fins al sostre amb material de recanvi.");
                Zona esclusa = new Zona(10, "Esclusa",
                                "La porta exterior de la nau. Al darrere nomes hi ha el buit.");
                Zona motors = new Zona(11, "Zona de Motors",
                                "Ets fora de la nau, enganxat al casc. Els propulsors fumegen.");

                zones = new Zona[11];
                zones[0] = dormitoris;
                zones[1] = passadis;
                zones[2] = oficina;
                zones[3] = comandaments;
                zones[4] = vestuaris;
                zones[5] = infermeria;
                zones[6] = taller;
                zones[7] = cuina;
                zones[8] = magatzem;
                zones[9] = esclusa;
                zones[10] = motors;

                connectar(dormitoris, "NORD", passadis, "SUD");
                connectar(dormitoris, "SUD", esclusa, "NORD");
                connectar(passadis, "NORD", oficina, "SUD");
                connectar(passadis, "EST", cuina, "OEST");
                connectar(passadis, "OEST", taller, "EST");
                connectar(oficina, "NORD", comandaments, "SUD");
                connectar(oficina, "OEST", vestuaris, "EST");
                connectar(oficina, "EST", infermeria, "OEST");
                connectar(cuina, "EST", magatzem, "OEST");
                connectar(esclusa, "SUD", motors, "NORD");

                jugador = new Jugador("Bond", dormitoris);

        }

        public void connectar(Zona a, String direccioAB, Zona b, String direccioBA) {
                a.afegirSortida(direccioAB, b);
                b.afegirSortida(direccioBA, a);
        }

        public void mostrarIntroduccio() {
                System.out.println("");
                System.out.println("        LA NAU PIAXXII --- Any 2120 D.C.        ");
                System.out.println();
                System.out.println("Et despertes de la hibernacio amb la veu de l'ordinador de bord:");
                System.out.println();
                System.out.println("iHall: - Que tal ha dormit, capita Bond? Em sap greu destorbar-lo,");
                System.out.println("         pero hem xocat amb un aerolit i els propulsors estan tocats.");
                System.out.println();
                System.out.println("Escriu AJUDA per veure les ordres.");
        }

        private void mostrarAjuda() {
                System.out.println();
                System.out.println("--- ORDRES ---");
                System.out.println(" ANAR <direccio>   (NORD, SUD, EST, OEST)");
                System.out.println(" MIRAR             torna a descriure la zona");
                System.out.println(" AJUDA             aquesta llista");
                System.out.println(" SORTIR            acaba el joc");
                System.out.println();
                System.out.println("--- ZONES DE LA NAU ---");

                for (int i = 0; i < zones.length; i++) {
                        System.out.println(" " + zones[i].getNom() + " (sortides: " + zones[i].getSortidesText() + ")");
                }
        }

        public void mostrarDescripcioZona() {
                jugador.getZonaActual().mostrarDescripcio();
        }

        public void processarOrdre(String text) {
                String[] parts = text.trim().split(" ");
                String verb = parts[0].toUpperCase();
                String complement = "";

                if (parts.length > 1) {
                        complement = parts[1].toUpperCase();
                }

                if (esDireccio(verb)) {
                        anar(verb);
                        return;
                }

                if (verb.equals("ANAR")) {
                        anar(complement);
                } else if (verb.equals("MIRAR")) {
                        mostrarDescripcioZona();
                } else if (verb.equals("AJUDA")) {
                        mostrarAjuda();
                } else if (verb.equals("SORTIR")) {
                        System.out.println("Abandones la missio. La PiaXXII es perd per sempre a l'espai...");
                        finalitzat = true;
                } else if (verb.equals("")) {
                        System.out.println("No has escrit res, capita.");
                } else {
                        System.out.println("No entenc la paraula " + verb + ". Escriu AJUDA per veure les ordres.");
                }
        }

        private void anar(String direccio) {
                if (direccio.equals("")) {
                        System.out.println("Cap a on vols anar? (NORD, SUD, EST, OEST)");
                        return;
                }

                if (!esDireccio(direccio)) {
                        System.out.println("Nomes pots anar cap al NORD, SUD, EST o OEST.");
                        return;
                }

                direccio = direccioCompleta(direccio);
                Zona desti = jugador.getZonaActual().getSortida(direccio);

                if (desti == null) {
                        System.out.println("Per aqui no hi ha cap sortida.");
                        System.out.println("Sortides: " + jugador.getZonaActual().getSortidesText());
                        return;
                }

                jugador.moure(desti);
                System.out.println("Camines cap al " + direccio + "...");
                mostrarDescripcioZona();
        }

        private boolean esDireccio(String text) {
        text = text.toUpperCase();
        return text.equals("NORD") || text.equals("SUD")
            || text.equals("EST") || text.equals("OEST")
            || text.equals("N") || text.equals("S")
            || text.equals("E") || text.equals("O");
}

        private String direccioCompleta(String text) {
                if (text.equals("N")) {
                        return "NORD";
                }
                if (text.equals("S")) {
                        return "SUD";
                }
                if (text.equals("E")) {
                        return "EST";
                }
                if (text.equals("O")) {
                        return "OEST";
                }
                return text;
        }

}