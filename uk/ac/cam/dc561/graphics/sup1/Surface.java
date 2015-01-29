package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;

public abstract class Surface extends MathObject {	
	//protected Color color;
	protected float kA, kD, kS; //shading constants
	protected float n; //Phong 'roughness' coefficient
	
	public abstract Point intersect(Ray r);
	public abstract Vector normalAt(Point p);
	public abstract Color getColor();
}
