package uk.ac.cam.dc561.graphics.sup1;

public class Vector extends MathObject {
	public float x,y,z;
	
	public Vector() {
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float length() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public void normalise() {
		float l = this.length();
		x = x/l;
		y = y/l;
		z = z/l;
	}
	
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
	}
	
	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x-b.x, a.y-b.y, a.z-b.z);
	}
	
	
	public static Vector not(Vector v) {
		return new Vector(0f-v.x, 0f-v.y, 0f-v.z);
	}
	
	
	public  static Vector kProd(Vector v, float k) {
		return new Vector(v.x*k, v.y*k, v.z*k);
	}


	public static float dProd(Vector v, Vector w) {
		return (v.x*w.x + v.y*w.y + v.z*w.z);
	}
	
	
	public Vector xProd(Vector v) {
		float c1 = (y*v.z - z*v.y);
		float c2 = -(x*v.z - z*v.x);
		float c3 = (x*v.y - y*v.x);
		return new Vector(c1,c2,c3);
	}
	
	public float[] toArray() {return new float[]{x,y,z};}
	public String toString() {return "(x="+x + ", y="+y + ", z="+z +")";}
	
	public void pPrint() {
		System.out.println(toString());
	}
}
