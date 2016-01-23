
public class Test {
	public static void main(String[] args){
		UnionFind union = new UnionFind(20);
		System.out.println(union.size());
		union.union(4, 2);
		System.out.println(union.size());
		System.out.println(union.find(2));
		System.out.println(union.toString());
		
		TelNet net = new TelNet(10);
		net.addTelKnoten (1,1);
		net.addTelKnoten (2,4);
		net.addTelKnoten (3,5);
		net.addTelKnoten (7,9);
		
		System.out.println(net.connections);
		}
}
