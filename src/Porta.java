public class Porta {

    private Zona zonaA;
    private Zona zonaB;
    private boolean oberta;

    public Porta(Zona zonaA, Zona zonaB) {
        this.zonaA = zonaA;
        this.zonaB = zonaB;
        this.oberta = false;
    }

    public boolean isOberta() {
        return oberta;
    }

    public void obrir() {
        oberta = true;
    }

    public void tancar() {
        oberta = false;
    }

    public Zona getAltraZona(Zona zona) {
        if (zona == zonaA) {
            return zonaB;
        }
        return zonaA;
    }
}
