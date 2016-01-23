import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TelNet {
	int lbg;
	public List<TelVerbindung> connections;
	public List<TelKnoten> edges;
	List<TelVerbindung> minSpanTree = new LinkedList<>();

	public TelNet(int lbg) {
		this.lbg = lbg;
		connections = new LinkedList<>();
		edges = new LinkedList<>();
	}

	public boolean addTelKnoten(int x, int y) {
		TelKnoten tk = new TelKnoten(x, y);
		if (edges.contains(tk))
			return false;

		edges.add(tk);
		return true;
	}

	public boolean computeOptTelNet() {
		UnionFind forest = new UnionFind(size());
		PriorityQueue<TelVerbindung> telverb = new PriorityQueue<>(connections);
		List<TelVerbindung> spantree = new LinkedList<>();

		while (forest.size() != 1 && !telverb.isEmpty()) {
			TelVerbindung vw = telverb.poll();
			TelKnoten v = vw.getSource();
			TelKnoten w = vw.getTarget();
			int counter = 0;
			int one = 0;
			int two = 0;
			for (TelKnoten tk : edges) {
				if (tk == v) {
					one = counter;
				}
				if (tk == w) {
					two = counter;
				}
				counter++;
			}
			int t1 = forest.find(one);
			int t2 = forest.find(two);

			if (t1 != t2) {
				forest.union(t1, t2);
				spantree.add(vw);
			}
		}
		if (telverb.isEmpty() && forest.size() != 1) {
			return false;
		}
		minSpanTree.addAll(spantree);
		return true;
	}

	public int size() {
		return edges.size();
	}

	public List<TelVerbindung> getOptTelNet()
            throws java.lang.IllegalStateException{
				return minSpanTree;
	}
	
	public int getOptTelNetKosten()
            throws java.lang.IllegalStateException{
		int gesKosten = 0;
		for(TelVerbindung telv : minSpanTree){
			gesKosten += telv.getWeight();
		}
		return gesKosten;
	}
	
	public void drawOptTelNet(int xMax, int yMax)
     throws java.lang.IllegalStateException{
		StdDraw.clear();
		StdDraw.setCanvasSize(xMax, yMax);
		List<TelVerbindung> list = getOptTelNet();
		float pen = (((float)1));
		float factorX = (float)1;
		float factorY = (float)1;
		for(TelVerbindung v : list){
			
			float x1 = (v.getSource().x) * factorX;
			float y1 = (v.getSource().y) * factorY;
			float x2 = (v.getTarget().x) * factorX;
			float y2 = (v.getTarget().y) * factorY;
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.filledCircle(x1,y1, pen);//Draws the Node 1
			StdDraw.filledCircle(x2, y2, pen);//Draws the Node2 
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(x1, y1, x2, y1); //Draws the Line x side			
			StdDraw.line(x2, y1, x2, y2);//Draws the Line y side
			
		}
		StdDraw.show();
	}
	
	public void generateRandomTelNet(int n, int xMax, int yMax){
		for(int i = 0; i < n; i++){
			int x = (int) (Math.random() * xMax);
			int y = (int) (Math.random() * yMax);
			addTelKnoten(x,y);
		}	
	}
	
}
