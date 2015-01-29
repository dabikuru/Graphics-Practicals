package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;

public class RayTracer {
	/** This ray tracer is initialised with a pixelArray, a pixelSize and a Model
	 *  One ray is computed for each pixel in the array, using perspective projection 
	 */
	
	private PixelArray pixelArray;
	private int pixelSize;
	private Framework framework;
	
	private final Point E = new Point(0f,0f,0f);
	public static float DISTANCE = 10f;
	private final double theta;
	private final float t, r; //Top and Right screen coordinates
	
	private Ray ray;
	private Model model;
	
	public RayTracer(PixelArray pixelArray, int pixelSize, Model model) {
		this.pixelArray = pixelArray;
		this.pixelSize = pixelSize;
		this.framework = new Framework(pixelArray, pixelSize);
		this.model = model;
		
		this.theta = 7.0/18.0 * Math.PI; // vertical Field of Vision
		// tan(theta/2) = t/DISTANCE
		this.t = (float) (DISTANCE * Math.tan(theta/2.0)); // top edge of screen
		// (pixW / pixH) = r/t
		this.r = pixelArray.getPixW() * t / pixelArray.getPixH(); //right edge
	}
	
	
	public void run() {
		//Iterate through all pixels and compute a Ray
		for (int j=0; j<pixelArray.getPixH(); j++) {
			for (int i=0; i<pixelArray.getPixW(); i++) {
				ray = computeRay(i, j);
				// Compare all objects in the Model and compute intersection
				
				float closestDistance = Float.POSITIVE_INFINITY;
				//Color colorOfClosest = framework.BACKGROUND;
				Surface closestSurface = null;
				Point closestPoint = null;
				
				for (Surface s: model.getObjects()) {
					//Pick closest object and store color
					Point p = s.intersect(ray);
					if (p != null && p.length() <= closestDistance) {			
						closestDistance = p.length();
						//colorOfClosest = s.getColor();
						closestPoint = p;
						closestSurface = s;
					}
				}
				//Set pixel colour according to shading
				if (closestSurface == null) {
					pixelArray.setPixel(i, j, framework.BACKGROUND);
				} else {
					Color color = calculateColour(closestSurface, closestPoint);
					pixelArray.setPixel(i, j, color);
				}				
			}
		}
		framework.resetScreen();
	}
	
	
	private Ray computeRay(int i, int j) {
		//Compute ray using perspective projection	
		float u = -r + 2f*r*((float)i+0.5f)/pixelArray.getPixW(); // u = l + (r-l)(i+0.5)/pixW
		float v = -t + 2f*t*((float)j+0.5f)/pixelArray.getPixH(); // v = b + (t-b)(j+0.5)/pixH
		
		Vector d = new Vector(u, v, DISTANCE*(-1f));
		return new Ray(E, d);
	}

	private Color calculateColour(Surface object, Point p) {
		int r = object.getColor().getRed();
		int g = object.getColor().getGreen();
		int b = object.getColor().getBlue();
		Vector L,N;
		
		//Ambient light
		r *= (int) (model.ambientLight.getRed()   * object.kA);
		g *= (int) (model.ambientLight.getGreen() * object.kA);
		b *= (int) (model.ambientLight.getBlue()  * object.kA);
		
		L = Vector.sub(model.pointLight.origin, p);
		L.normalise(); //light unit vector
		N = object.normalAt(p);
		N.normalise(); //normal unit vector
		
		float ir = model.pointLight.intensity.getRed();
		float ig = model.pointLight.intensity.getGreen();
		float ib = model.pointLight.intensity.getBlue();
		
		Point offset = new Point(p.x+0.001f, p.y+0.001f, p.z+0.001f);
		Ray shadowRay = new Ray(offset, L);
		for (Surface s: model.getObjects()) {
			Point interx = s.intersect(shadowRay);
			if (interx != null && interx.length()>p.length()) {
				r = (r > 255) ? 255 : r;
				g = (g > 255) ? 255 : g;
				b = (b > 255) ? 255 : b;
				return new Color(r,g,b);	
			}
		}
		
		float dotLN = Vector.dProd(L,N);
		if (dotLN > 0f) {
			if (object.kD > 0f) {
				float diffuse = object.kD * dotLN;
				r += diffuse * ir;
				g += diffuse * ig;
				b += diffuse * ib;
			}
			if (object.kS > 0f) {
				// R = L - 2* |N| * (L*N)
				Vector R = Vector.sub(L, Vector.kProd(N, 2f*dotLN));
				float spec = Vector.dProd(ray.direction, R);
				if (spec>0) {
					spec = object.kS * (float) Math.pow((double) spec, (double) object.n);
					r += spec * ir;
					g += spec * ig;
					b += spec * ib;
				}
			}
		}
		
		r = (r > 255) ? 255 : r;
		g = (g > 255) ? 255 : g;
		b = (b > 255) ? 255 : b;
		return new Color(r,g,b);
	}

	
	
	public static void main(String[] args) {
		int pixW, pixH, pixelSize;
		
		if (args.length == 3) {
			try {
				pixW = Integer.parseInt(args[0]);
				pixH = Integer.parseInt(args[1]);
				pixelSize = Integer.parseInt(args[2]);
			} catch (Exception e) {
				System.err.println("Usage: <width in pixels> <height in pixels> <pixel size>");
				return;
			}
		} else {
			System.err.println("Usage: <width in pixels> <height in pixels> <pixel size>");
			return;
		}
					
		//Initialise object model
		Model model = new Model();
		
		//Add ambient and point lights
		model.setAmbientLight(new Color(0.3f, 0.3f, 1f));
		model.setPointLight(new Light(new Point(-40f, -30f, -20f)));
		
		//Add 4 sample spheres
		Vector c = new Vector(0f, 0f, -30f);
		float radius = 3;
		Sphere s = new Sphere(c,radius,java.awt.Color.ORANGE);
		model.addObject(s);
		
		c = new Vector(10f, -25f, -80f);
		radius = 6;
		s = new Sphere(c,radius,java.awt.Color.GREEN);
		model.addObject(s);
		
		c = new Vector(-8f, 10f, -30f);
		radius = 3;
		s = new Sphere(c,radius,java.awt.Color.MAGENTA);
		model.addObject(s);
		
		c = new Vector(5f, 0f, -20f);
		radius = 4;
		s = new Sphere(c,radius,java.awt.Color.RED);
		model.addObject(s);

		//Initialise the ray-tracer and create an image
		RayTracer rayTracer = new RayTracer(new PixelArray(pixW, pixH), pixelSize, model);
		rayTracer.run();	
	}
}
