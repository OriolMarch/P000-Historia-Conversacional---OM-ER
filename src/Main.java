import java.util.Random;
import java.util.Scanner;

public class Main {

        private Jugador jugador;
        private Zona[] zones;
        private boolean finalitzat;
        private Scanner teclat;
        private Random random = new Random();

        private IHall ihall;
        private Malien malien;
        private Company ripley;

        private Zona comandaments;
        private Zona motors;

        private Objecte eina;
        private Objecte llanterna;
        private Objecte vestit;
        private Objecte tarja;
        private Objecte carnet;
        private Objecte donuts;

        private boolean llanternaEncesa;
        private boolean calaixObert;
        private boolean tarjaTrobada;
        private boolean propulsorsReparats;
        private boolean malienAvisat;
        private boolean malienDins;
        private int tornsQueQueden;
        private int moviments;

        public static void main(String[] args) {
                Main joc = new Main();
                joc.iniciar();
        }

        public void iniciar() {
                teclat = new Scanner(System.in);
                boolean jugar = true;

                while (jugar) {
                        jugarPartida();
                        jugar = tornarAJugar();
                }

                System.out.println();
                System.out.println("Fins aviat, capita Bond!");
                teclat.close();
        }

        private void jugarPartida() {
                finalitzat = false;
                crearMon();
                mostrarIntroduccio();
                mostrarDescripcioZona();

                while (!comprovarFinalJoc()) {
                        System.out.print("\n> ");

                        // Si ja no hi ha res mes per llegir, s'acaba la partida
                        if (teclat.hasNextLine()) {
                                String ordre = teclat.nextLine();
                                // Si una ordre dona error, el joc no es tanca
                                try {
                                        processarOrdre(ordre);
                                } catch (Exception e) {
                                        System.out.println("Error en fer l'ordre: " + e.getMessage());
                                }
                        } else {
                                finalitzat = true;
                        }
                }
        }

