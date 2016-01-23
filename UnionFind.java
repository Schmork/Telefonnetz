package Telefonnetz;

public class UnionFind {
	public int p[];
	/* Legt eine neue Union-Find-struktur
	 * mit der Partition {{1},{2},...,{n}} an */
	public UnionFind(int n){
		p = new int[n];
		for(int i = 0; i < p.length; i++){
			p[i] = 0;
		}
	}
	
	//Sucht die Menge, zu der e gehört
	public int find(int e){
		if(p[e] == 0)
			return e;
		else
			return find(p[e]);
	}
	
	/*Liefert die Anzahl der Mengen in
	* der Partition zurück */
	public int size(){
		int length = 0;
		for(int i = 1; i < p.length; i++){
			if(p[i] == 0){
				length++;
			}
		}
		return length;
	}
	
	/*Vereinigt die Menge, zu der e1 gehört
	 * mit der Menge zu der e2 gehört */
	public void union (int e1, int e2){
		if(find(e1) != find(e2)){
			int sizeE1 = 0;
			int sizeE2 = 0;
			treeSize(find(e1), sizeE1);
			treeSize(find(e2), sizeE2);
			if(sizeE1 < sizeE2)
				p[e1] = e2;
			else 
				p[e2] = e1;
		} else {
			System.out.println("union: Beide Knoten gleich!");
		}
	}
	
	private void treeSize(int e, int counter){
		for(int i = 1; i < p.length; i++ ){
			if(p[i] == e){
				counter++;
				treeSize(i, counter);
			}
		}
	}
	
	public String toString(){
		String str = "";
		for(int i = 1; i < p.length; i++){
			str = str + "Element: " + i + ", p: " + p[i] + "\n";
		}
		return str;
	}
}