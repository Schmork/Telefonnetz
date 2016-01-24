import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TelNet {
	double lbg = Double.POSITIVE_INFINITY;
	public List<TelVerbindung> connections;
	public List<TelKnoten> knoten;
	List<TelVerbindung> minSpanTree = new LinkedList<>();
	int tXMax;
	int tYMax;
	

	public TelNet(int lbg) {
		this.lbg = lbg;
		connections = new LinkedList<>();
		knoten = new LinkedList<>();
		minSpanTree = new LinkedList<>();
	}

	public boolean addTelKnoten(int x, int y) {
		
		if(x > tXMax)
			tXMax = x;
		if(y > tYMax)
			tYMax = y;
		TelKnoten v = new TelKnoten(x,y);
		if(knoten.contains(v)){
			return false;
		}
		else
			knoten.add(v);
		for(TelKnoten n : knoten){
			if(n != v){
			int cost = (int)cost(n,v);
				if(cost < lbg){
					TelVerbindung c = new TelVerbindung(n,v,cost);
					TelVerbindung cr = new TelVerbindung(v,n,cost);
					connections.add(c);
					connections.add(cr);
				}
			}
		}
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
			for (TelKnoten tk : knoten) {
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
//		if (telverb.isEmpty() && forest.size() != 1) {
//			return false;
//		}
		minSpanTree.addAll(spantree);
		return true;
	}

	public int size() {
		return knoten.size();
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
		float pen = (((float)1)/(tXMax));
		float factorX = (float)1/(tXMax);
		float factorY = (float)1/(tYMax);
		for(TelVerbindung v : list){
			
			float x1 = (v.getSource().getX()) * factorX;
			float y1 = (v.getSource().getY()) * factorY;
			float x2 = (v.getTarget().getX()) * factorX;
			float y2 = (v.getTarget().getY()) * factorY;
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.filledCircle(x1,y1, pen);
			StdDraw.filledCircle(x2, y2, pen);
			StdDraw.setPenColor(Color.GRAY);
			StdDraw.line(x1, y1, x2, y1);	
			StdDraw.line(x2, y1, x2, y2);
			
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
	
	public static void main(String[] args){
		TelNet net = new TelNet(100);		
		net.generateRandomTelNet(1000, 1000, 1000);
		net.computeOptTelNet();
		net.drawOptTelNet(700,700);
		
	}
	
	public double cost(TelKnoten a, TelKnoten b){
		int dist = Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
		return dist;
	}
}
