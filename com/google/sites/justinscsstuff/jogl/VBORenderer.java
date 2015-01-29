package com.google.sites.justinscsstuff.jogl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import uk.ac.cam.dc561.graphics.jogltutorial.Triangle;
import uk.ac.cam.dc561.graphics.jogltutorial.TriangleRenderer;

import com.jogamp.common.nio.Buffers;

public class VBORenderer implements TriangleRenderer {

   int totalNumVerts;
   int vbo;

   int vertexStride;
   int colorPointer;
   int vertexPointer;

   public void init(GL2 gl, Triangle[] triangles) {
      totalNumVerts = triangles.length * 3;

      // generate a VBO pointer / handle
      IntBuffer buf = Buffers.newDirectIntBuffer(1);
      gl.glGenBuffers(1, buf);
      vbo = buf.get();

      // interleave vertex / color data
      FloatBuffer data = Buffers.newDirectFloatBuffer(triangles.length * 18);
      for (int i = 0; i < triangles.length; i++) {
         for (int j = 0; j < 3; j++) {
            data.put(triangles[i].colors[j]);
            data.put(triangles[i].vertices[j]);
         }
      }
      data.rewind();

      int bytesPerFloat = Float.SIZE / Byte.SIZE;

      // transfer data to VBO
      int numBytes = data.capacity() * bytesPerFloat;
      gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
      gl.glBufferData(GL.GL_ARRAY_BUFFER, numBytes, data, GL.GL_STATIC_DRAW);
      gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

      vertexStride = 6 * bytesPerFloat;
      colorPointer = 0;
      vertexPointer = 3 * bytesPerFloat;
   }

   public void render(GL2 gl) {
      gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);

      gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

      gl.glColorPointer(3, GL.GL_FLOAT, vertexStride, colorPointer);
      gl.glVertexPointer(3, GL.GL_FLOAT, vertexStride, vertexPointer);

      gl.glDrawArrays(GL.GL_TRIANGLES, 0, totalNumVerts);

      gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
      gl.glDisableClientState(GL2.GL_COLOR_ARRAY);

      gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
   }

   public void dispose(GL2 gl) {
      gl.glDeleteBuffers(1, new int[] { vbo }, 0);
   }

   public String toString() {
      return "Vertex Buffer Object (VBO)";
   }
}
