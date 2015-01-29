package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;

public class Sphere extends Surface {
	public  Vector c; //centre
	public float radius;
	private Color color;
	
	public Sphere(Vector c, float r, Color color) {		
		this.c = c;
		this.radius = r;
		this.color = color;
		
		this.kA = 0.10f;
		this.kD = 0.50f;
		this.kS = 0.40f;
		this.n = 1f;
		
	}
	
	public Color getColor() {
		return color;
	}
	
	public Point intersect(Ray ray) {
		// ray R = O + sD
		// sphere (P-C)*(P-C) = r^2
		
		// oc = O-C
		Vector oc = Vector.sub(ray.origin, c);
		// a = D*D
		float a = Vector.dProd(ray.direction, ray.direction);
		// b = 2D*(O-C)
		float b =  Vector.dProd(ray.direction, oc)*2;
		// c = (O-C)*(O-C)-r^2
		float c = Vector.dProd(oc, oc) - radius*radius;
		// Discriminant d = sqrt(b^2 - 4ac)
		float d = (float) b*b - 4f*a*c;
		
		float s1, s2;
		if (d < 0) return null;
		else if (d == 0) {
			s1 = b*(-1)/(2*a);
			return ray.getPointAt(s1);
		} else {
			float dSqrt = (float) Math.sqrt(d);
			s1 = (-b+dSqrt)/(2*a);
			s2 = (-b-dSqrt)/(2*a);
			Point p1 = ray.getPointAt(s1);
			Point p2 = ray.getPointAt(s2);
			return (p1.length()<p2.length()) ? p1 : p2;
		}
	}
	
	public Vector normalAt(Point p) {
		//Check the point lies on the sphere
		//(P-C)*(P-C) - r^2 = 0
		Vector pc = Vector.sub(p, c);
		float d = Vector.dProd(pc, pc) - radius*radius;
		return pc;
	}
	
	public void pPrint() {
		System.out.println("Sphere with:");
		System.out.println("Vector: "+c.toString());
		System.out.println("Radius: "+radius);
		System.out.println("Color: "+color.toString()+"\n");
	}
}
