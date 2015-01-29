package uk.ac.cam.dc561.graphics.jogltutorial;

public class Triangle {
	//each triangle consists of 3 vertices and a color for each vertex
	
	public final float[][] vertices;
	public final float[][] colors;
	
	public Triangle() {
		vertices = new float[3][3];
		vertices[0] = createRandomPoint(new float[] { 0, 0, 0 }, 15);
		vertices[1] = createRandomPoint(vertices[0], 2); //new point within 2/2 units from v0
		vertices[2] = createRandomPoint(vertices[0], 2);
		
		colors = new float[3][3];
	      for (int i = 0; i < 3; i++)
	         for (int j = 0; j < 3; j++)
	            colors[i][j] = (float) Math.random();
	}

	private static float[] createRandomPoint(float[] p, float dist) {
	    //new point within a cube area: keep it within dist/2 units from given point  
		return new float[] { 
	         (float) ((Math.random() - 0.5) * dist + p[0]),
	         (float) ((Math.random() - 0.5) * dist + p[1]),
	         (float) ((Math.random() - 0.5) * dist + p[2]) };
	}
}
