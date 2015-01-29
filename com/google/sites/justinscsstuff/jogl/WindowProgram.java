package com.google.sites.justinscsstuff.jogl;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Interface for a single-window JOGL program.
 * 
 * @author Justin Stoecker
 */
public interface WindowProgram extends GLEventListener {

    public GLU getGLU();
    public GLUT getGLUT();
    public GLAutoDrawable getGLDrawable();
    public Viewport getWindowDimensions();
    
    public void update(GL gl);
    public void render(GL gl);
}