        private boolean tornarAJugar() {
                System.out.print("\nVols tornar a jugar? (S/N) ");
                if (!teclat.hasNextLine()) {
                        return false;
                }
                String resposta = teclat.nextLine().trim().toUpperCase();
                return resposta.equals("S") || resposta.equals("SI");
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
                comandaments = new Zona(4, "Sala de Comandaments",
                                "Pantalles, botons i una gran placa vermella per engegar motors.");
                Zona vestuaris = new Zona(5, "Vestuaris",
                                "Armariets oberts i bancs, i una fila de ganxos per penjar els vestits espacials.");
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
                motors = new Zona(11, "Zona de Motors",
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

                taller.setFosca(true);
                motors.setExterior(true);

                // Objectes que es poden agafar
                eina = new Objecte("EINA", "Una eina especial per reparar els propulsors.", true);
                llanterna = new Objecte("LLANTERNA", "Una llanterna petita pero potent.", true);
                vestit = new Objecte("VESTIT", "Un vestit espacial per poder sortir de la nau.", true);
                tarja = new Objecte("TARJA", "La teva tarja identificadora. Obre les portes.", true);
                carnet = new Objecte("CARNET", "El carnet de la Ripley. Tambe obre les portes.", true);
                donuts = new Objecte("DONUTS", "Una capsa de donuts. Al Malien li encanten.", true);

                taller.afegirObjecte(eina);
                vestuaris.afegirObjecte(vestit);
                cuina.afegirObjecte(donuts);

                // La llanterna va a una zona a l'atzar
                Zona[] llocsLlanterna = {passadis, oficina, comandaments, vestuaris, infermeria, cuina, magatzem, esclusa};
                llocsLlanterna[random.nextInt(llocsLlanterna.length)].afegirObjecte(llanterna);

                // Objectes que no es poden agafar
                oficina.afegirObjecte(new Objecte("CALAIX", "Un calaix de l'escriptori.", false));
                dormitoris.afegirObjecte(new Objecte("CAPSULA", "La capsula on dorm la Ripley.", false));
                comandaments.afegirObjecte(new Objecte("PLACA", "La placa vermella que engega els motors.", false));
                motors.afegirObjecte(new Objecte("PROPULSORS", "Els propulsors, trencats per l'aerolit.", false));

                Objecte fluorescents = new Objecte("FLUORESCENTS", "Uns fluorescents que parpellegen.", false);
                fluorescents.setTextUsar("Dones un cop als fluorescents i deixen de parpellejar... un moment.");
                passadis.afegirObjecte(fluorescents);

                Objecte cafetera = new Objecte("CAFETERA", "Una cafetera vella.", false);
                cafetera.setTextUsar("Et fas un cafe ben carregat. Ara si que estas despert.");
                cuina.afegirObjecte(cafetera);

                Objecte llitera = new Objecte("LLITERA", "Una llitera ben feta.", false);
                llitera.setTextUsar("T'estires un moment. iHall: 'Capita, no es moment de dormir!'");
                infermeria.afegirObjecte(llitera);

                Objecte caixes = new Objecte("CAIXES", "Caixes de material de recanvi.", false);
                caixes.setTextUsar("Remenes les caixes: cables i cargols. Res que et serveixi.");
                magatzem.afegirObjecte(caixes);

                Objecte finestreta = new Objecte("FINESTRETA", "Una finestreta que dona a l'exterior.", false);
                finestreta.setTextUsar("Per la finestreta veus els propulsors fumejant.");
                esclusa.afegirObjecte(finestreta);

                // Personatges
                jugador = new Jugador("Bond", dormitoris);

                Zona[] llocsMalien = {comandaments, vestuaris, infermeria, magatzem, taller};
                malien = new Malien(llocsMalien[random.nextInt(llocsMalien.length)]);
                ripley = new Company(dormitoris, carnet);
                ihall = new IHall(zones, llanterna, malien);

                llanternaEncesa = false;
                calaixObert = false;
                tarjaTrobada = false;
                propulsorsReparats = false;
                malienAvisat = false;
                malienDins = false;
                tornsQueQueden = 0;
                moviments = 0;
        }

        public void connectar(Zona a, String direccioAB, Zona b, String direccioBA) {
                Porta porta = new Porta(a, b);
                a.afegirSortida(direccioAB, porta);
                b.afegirSortida(direccioBA, porta);
        }

        public void mostrarIntroduccio() {
                System.out.println("");
                System.out.println("        LA NAU PIAXXII --- Any 2120 D.C.        ");
                System.out.println();
                System.out.println("Et despertes de la hibernacio amb la veu de l'ordinador de bord:");
                System.out.println();
                System.out.println("iHall: - Que tal ha dormit, capita Bond? Em sap greu destorbar-lo,");
                System.out.println("         pero hem xocat amb un aerolit i els propulsors estan tocats.");
                System.out.println("         Ha de sortir a reparar-los i despres engegar els motors.");
                System.out.println("         Les portes s'obren amb la seva tarja. Si no la te, demani'm");
                System.out.println("         que les obri: PARLAR IHALL obre la porta nord");
                System.out.println("         I compte, que hi ha un alien a la nau: el Malien.");
                System.out.println();
                System.out.println("Escriu AJUDA per veure les ordres.");
        }

        private void mostrarAjuda() {
                System.out.println();
                System.out.println("--- ORDRES ---");
                System.out.println(" ANAR <direccio>       NORD, SUD, EST, OEST (o N, S, E, O)");
                System.out.println(" MIRAR                 torna a descriure la zona");
                System.out.println(" AGAFAR <objecte>      agafa un objecte de la zona");
                System.out.println(" DEIXAR <objecte>      deixa un objecte a la zona");
                System.out.println(" USAR <objecte>        fa servir un objecte (USAR EINA PROPULSORS)");
                System.out.println(" ENCENDRE <objecte>    (ENCENDRE LLANTERNA, ENCENDRE MOTORS)");
                System.out.println(" APAGAR <objecte>      (APAGAR LLANTERNA)");
                System.out.println(" OBRIR <objecte>       (OBRIR CALAIX, OBRIR PORTA NORD)");
                System.out.println(" TANCAR <objecte>      (TANCAR CALAIX, TANCAR PORTA NORD)");
                System.out.println(" PARLAR <qui> <frase>  (PARLAR IHALL on es la llanterna)");
                System.out.println(" INVENTARI             el que portes a la motxilla");
                System.out.println(" AJUDA                 aquesta llista");
                System.out.println(" SORTIR                acaba el joc");
                System.out.println();
                System.out.println("--- ZONES DE LA NAU ---");

                for (int i = 0; i < zones.length; i++) {
                        System.out.println(" " + zones[i].getNom() + " (sortides: " + zones[i].getSortidesText() + ")");
                }
        }

        public void mostrarDescripcioZona() {
                Zona zona = jugador.getZonaActual();
                zona.mostrarDescripcio(hiHaLlum(zona));

                if (ripley.getZonaActual() == zona) {
                        if (ripley.isDespert()) {
                                System.out.println("La Ripley es aqui.");
                        } else {
                                System.out.println("La Ripley dorm dins la seva CAPSULA, amb el CARNET penjat al coll.");
                        }
                }

                if (malien.getZonaActual() == zona && malien.isDistret()) {
                        System.out.println("El Malien es aqui menjant donuts. No et fa cas.");
                }
        }

        private boolean hiHaLlum(Zona zona) {
                if (!zona.isFosca()) {
                        return true;
                }
                return llanternaEncesa && jugador.getMotxilla().conte(llanterna);
        }

        public void processarOrdre(String text) {
                String[] parts = text.trim().toUpperCase().split("\\s+", 2);
                String verb = parts[0];
                String complement = "";

                if (parts.length > 1) {
                        complement = parts[1];
                }

                if (verb.equals("")) {
                        System.out.println("No has escrit res, capita.");
                        return;
                }
                if (verb.equals("AJUDA")) {
                        mostrarAjuda();
                        return;
                }
                if (verb.equals("SORTIR")) {
                        System.out.println("Abandones la missio. La PiaXXII es perd per sempre a l'espai...");
                        finalitzat = true;
                        return;
                }

                // Si el Malien es dins d'en Bond, cada ordre resta un torn
                if (malienDins) {
                        tornsQueQueden--;
                        if (tornsQueQueden == 0) {
                                acabarPartida("Un Malien petit et surt de l'estomac...", false);
                                return;
                        }
                        System.out.println("Notes alguna cosa a l'estomac... Ordres que et queden: " + tornsQueQueden);
                }

                if (esDireccio(verb)) {
                        anar(verb);
                } else if (verb.equals("ANAR")) {
                        anar(complement);
                } else if (verb.equals("MIRAR")) {
                        mostrarDescripcioZona();
                } else if (verb.equals("AGAFAR")) {
                        agafar(complement);
                } else if (verb.equals("DEIXAR")) {
                        deixar(complement);
                } else if (verb.equals("USAR")) {
                        usar(complement);
                } else if (verb.equals("ENCENDRE")) {
                        encendre(complement);
                } else if (verb.equals("APAGAR")) {
                        apagar(complement);
                } else if (verb.equals("OBRIR")) {
                        obrir(complement);
                } else if (verb.equals("TANCAR")) {
                        tancar(complement);
                } else if (verb.equals("PARLAR")) {
                        parlar(complement);
                } else if (verb.equals("INVENTARI")) {
                        mostrarInventari();
                } else {
                        System.out.println("No entenc la paraula " + verb + ". Escriu AJUDA per veure les ordres.");
                        return;
                }

                if (!finalitzat) {
                        comprovarPersonatges();
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
                Zona zona = jugador.getZonaActual();
                Porta porta = zona.getPorta(direccio);

                if (porta == null) {
                        System.out.println("Per aqui no hi ha cap sortida.");
                        System.out.println("Sortides: " + zona.getSortidesText());
                        return;
                }

                if (!porta.isOberta()) {
                        if (!obrirAmbTarja(porta, direccio)) {
                                return;
                        }
                }

                Zona desti = porta.getAltraZona(zona);
                if (desti.isExterior() && !jugador.portaVestit()) {
                        acabarPartida("Surts a l'espai sense el vestit... No pots respirar.", false);
                        return;
                }

                jugador.moure(desti);
                malienAvisat = false;
                System.out.println("Camines cap al " + direccio + "...");
                mostrarDescripcioZona();

                // La Ripley es mou cada vegada i el Malien cada 2 moviments
                moviments++;
                ripley.moures();
                if (moviments % 2 == 0) {
                        malien.moures();
                }
        }

        private boolean obrirAmbTarja(Porta porta, String direccio) {
                if (jugador.getMotxilla().conte(tarja)) {
                        porta.obrir();
                        System.out.println("Passes la teva tarja i la porta s'obre.");
                        return true;
                }

                if (jugador.getMotxilla().conte(carnet)) {
                        porta.obrir();
                        System.out.println("Passes el carnet de la Ripley i la porta s'obre.");
                        System.out.println("iHall: Aquest carnet no es seu, capita! Ja no l'ajudare tant.");
                        ihall.enfadar();
                        return true;
                }

                System.out.println("La porta es tancada i no tens tarja.");
                System.out.println("Demana-ho a iHall: PARLAR IHALL obre la porta " + direccio);
                return false;
        }

        private void agafar(String nom) {
                if (nom.equals("")) {
                        System.out.println("Que vols agafar?");
                        return;
                }

                Zona zona = jugador.getZonaActual();
                Objecte obj = zona.buscarObjecte(nom);

                if (obj == null) {
                        if (jugador.getMotxilla().buscar(nom) != null) {
                                System.out.println("Ja portes " + nom + " a la motxilla.");
                        } else {
                                System.out.println("Aqui no hi ha cap " + nom + ".");
                        }
                        return;
                }

                if (obj == eina && !hiHaLlum(zona)) {
                        System.out.println("Esta massa fosc. No trobes l'eina.");
                        return;
                }

                if (!obj.isAgafable()) {
                        System.out.println("No pots agafar " + nom + ".");
                        return;
                }

                jugador.agafar(obj);
                System.out.println("Has agafat " + nom + ".");
        }

        private void deixar(String nom) {
                if (nom.equals("")) {
                        System.out.println("Que vols deixar?");
                        return;
                }

                Objecte obj = jugador.getMotxilla().buscar(nom);

                if (obj == null) {
                        System.out.println("No portes cap " + nom + " a la motxilla.");
                        return;
                }

                if (obj == vestit && jugador.portaVestit()) {
                        if (jugador.getZonaActual().isExterior()) {
                                System.out.println("No et pots treure el vestit a l'espai!");
                                return;
                        }
                        jugador.treureVestit();
                }

                jugador.deixar(obj);
                System.out.println("Has deixat " + nom + ".");
        }

        private void usar(String complement) {
                if (complement.equals("")) {
                        System.out.println("Que vols fer servir?");
                        return;
                }

                // USAR EINA PROPULSORS: la primera paraula es l'objecte i l'ultima, l'objectiu
                String[] paraules = complement.split("\\s+");
                String nom = paraules[0];
                String objectiu = "";
                if (paraules.length > 1) {
                        objectiu = paraules[paraules.length - 1];
                }

                Zona zona = jugador.getZonaActual();
                Objecte obj = jugador.getMotxilla().buscar(nom);
                if (obj == null) {
                        obj = zona.buscarObjecte(nom);
                }

                if (obj == null) {
                        System.out.println("No tens cap " + nom + ".");
                        return;
                }

                if (obj.isAgafable() && !jugador.getMotxilla().conte(obj)) {
                        System.out.println("Primer has d'agafar " + nom + ".");
                        return;
                }

                if (obj == vestit) {
                        usarVestit();
                } else if (obj == eina) {
                        usarEina(objectiu);
                } else if (obj == donuts) {
                        usarDonuts();
                } else if (obj == llanterna) {
                        System.out.println("Escriu ENCENDRE LLANTERNA o APAGAR LLANTERNA.");
                } else if (obj == tarja || obj == carnet) {
                        System.out.println("Les portes s'obren soles quan passes amb la tarja.");
                } else if (nom.equals("PLACA")) {
                        engegarMotors();
                } else if (!obj.getTextUsar().equals("")) {
                        System.out.println(obj.getTextUsar());
                } else {
                        System.out.println("No saps com fer servir " + nom + ".");
                }
        }

        private void usarVestit() {
                if (!jugador.portaVestit()) {
                        jugador.posarVestit();
                        System.out.println("Et poses el vestit espacial.");
                } else if (jugador.getZonaActual().isExterior()) {
                        System.out.println("No et pots treure el vestit a l'espai!");
                } else {
                        jugador.treureVestit();
                        System.out.println("Et treus el vestit espacial.");
                }
        }

        private void usarEina(String objectiu) {
                Zona zona = jugador.getZonaActual();

                if (objectiu.equals("MALIEN")) {
                        if (!malien.potAtacar() || malien.getZonaActual() != zona) {
                                System.out.println("El Malien no es aqui.");
                                return;
                        }
                        malien.setZonaActual(null);
                        malienDins = true;
                        tornsQueQueden = 7;
                        System.out.println("Ataques el Malien amb l'eina... pero se't fica per la gola!");
                        System.out.println("Et queden 7 ordres de vida.");
                        return;
                }

                if (zona != motors) {
                        System.out.println("Aqui no hi ha res per reparar.");
                        return;
                }

                if (propulsorsReparats) {
                        System.out.println("Els propulsors ja estan reparats.");
                        return;
                }

                propulsorsReparats = true;
                System.out.println("Repares els propulsors! Ara cal engegar els motors des de la Sala de Comandaments.");
        }

        private void usarDonuts() {
                if (malien.potAtacar() && malien.getZonaActual() == jugador.getZonaActual()) {
                        malien.distreure();
                        jugador.getMotxilla().treure(donuts);
                        System.out.println("Li llences els donuts al Malien i s'hi posa a menjar. Ja no et fara cas!");
                } else {
                        System.out.println("Millor guarda els donuts per al Malien.");
                }
        }

        private void engegarMotors() {
                if (jugador.getZonaActual() != comandaments) {
                        System.out.println("Els motors s'engeguen des de la Sala de Comandaments.");
                        return;
                }

                if (!propulsorsReparats) {
                        System.out.println("Prems la placa vermella, pero els propulsors encara estan trencats.");
                        return;
                }

                acabarPartida("Els motors rugeixen i la PiaXXII torna a anar cap a SUMMEM.\n"
                                + "iHall: Bona feina, capita. Ja pot tornar a dormir.", true);
        }

        private void encendre(String nom) {
                if (nom.equals("")) {
                        System.out.println("Que vols encendre?");
                } else if (nom.equals("MOTORS") || nom.equals("PLACA")) {
                        engegarMotors();
                } else if (nom.equals("LLANTERNA")) {
                        if (!jugador.getMotxilla().conte(llanterna)) {
                                System.out.println("No portes la llanterna.");
                                return;
                        }
                        llanternaEncesa = true;
                        System.out.println("Encens la llanterna.");
                        if (jugador.getZonaActual().isFosca()) {
                                mostrarDescripcioZona();
                        }
                } else {
                        System.out.println("No pots encendre " + nom + ".");
                }
        }

        private void apagar(String nom) {
                if (nom.equals("")) {
                        System.out.println("Que vols apagar?");
                } else if (nom.equals("LLANTERNA")) {
                        if (!jugador.getMotxilla().conte(llanterna)) {
                                System.out.println("No portes la llanterna.");
                                return;
                        }
                        llanternaEncesa = false;
                        System.out.println("Apagues la llanterna.");
                } else {
                        System.out.println("No pots apagar " + nom + ".");
                }
        }

        private void obrir(String nom) {
                Zona zona = jugador.getZonaActual();

                if (nom.equals("")) {
                        System.out.println("Que vols obrir?");
                } else if (nom.startsWith("PORTA")) {
                        String direccio = direccioPorta(nom);
                        if (!direccio.equals("")) {
                                Porta porta = zona.getPorta(direccio);
                                if (porta.isOberta()) {
                                        System.out.println("La porta ja es oberta.");
                                } else {
                                        obrirAmbTarja(porta, direccio);
                                }
                        }
                } else if (nom.equals("CALAIX") && zona.buscarObjecte("CALAIX") != null) {
                        if (calaixObert) {
                                System.out.println("El calaix ja es obert.");
                        } else if (!tarjaTrobada) {
                                calaixObert = true;
                                tarjaTrobada = true;
                                zona.afegirObjecte(tarja);
                                System.out.println("Obres el calaix i hi trobes la teva TARJA!");
                        } else {
                                calaixObert = true;
                                System.out.println("Obres el calaix. Es buit.");
                        }
                } else if (nom.equals("CAPSULA") && zona.buscarObjecte("CAPSULA") != null) {
                        if (ripley.isDespert()) {
                                System.out.println("La capsula ja es oberta.");
                        } else {
                                ripley.despertar();
                                System.out.println("Obres la capsula i la Ripley es desperta de mal humor.");
                        }
                } else {
                        System.out.println("No pots obrir " + nom + ".");
                }
        }

        private void tancar(String nom) {
                Zona zona = jugador.getZonaActual();

                if (nom.equals("")) {
                        System.out.println("Que vols tancar?");
                } else if (nom.startsWith("PORTA")) {
                        String direccio = direccioPorta(nom);
                        if (!direccio.equals("")) {
                                zona.getPorta(direccio).tancar();
                                System.out.println("Tanques la porta.");
                        }
                } else if (nom.equals("CALAIX") && zona.buscarObjecte("CALAIX") != null) {
                        calaixObert = false;
                        System.out.println("Tanques el calaix.");
                } else {
                        System.out.println("No pots tancar " + nom + ".");
                }
        }

        // Rep "PORTA N" o "PORTA NORD" i retorna "NORD" (o "" si no hi ha porta)
        private String direccioPorta(String text) {
                String[] paraules = text.split("\\s+");

                if (paraules.length < 2 || !esDireccio(paraules[1])) {
                        System.out.println("Quina porta? Per exemple: OBRIR PORTA NORD");
                        return "";
                }

                String direccio = direccioCompleta(paraules[1]);
                if (jugador.getZonaActual().getPorta(direccio) == null) {
                        System.out.println("Per aqui no hi ha cap porta.");
                        return "";
                }
                return direccio;
        }

        private void parlar(String complement) {
                if (complement.equals("")) {
                        System.out.println("Amb qui vols parlar? Per exemple: PARLAR IHALL hola");
                        return;
                }

                String[] parts = complement.split("\\s+", 2);
                String nom = parts[0];
                String frase = "";
                if (parts.length > 1) {
                        frase = parts[1];
                }

                Zona zona = jugador.getZonaActual();
                Personatge personatge = null;

                if (nom.equals("IHALL")) {
                        personatge = ihall;
                } else if (nom.equals("RIPLEY") && ripley.getZonaActual() == zona) {
                        personatge = ripley;
                } else if (nom.equals("MALIEN") && malien.getZonaActual() == zona) {
                        personatge = malien;
                }

                if (personatge == null) {
                        System.out.println("Aqui no hi ha ningu que es digui " + nom + ".");
                        return;
                }

                System.out.println(personatge.getNom() + ": " + personatge.parlar(frase, jugador));
        }

        private void mostrarInventari() {
                jugador.getMotxilla().mostrarContingut();

                if (jugador.portaVestit()) {
                        System.out.println(" (Portes posat el vestit.)");
                }
                if (llanternaEncesa && jugador.getMotxilla().conte(llanterna)) {
                        System.out.println(" (La llanterna esta encesa.)");
                }
        }

        private void comprovarPersonatges() {
                Zona zona = jugador.getZonaActual();

                // La Ripley es menja els donuts si els troba
                if (ripley.isDespert() && ripley.getZonaActual().conte(donuts)) {
                        ripley.getZonaActual().treureObjecte(donuts);
                        System.out.println("iHall: Capita, la Ripley s'ha menjat els donuts!");
                }

                // Si la Ripley es troba el Malien, s'acaba la partida
                if (ripley.isDespert() && malien.potAtacar() && ripley.getZonaActual() == malien.getZonaActual()) {
                        acabarPartida("iHall: La Ripley s'ha trobat el Malien... La missio ha fracassat.", false);
                        return;
                }

                // El Malien primer avisa i a la seguent ordre ataca
                if (malien.potAtacar() && malien.getZonaActual() == zona) {
                        if (malienAvisat) {
                                acabarPartida("El Malien se t'abraona. No has fet res a temps.", false);
                                return;
                        }
                        System.out.println("EL MALIEN ES AQUI! Fuig o distreu-lo, rapid!");
                        malienAvisat = true;
                } else {
                        malienAvisat = false;
                }
        }

        private void acabarPartida(String missatge, boolean guanyat) {
                System.out.println();
                System.out.println(missatge);
                System.out.println();

                if (guanyat) {
                        System.out.println("*** HAS GUANYAT! ***");
                } else {
                        System.out.println("*** HAS PERDUT ***");
                }
                finalitzat = true;
        }

        private boolean esDireccio(String text) {
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
