package Telefonnetz;

public class TelVerbindung implements Comparable  {
	private int c;
	private TelKnoten u;
	private TelKnoten v;

	public TelVerbindung(TelKnoten u, TelKnoten v, int c){
		this.u = u;
		this.v = v;
		this.c = c;
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
		return "Verbindung von " + getSource().toString() +
				" nach " + getTarget().toString() + " mit Gewicht " + getWeight() + "\n";
	}

	@Override
	public int compareTo(Object o) {
		if (o.getClass() != getClass()) throw new IllegalArgumentException("Type mismatch. Expected: "
													+ getClass() + ". Received: " + o.getClass() + ".");
		TelVerbindung tv = (TelVerbindung) o;
		return Integer.compare(getWeight(), tv.getWeight());
	}
}