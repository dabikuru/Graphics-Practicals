package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;

public class Light {
	 Color intensity;
	Point origin;
	
	
	public Light() {
		this.intensity = new Color(0.2f,0.2f,0.2f);
		this.origin = new Point(0f, 0f, 0f);
	}
	
	public Light(Point p) {
		this.intensity = new Color(0.2f,0.2f,0.2f);
		this.origin = p;
	}
}
