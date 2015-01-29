package uk.ac.cam.dc561.graphics.sup1;

public class Point extends Vector {
	public Point() {
		super();
	}
	
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public boolean equals(Point p) {
		return ((x==p.x) && (y==p.y) && (z==p.z));
	}
	
}