package Telefonnetz;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TelNet {

    int lbg;
    List telKnoten;

    // Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
    public TelNet(int lbg) {
        this.lbg = lbg;
        telKnoten = new LinkedList<>();
    }

    // Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    public boolean addTelKnoten(int x, int y) {
        TelKnoten tk = new TelKnoten(x, y);
        if (telKnoten.contains(tk)) return false;

        telKnoten.add(tk);
        return true;
    }

    // Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
    // true, falls es einen minimal aufspannenden Baum gibt. Sonst false.
    public boolean computeOptTelNet() {
        UnionFind forest = new UnionFind(telKnoten.size());
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>();
        List<TelVerbindung> minSpanTree = new LinkedList<>();

        while (forest.size() != 1 && !edges.isEmpty()) {
            throw new NotImplementedException();
        }

        return false;
    }

    // Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    public void	drawOptTelNet(int xMax, int yMax) throws IllegalStateException {
        checkSuccess();

        StdDraw.setCanvasSize(xMax, yMax);
        for(TelVerbindung telVer : getOptTelNet()) {
            StdDraw.line(telVer.u.x, telVer.u.y, telVer.v.x, telVer.v.y);
        }
    }

    //Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    public void	generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i < n; i++) {
            int x = (int)(xMax * RNG.nextDouble());
            int y = (int)(yMax * RNG.nextDouble());
            telKnoten.add(new TelKnoten(x, y));
        }
    }

    // Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
    public List<TelVerbindung> getOptTelNet() throws IllegalStateException {
        checkSuccess();

        throw new NotImplementedException();
    }

    // Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
    public int getOptTelNetKosten() {
        checkSuccess();

        List<TelVerbindung> verbindungen = getOptTelNet();
        int total = 0;
        for(TelVerbindung telVer : verbindungen) {
            total += telVer.c;
        }
        return  total;
    }

    // Prüft, ob computeOptTelNet() erfolgreich ausgeführt wurde.
    private void checkSuccess() throws IllegalStateException {
        if (!computeOptTelNet()) throw new IllegalStateException("Der minimal aufspannende Baum wurde nicht gefunden");
    }

    static void	main(String[] args) {
        throw new NotImplementedException();
    }

    // Liefert die Anzahl der Knoten des Telefonnetzes zurück.
    public int size() {
        return telKnoten.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Telefonnetz mit " + size() + " Knoten: Start -(Gewicht)- Ziel\n");
        for(TelVerbindung telVer : getOptTelNet()) {
            sb.append(telVer.u + " -(" + telVer.c + ")- " + telVer.v + "\n");
        }

        return sb.toString();
    }
}
