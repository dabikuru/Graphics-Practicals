package com.google.sites.justinscsstuff.jogl;

import javax.media.opengl.*;

/**
 * Checks the OpenGL / shading language versions and queries which extensions
 * are available.
 * 
 * @author Justin Stoecker
 */
public class GLInfo {

   private String basicInfo;
   private String extensions;

   public GLInfo(GL gl) {

      StringBuilder b = new StringBuilder();
      b.append("*** OpenGL Support Info ***\n");

      b.append(String.format("-%-25s: %s\n", "GL Version",
            gl.glGetString(GL.GL_VERSION)));
      if (gl instanceof GL2ES2) {
         b.append(String.format("-%-25s: %s\n", "SL Version",
               gl.glGetString(GL2ES2.GL_SHADING_LANGUAGE_VERSION)));
      }
      b.append(String.format("-%-25s: %s\n", "Vendor",
            gl.glGetString(GL.GL_VENDOR)));
      b.append(String.format("-%-25s: %s\n", "Renderer",
            gl.glGetString(GL.GL_RENDERER)));
      basicInfo = b.toString();

      extensions = gl.glGetString(GL.GL_EXTENSIONS);
   }

   /**
    * Checks if a particular extension is supported by the client's OpenGL
    * implementation.
    */
   public boolean extSupported(String ext) {
      return extensions == null ? false : extensions.indexOf(ext) != -1;
   }

   /**
    * Prints out basic info about client's OpenGL implementation. This requires
    * that the client info has been checked by calling GLInfo.check(GL gl).
    */
   public void print() {
      System.out.println(basicInfo);
   }

   /**
    * List all OpenGL extensions supported by the client
    */
   public void printExtensions() {
      System.out.println(extensions);
   }
}
