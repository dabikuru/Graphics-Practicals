package com.google.sites.justinscsstuff.jogl;

import java.awt.Point;

import javax.media.opengl.GL;

public class Viewport {
   public final int   x, y, w, h;
   public final float aspect;

   public Viewport(int x, int y, int w, int h) {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      aspect = w / (float) h;
   }

   public Viewport(int[] values) {
      this.x = values[0];
      this.y = values[1];
      this.w = values[2];
      this.h = values[3];
      aspect = w / (float) h;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public int getW() {
      return w;
   }

   public int getH() {
      return h;
   }

   public float getAspect() {
      return aspect;
   }

   public boolean contains(Point p) {
      return p.x >= x && p.x <= x + w && p.y >= y && p.y <= y + h;
   }

   public int[] getValues() {
      return new int[] { x, y, w, h };
   }

   public void apply(GL gl) {
      gl.glViewport(x, y, w, h);
   }
}
