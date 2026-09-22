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

}
