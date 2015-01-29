package com.google.sites.justinscsstuff.jogl;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import uk.ac.cam.dc561.graphics.jogltutorial.Triangle;
import uk.ac.cam.dc561.graphics.jogltutorial.TriangleRenderer;

import com.jogamp.common.nio.Buffers;

public class VertexArrayRenderer implements TriangleRenderer {

   private int         totalNumVerts;
   private FloatBuffer vertexBuffer;
   private FloatBuffer colorBuffer;

   public void init(GL2 gl, Triangle[] triangles) {
      totalNumVerts = triangles.length * 3;

      vertexBuffer = Buffers.newDirectFloatBuffer(triangles.length * 9);
      colorBuffer = Buffers.newDirectFloatBuffer(triangles.length * 9);

      for (int i = 0; i < triangles.length; i++) {
         for (int j = 0; j < 3; j++) {
            vertexBuffer.put(triangles[i].vertices[j]);
            colorBuffer.put(triangles[i].colors[j]);
         }
      }
      vertexBuffer.rewind();
      colorBuffer.rewind();
   }

   public void render(GL2 gl) {
      gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

      gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);
      gl.glColorPointer(3, GL.GL_FLOAT, 0, colorBuffer);

      gl.glDrawArrays(GL.GL_TRIANGLES, 0, totalNumVerts);

      gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
      gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
   }

   public void dispose(GL2 gl) {
   }

   public String toString() {
      return "Vertex Array";
   }
}
