
public class TelVerbindung implements Comparable<TelVerbindung>{
	public final int weight;
	public TelKnoten u;
	public TelKnoten v;
	
	public TelVerbindung(TelKnoten u, TelKnoten v, int w){
		this.u = u;
		this.v = v;
		this.weight = w;
	}
	
	@Override
	public int compareTo(TelVerbindung cmp) {
		if(weight > cmp.getWeight()){
			return 1;
		}
		if(weight < cmp.getWeight()){
			return -1;
		}
		return 0;
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public TelKnoten getSource(){
		return u;
	}
	
	public TelKnoten getTarget(){
		return v;
	}
	
	
	public String toString(){
		return "Telefonverndung von " + getSource().toString() + " nach " + getTarget().toString() + " mit dem Gewicht " + getWeight();
	}
}
