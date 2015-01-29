package com.google.sites.justinscsstuff.jogl;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import uk.ac.cam.dc561.graphics.jogltutorial.Triangle;
import uk.ac.cam.dc561.graphics.jogltutorial.TriangleRenderer;

public class DisplayListRenderer implements TriangleRenderer {

   private int list;

   public void init(GL2 gl, Triangle[] triangles) {

      list = gl.glGenLists(1);

      gl.glNewList(list, GL2.GL_COMPILE);
      {
         gl.glBegin(GL.GL_TRIANGLES);
         for (int i = 0; i < triangles.length; i++) {
            for (int j = 0; j < 3; j++) {
               gl.glColor3fv(triangles[i].colors[j], 0);
               gl.glVertex3fv(triangles[i].vertices[j], 0);
            }
         }
         gl.glEnd();
      }
      gl.glEndList();
   }

   public void render(GL2 gl) {
      gl.glCallList(list);
   }

   public void dispose(GL2 gl) {
      gl.glDeleteLists(list, 1);
   }

   public String toString() {
      return "Display List";
   }
}
