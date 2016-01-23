package Telefonnetz;

import java.util.Objects;

public class TelKnoten {
	public int x;
	public int y;
	
	public TelKnoten(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TelKnoten))
			return false;

		TelKnoten that = (TelKnoten) other;

		return this.x == that.x && this.y == that.y;
	}
}
