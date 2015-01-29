package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;
import java.util.ArrayList;

public class Model {
	private ArrayList<Surface> objects;
	public Color ambientLight;
	public Light pointLight;
	
	public Model() {
		this.objects = new ArrayList<Surface>();
		ambientLight = new Color(0.3f, 0.3f, 0.3f);
		pointLight = new Light();
	}
	
	public Model(ArrayList<Surface> list) {
		this.objects = list;
	}
	
	public Model(ArrayList<Surface> list, Light light) {
		this.objects = list;
		this.pointLight = light;
	}
	
	
	public void addObject(Surface s) {objects.add(s);}
	public ArrayList<Surface> getObjects() {return objects;}

	public void setPointLight(Light light) {this.pointLight = light;}
	public void setAmbientLight(Color light) {this.ambientLight = light;}
	
	
	public void pPrint() {
		System.out.println("Scene has "+ getObjects().size() + " object(s)");
		
		for (Surface s: getObjects()) {
			s.pPrint();
		}
	}
}
