package uk.ac.cam.dc561.graphics.sup2;

import javax.media.opengl.GL2;

public final class Icosphere {	
	private float radius;
	private float[] pos;
	private double[] colour;
	
	public Icosphere(float radius, float[] pos) {
		this.radius = radius;
		this.pos = pos;
		colour =  new double[]{Math.random(), Math.random(), Math.random()};		
	}
	
	public void drawSphere(GL2 gl, double phi, int iter) {
		gl.glPushMatrix();
			gl.glTranslatef(pos[0], pos[1], pos[2]);
			gl.glRotated(phi, 0.0f, 1.0f, 0.0f);
			gl.glScaled(radius, radius, radius);
			gl.glColor3dv(colour, 0);
			unitSphere(gl, iter);
		gl.glPopMatrix();	
	}
	
	private void unitSphere(GL2 gl, int iter) {
		/*This first set makes a smooth sphere with low iter
		* but we need to use constants...
		*/
		/*
		final float X =  0.525731112119133606f;
		final float Z = 0.850650808352039932f;
		 
		float[][] vertices = {    
		   {-X, 0f, Z,   0.1f, 0.1f, 1.0f},
		   {X, 0f, Z,    0.1f, 1.0f, 0.1f},
		   {-X, 0f, -Z,  0.1f, 1.0f, 1.0f},
		   {X, 0f, -Z,   0.1f, 0.1f, 0.1f},  
		   
		   {0f, Z, X,    1.0f, 0.1f, 1.0f},
		   {0f, Z, -X,   1.0f, 1.0f, 1.0f},
		   {0f, -Z, X,   0.5f, 0.1f, 1.0f},
		   {0f, -Z, -X,  0.5f, 1.0f, 0.1f},  
		   
		   {Z, X, 0f,    0.5f, 1.0f, 1.0f},
		   {-Z, X, 0f,   0.5f, 0.1f, 0.1f},
		   {Z, -X, 0f,   0.1f, 0.5f, 0.1f},
		   {-Z, -X, 0f,  1.0f, 0.1f, 0.1f} 
		};
		int[][]tris = { 
		   {0,4,1}, {0,9,4}, {9,5,4}, {4,5,8}, {4,8,1},    
		   {8,10,1}, {8,3,10}, {5,3,8}, {5,2,3}, {2,7,3},    
		   {7,10,3}, {7,6,10}, {7,11,6}, {11,0,6}, {0,1,6}, 
		   {6,1,10}, {9,0,11}, {9,11,2}, {9,2,5}, {7,2,11} };
		*/
		
		/* This set is more mathematically correct,
		* but leaves gaps in the sphere with low iter
		*/
		float t = (float) ((1.0 + Math.sqrt(5.0)) / 2.0);
		float[][] vertices = {
            // Position     Colour
    		{-1,  t,  0,    0.5f, 0.1f, 1.0f},
    		{ 1,  t,  0,    0.1f, 1.0f, 0.1f},
    		{-1, -t,  0,    0.1f, 1.0f, 1.0f},
    		{ 1, -t,  0,    1.0f, 0.1f, 1.0f},
    		
    		{ 0, -1,  t,    0.1f, 0.1f, 0.1f},
    		{ 0,  1,  t,    1.0f, 1.0f, 1.0f},
    		{ 0, -1, -t,    0.1f, 0.4f, 1.0f},
    		{ 0,  1, -t,    0.5f, 1.0f, 0.8f},

    		{ t,  0, -1,    0.5f, 1.0f, 1.0f},
    		{ t,  0,  1,    0.5f, 0.1f, 0.1f},
    		{-t,  0, -1,    0.1f, 0.5f, 0.1f},
    		{-t,  0,  1,    1.0f, 0.1f, 0.1f}
		};
		
		int[][] tris = {   
			// 5 faces around point 0
			{0, 11, 5},
			{0, 5, 1},
			{0, 1, 7},
			{0, 7, 10},
			{0, 10, 11},

			// 5 adjacent faces
			{1, 5, 9},
			{5, 11, 4},
			{11, 10, 2},
			{10, 7, 6},
			{7, 1, 8},

			// 5 faces around point 3
			{3, 9, 4},
			{3, 4, 2},
			{3, 2, 6},
			{3, 6, 8},
			{3, 8, 9},

			// 5 adjacent faces
			{4, 9, 5},
			{2, 4, 11},
			{6, 2, 10},
			{8, 6, 7},
			{9, 8, 1}
		};
	
		//gl.glColor3dv(colour, 0);
		for (int i = 0; i < 7; i++) { 	
			subdivide(gl, vertices[tris[i][0]], vertices[tris[i][1]], vertices[tris[i][2]], iter); 
		}
		//Break the loop into two, and change colour in between.
		//gl.glColor3d(1-colour[0]*0.5, 1-colour[1]*0.5, 1-colour[2]*0.5);
		for (int i = 7; i < 20; i++) { 	
			subdivide(gl, vertices[tris[i][0]], vertices[tris[i][1]], vertices[tris[i][2]], iter); 
		}
	}

	private static float[] normalize(float[] v) {    
		   float d = (float) Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]); 
		   float[] out = {v[0] /= d, v[1] /= d, v[2] /= d};
		   return out;
		}
		 
	private static float[] normcrossprod(float[] v1, float[] v2) { 
		float[] out = new float[3];
		out[0] = v1[1]*v2[2] - v1[2]*v2[1]; 
		out[1] = v1[2]*v2[0] - v1[0]*v2[2]; 
		out[2] = v1[0]*v2[1] - v1[1]*v2[0]; 
		return normalize(out);
	}
	
	private static void drawTriangle(GL2 gl, float[] v1, float[] v2, float[] v3) { 
	   gl.glBegin(GL2.GL_TRIANGLES); 
		   gl.glNormal3fv(v1,0);
		   gl.glVertex3fv(v1,0);
		   
		   gl.glNormal3fv(v2,0);
		   gl.glVertex3fv(v2,0); 
		   
		   gl.glNormal3fv(v3,0);
		   gl.glVertex3fv(v3,0);  
	   gl.glEnd();
	}
		
	private void subdivide(GL2 gl, float[] v1, float[] v2, float[] v3, int iter)
	{
		float[] v12 = new float[3];
		float[] v23 = new float[3];   
		float[] v31 = new float[3];
	 
	   if (iter == 0) {
		   drawTriangle(gl, normalize(v1), normalize(v2), normalize(v3));
	      return;
	   }
	   for (int i=0; i<3; i++) {
	      v12[i] = v1[i]+v2[i];
	      v23[i] = v2[i]+v3[i];
	      v31[i] = v3[i]+v1[i];
	   }
	   v12 = normalize(v12);
	   v23 = normalize(v23);
	   v31 = normalize(v31);
	   
	   if (iter==2) gl.glColor3d(colour[0], colour[1], colour[2]);
	   subdivide(gl, v1, v12, v31, iter-1);
	   if (iter==2) gl.glColor3d(colour[0]*2, colour[1]*2, colour[2]*2);
	   subdivide(gl, v2, v23, v12, iter-1);
	   if (iter==2) gl.glColor3d(1-colour[0], 1-colour[1], 1-colour[2]);
	   subdivide(gl, v3, v31, v23, iter-1);
	   if (iter==2) gl.glColor3d(1-colour[0]*2, 1-colour[1]*2, 1-colour[2]*2);
	   subdivide(gl, v12, v23, v31, iter-1);
	}
}
