
public class TelKnoten {
	int x;
	int y;
	public TelKnoten(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return "x Coordinate: " + x + ", y Coordinate: " + y;
	}
}
