import java.util.Arrays;

public class UnionFind {

		private int[] p;
		private int[] rang;
	
	
	public UnionFind(int n){
		p = new int[n];
		rang = new int[n];
		for (int i = 0; i < p.length; i++){
			p[i] = 0;
		}
	}
	
	public int find(int e){
		if (p[e] == 0){
			return e;
		} else {
			return find(p[e]);
		}
	}
	
	public void union(int s1, int s2){
		int root1 = find(s1);
		int root2 = find(s2);
		
		if(root1 == root2) return;
		if(rang[root1] > rang[root2]){
			p[root2] = root1;
		} else if (rang[root2] > rang[root1]){
			p[root1] = root2;
		} else {
			p[root2] = root1;
			rang[root1]++;
		}
	}
	
	public int size(){
		int anzahlMengen = 0;
		for (int i = 0; i < p.length; i++){
			if(p[i] == -1) anzahlMengen++;
		}
		return anzahlMengen;
	}
	
	public String toString() {
	    return "<UnionFind\np " + Arrays.toString(p) + "\nr " + Arrays.toString(rang) + "\n>";
	 }
	
	

	
	
}
