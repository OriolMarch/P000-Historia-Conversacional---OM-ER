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
    
    }

    public void mostrarIntroduccio(){
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

}
