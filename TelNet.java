package Telefonnetz;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TelNet {

    int lbg;
    List<TelKnoten> telKnoten;
    List<TelVerbindung> telVerbindungen;
    List<TelVerbindung> minSpanTree;

    // Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
    public TelNet(int lbg) {
        this.lbg = lbg;
        telKnoten = new LinkedList<>();
        telVerbindungen = new LinkedList<>();
        minSpanTree = new LinkedList<>();
    }

    // Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    public boolean addTelKnoten(int x, int y) {
        TelKnoten tk = new TelKnoten(x, y);
        if (telKnoten.contains(tk)) return false;

        telKnoten.add(tk);

        for (TelKnoten tk2 : telKnoten) {
            if (tk.equals(tk2)) continue;
            int dist = distanz(tk, tk2);
            if (dist < lbg) {
                telVerbindungen.add(new TelVerbindung(tk, tk2, dist));
                telVerbindungen.add(new TelVerbindung(tk2, tk, dist));
            }
        }
        return true;
    }

    int distanz(TelKnoten a, TelKnoten b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    // Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
    // true, falls es einen minimal aufspannenden Baum gibt. Sonst false.
    public boolean computeOptTelNet() {
        List<TelVerbindung> spanTree = new LinkedList<>();
        UnionFind forest = new UnionFind(telKnoten.size());
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(telVerbindungen);

        while (forest.size() != 1 && !edges.isEmpty()) {
            TelVerbindung telVer = edges.poll();
            TelKnoten source = telVer.getSource();
            TelKnoten target = telVer.getTarget();
            int counter = 0;
            int one = 0;
            int two = 0;
            for (TelKnoten tk : telKnoten) {
                if (source.equals(tk)) one = counter;
                if (target.equals(tk)) two = counter;
                counter++;
            }

            int t1 = forest.find(one);
            int t2 = forest.find(two);

            if (t1 != t2) {
                forest.union(t1, t2);
                spanTree.add(telVer);
            }
        }

		if (edges.isEmpty() && forest.size() != 1) {
        	return false;
        } else {
            minSpanTree.addAll(spanTree);
            return true;
        }
    }

    // Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    public void	drawOptTelNet(int xMax, int yMax) throws IllegalStateException {
        checkSuccess();

        StdDraw.clear();
        StdDraw.setCanvasSize(xMax, yMax);
        for(TelVerbindung telVer : getOptTelNet()) {
            int x1 = telVer.getSource().x;
            int y1 = telVer.getSource().y;
            int x2 = telVer.getTarget().x;
            int y2 = telVer.getTarget().y;

            StdDraw.setPenColor(Color.BLUE);
            StdDraw.filledCircle(x1, y1, 3);
            StdDraw.filledCircle(x2, y2, 3);

            StdDraw.setPenColor(Color.GRAY);
            StdDraw.line(x1, y1, x2, y1);
            StdDraw.line(x2, y1, x2, y2);
        }
        StdDraw.show();
    }

    //Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    public void	generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i < n; i++) {
            int x = (int)(xMax * Math.random());
            int y = (int)(yMax * Math.random());
            addTelKnoten(x, y);
        }
    }

    // Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
    public List<TelVerbindung> getOptTelNet() throws IllegalStateException {
        checkSuccess();
        return minSpanTree;
    }

    // Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
    public int getOptTelNetKosten() {
        checkSuccess();

        List<TelVerbindung> verbindungen = getOptTelNet();
        int total = 0;
        for(TelVerbindung telVer : verbindungen) {
            total += telVer.getWeight();
        }
        return  total;
    }

    // Prüft, ob computeOptTelNet() erfolgreich ausgeführt wurde.
    private void checkSuccess() throws IllegalStateException {
        if (!computeOptTelNet()) throw new IllegalStateException("Der minimal aufspannende Baum wurde nicht gefunden");
    }

    public static void	main(String[] args) {
        TelNet net = new TelNet(1000);
        net.generateRandomTelNet(500, 500, 500);
        net.computeOptTelNet();
        net.drawOptTelNet(500, 500);
    }

    // Liefert die Anzahl der Knoten des Telefonnetzes zurück.
    public int size() {
        return telKnoten.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Telefonnetz mit " + size() + " Knoten: Start -(Gewicht)- Ziel\n");
        for(TelVerbindung telVer : getOptTelNet()) {
            sb.append(telVer.getSource() + " -(" + telVer.getWeight() + ")- " + telVer.getTarget() + "\n");
        }

        return sb.toString();
    }
}
