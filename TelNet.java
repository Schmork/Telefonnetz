package Telefonnetz;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelNet {

    int lbg;
    Map<TelKnoten, Integer> telKnoten;
    List<TelVerbindung> telVerbindungen;
    List<TelVerbindung> minSpanTree;

    // Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
    public TelNet(int lbg) {
        this.lbg = lbg;
        telKnoten = new HashMap<>();
        telVerbindungen = new LinkedList<>();
        minSpanTree = new LinkedList<>();
    }

    // Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    public boolean addTelKnoten(int x, int y) {
        TelKnoten tk = new TelKnoten(x, y);
        if (telKnoten.containsKey(tk)) return false;

        telKnoten.put(tk, telKnoten.size());    // .size() entspricht dem zu vergebenden Index

        for (TelKnoten tk2 : telKnoten.keySet()) {
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

            int sourceID = telKnoten.get(source);
            int targetID = telKnoten.get(target);

            int t1 = forest.find(sourceID);
            int t2 = forest.find(targetID);

            if (t1 != t2) {
                forest.union(t1, t2);
                spanTree.add(telVer);
            }
        }

		if (edges.isEmpty() && forest.size() != 1) {
        	return false;
        } else {
            minSpanTree.clear();
            minSpanTree.addAll(spanTree);
            return true;
        }
    }

    // Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    public void	drawOptTelNet(int xMax, int yMax) throws IllegalStateException {
        checkSuccess();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // holt Bildschirmauflösung
        double width = screenSize.getWidth() - 5;
        double height = screenSize.getHeight() - 88;    // 88 scheint die kombinierte Höhe von Titel- und Menüleiste zu sein
        float aspect = (float)width / (float)height;

        StdDraw.setCanvasSize((int)width, (int)height);     // beinahe Vollbild
        StdDraw.setXscale(0, aspect);

        float factorX = 1f / xMax * aspect;
        float factorY = 1f / yMax;

        float radius = Math.min(factorX, factorY) * 0.45f;  // benachbarte Kreise würden überlappen falls > 0.5f

        for(TelVerbindung telVer : getOptTelNet()) {
            float x1 = telVer.getSource().x * factorX;
            float y1 = telVer.getSource().y * factorY;
            float x2 = telVer.getTarget().x * factorX;
            float y2 = telVer.getTarget().y * factorY;

            StdDraw.setPenColor(Color.BLUE);
            StdDraw.filledCircle(x1, y1, radius);
            StdDraw.filledCircle(x2, y2, radius);

            StdDraw.setPenColor(Color.GRAY);
            StdDraw.line(x1, y1, x2, y1);
            StdDraw.line(x2, y1, x2, y2);
        }
    }

    //Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    public void	generateRandomTelNet(int n, int xMax, int yMax) {

        int maxDurchlauf = 100000;              // wie oft soll ein neuer Ort gesucht werden, falls kein freier gefunden wurde?
        for (int i = 0; i < n; i++) {

            int x, y;
            boolean bereitsVorhanden = false;
            int durchlauf = 0;
            do {                                                // diese while-Schleife garantiert "n" Knoten statt "bis zu n"
                x = (int) (xMax * Math.random());
                y = (int) (yMax * Math.random());

                for (TelKnoten tk : telKnoten.keySet()) {        // haben wir an dieser Stelle bereits einen TelKnoten?
                    if (tk.x == x && tk.y == y) {
                        bereitsVorhanden = true;
                        break;
                    }
                }
            } while (bereitsVorhanden && durchlauf++ < maxDurchlauf);

            if (bereitsVorhanden) {
                System.out.println("Selbst nach " + maxDurchlauf + " Versuchen konnte kein freier Platz für Knoten " + telKnoten.size() + " gefunden werden.");
                System.exit(-maxDurchlauf);
            } else {
                addTelKnoten(x, y);
            }
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

        int lbg = 150;
        int xMax = 300;
        int yMax = 200;//xMax;
        int nodes = 200;

        TelNet net = new TelNet(lbg);
        net.generateRandomTelNet(nodes, xMax, yMax);
        net.computeOptTelNet();
        net.drawOptTelNet(xMax, yMax);
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
