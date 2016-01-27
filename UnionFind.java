package Telefonnetz;

import java.util.Arrays;

public class UnionFind {
	private int[] p, rang;
	/* Legt eine neue Union-Find-struktur
	 * mit der Partition {{1},{2},...,{n}} an */
	public UnionFind(int n){
		p = new int[n];
		rang = new int[n];
		for(int i = 0; i < p.length; i++){
			p[i] = -1;
		}
	}

	//Sucht die Menge, zu der e geh�rt
	public int find(int e){
		if (p[e] == -1){
			return e;
		} else {
			return find(p[e]);
		}
	}

	/*Liefert die Anzahl der Mengen in
	* der Partition zur�ck */
	public int size(){
		int anzahlMengen = 0;
		for (int i = 0; i < p.length; i++) {
			if (p[i] == -1) anzahlMengen++;
		}
		return anzahlMengen;
	}

	/*Vereinigt die Menge, zu der e1 geh�rt
	 * mit der Menge zu der e2 geh�rt */
	public void union (int e1, int e2){
		int root1 = find(e1);
		int root2 = find(e2);

		if (root1 == root2) return;

		if (rang[root1] > rang[root2]) {
			p[root2] = root1;
		} else if (rang[root2] > rang[root1]) {
			p[root1] = root2;
		} else {
			p[root2] = root1;
			rang[root1]++;
		}
	}

	public String toString(){
		return "<UnionFind\n p: " + Arrays.toString(p) + "\n r: " + Arrays.toString(rang) + "\n>";
	}
}