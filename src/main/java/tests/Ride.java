package tests;

public class Ride {

	private int a;
	private int b;
	private int x;
	private int y;
	private int s;
	private int f;
	private int id;

	public Ride(int a, int b, int x, int y, int s, int f, int id) {

		this.setA(a);
		this.setB(b);
		this.setX(x);
		this.setY(y);
		this.setS(s);
		this.setF(f);
		this.setId(id);
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getManhattanDistance() {
		return Math.abs(this.a - this.x) + Math.abs(this.b - this.y);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
