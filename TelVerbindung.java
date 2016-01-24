
public class TelVerbindung implements Comparable<TelVerbindung> {
	int c;
	TelKnoten u;
	TelKnoten v;
	
	public TelVerbindung(TelKnoten u, TelKnoten v, int c){
		this.u = u;
		this.v = v;
		this.c = c;
	}
	
	public int compareTo(TelVerbindung t2){
		if(this.c < t2.getWeight()){
			return 1;
		} else if (this.c > t2.getWeight()){
			return -1;
		} else return 0;
	}
	
	public int getWeight(){
		return this.c;
	}
	
	public TelKnoten getSource(){
		return u;
	}
	
	public TelKnoten getTarget(){
		return v;
	}
	
	
	public String toString(){
		return "Telefonverndung von " + getSource().toString() + 
				" nach " + getTarget().toString() + " mit dem Gewicht " + getWeight();
	}
}
