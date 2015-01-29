package uk.ac.cam.dc561.graphics.sup2;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;


public class MyGraphics extends GLCanvas implements GLEventListener {
	private double theta = 45.0; //angle of Look with screen
	private double phi = 0;      //rotaion angle
	private final float FOV = 70;
	
	private Icosphere sphere1;
	private Icosphere sphere2;
	private Icosphere sphere3;

	MyGraphics(GLCapabilities glc) {
		super(glc);
		this.addGLEventListener(this);
		
		float[] pos1 = {3f,-4f,2f};
        sphere1 = new Icosphere(5, pos1);
        
        float[] pos2 = {4f,5f,-3f};
        sphere2 = new Icosphere(3, pos2);
        
        float[] pos3 = {-10f,2f, 0f};
        sphere3 = new Icosphere(4, pos3);
	}
	
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClearColor(1f, 1f, 1f, 0f);
		
		// Prepare light parameters
        float SHINE_ALL_DIRECTIONS = 0f;
        float[] lightPos = {-1, 1, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.5f, 0.5f, 0.5f, 0f};
        float[] lightColorDiffuse = {1f, 1f, 1f, 0f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Enable lighting
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_NORMALIZE);
        
        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorDiffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
        
        // Material
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);        
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl);
		
		// Set camera.
        setCamera(gl, glu, 30);
       
        update();
        render(drawable);
	}
	
	private void setCamera(GL2 gl, GLU glu, float distance) {
		// Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        
        glu.gluPerspective(FOV, widthHeightRatio, 0.1, 100);
        glu.gluLookAt(Math.sin(theta)*30, 10, Math.cos(theta)*30, 0, 0, 0, 0, 1, 0);
        
        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
	
	public void dispose(GLAutoDrawable drawable) { }
	
	public void reshape(GLAutoDrawable drawable, int x,  int y, int width, int height) {}
	
	private void update() {
	    phi += 1;
	}
	
	private void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
	    GLU glu = GLU.createGLU(gl);
	    setCamera(gl, glu, 30);

	    // clear the color buffer
	    gl.glShadeModel(GL2.GL_SMOOTH);
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    gl.glClearColor(0.2f, 0.2f, 0.2f, 0f); 
	    gl.glClearDepth(1.0f);
	    gl.glEnable(GL2.GL_DEPTH_TEST);
	    gl.glDepthFunc(GL2.GL_LEQUAL);
	    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
  
	    sphere1.drawSphere(gl, phi, 4);
	    sphere2.drawSphere(gl, -2.0*phi, 4);
	    sphere3.drawSphere(gl, phi*0.5, 4);
	    
	}
}