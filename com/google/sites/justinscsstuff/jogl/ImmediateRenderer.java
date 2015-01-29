package com.google.sites.justinscsstuff.jogl;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import uk.ac.cam.dc561.graphics.jogltutorial.Triangle;
import uk.ac.cam.dc561.graphics.jogltutorial.TriangleRenderer;

public class ImmediateRenderer implements TriangleRenderer {

    private Triangle[] triangles;
    
    public void init(GL2 gl, Triangle[] triangles) {
        this.triangles = triangles;
    }

    public void render(GL2 gl) {
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < triangles.length; i++) {
            for (int j = 0; j < 3; j++) {
                gl.glColor3fv(triangles[i].colors[j], 0);
                gl.glVertex3fv(triangles[i].vertices[j], 0);
            } 
        }
        gl.glEnd();
    }

    public void dispose(GL2 gl) {
    }
    
    public String toString() {
        return "Immediate Mode";
    }
}
