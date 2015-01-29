package com.google.sites.justinscsstuff.jogl;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

public abstract class AWTWindowProgram implements WindowProgram {

   static { GLProfile.initSingleton(); }
   
   public final GLUT  glut           = new GLUT();
   public final GLU   glu            = new GLU();

   private Animator   animator;
   protected Frame    frame;
   protected GLCanvas canvas;
   protected Viewport screen;

   private double     elapsedMS      = 0;
   private long       lastNanoTime   = 0;
   private double     fpsTimer       = 0;
   private double     fpsCheckTimeMS = 1000;
   private double     fps            = 0;

   public double getElapsedTimeMS() {
      return elapsedMS;
   }

   public double getFPS() {
      return fps;
   }

   public Frame getFrame() {
      return frame;
   }

   public GLCanvas getCanvas() {
      return canvas;
   }

   public GLAutoDrawable getGLDrawable() {
      return canvas;
   }

   public GLU getGLU() {
      return glu;
   }

   public GLUT getGLUT() {
      return glut;
   }

   public Viewport getWindowDimensions() {
      return screen;
   }

   public AWTWindowProgram(String title, int w, int h, GLCapabilities caps) {
      canvas = new GLCanvas(caps);
      screen = new Viewport(0, 0, w, h);

      canvas.addGLEventListener(this);

      frame = new Frame(title);
      frame.add(canvas);
      frame.setSize(w, h);
      frame.setLocationRelativeTo(null);
      animator = new Animator(canvas);
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            new Thread(new Runnable() {
               public void run() {
                  animator.stop();
                  System.exit(0);
               }
            }).start();
         }
      });

      frame.setVisible(true);
      animator.start();
      canvas.requestFocusInWindow();
   }

   /**
    * Called repeatedly by the GLAutoDrawable. This method calls the update and
    * render methods in order. This method also keeps tracks of the time elapsed
    * since it was last called.
    */
   public final void display(GLAutoDrawable drawable) {
      GL gl = drawable.getGL();

      long nanoTime = System.nanoTime();
      if (lastNanoTime > 0)
         elapsedMS = (nanoTime - lastNanoTime) / 10e5;
      lastNanoTime = nanoTime;

      fpsTimer += elapsedMS;
      if (fpsTimer >= fpsCheckTimeMS) {
         fps = 1000.0 / elapsedMS;
         fpsTimer -= fpsCheckTimeMS;
      }

      update(gl, elapsedMS);
      render(gl);
   }

   /**
    * First method called when OpenGL context is current. This method is called
    * only once by the GLAutoDrawable, and it should be used to setup necessary
    * data structures, lighting, etc.
    */
   public abstract void init(GLAutoDrawable drawable);

   /**
    * First method called in the animation loop. This method is where the world
    * state and input events should be handled.
    */
   public abstract void update(GL gl, double elapsedMS);

   /**
    * Second method called in the animation loop. This method is where all
    * rendering code should be placed: setting materials and lights, drawing
    * objects, etc.
    */
   public abstract void render(GL gl);

   public void reshape(GLAutoDrawable drawable, int x, int y, int width,
         int height) {
      screen = new Viewport(0, 0, width, height);
   }
}
