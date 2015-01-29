package uk.ac.cam.dc561.graphics.sup1;

public class Ray extends MathObject {
	// Represent a ray as a Point and a direction Vector (distance variable not encoded)
	public Point origin;
	public Vector direction;
	
	public Ray() {
		this.origin = new Point();
		this.direction = new Vector(1f, 1f, 1f);
	}
	
	public Ray(Point o, Vector d) {
		this.origin = o;
		this.direction = d;
	}

	public Point getPointAt(float k) {
		float a1 = origin.x + direction.x*k;
		float a2 = origin.y + direction.y*k;
		float a3 = origin.z + direction.z*k;
		
		return new Point(a1, a2, a3);
		
	}
	
	public void pPrint() {
		System.out.println("Origin: " + origin.toString());
		System.out.println("Direction: " + direction.toString());
	}
	
}
