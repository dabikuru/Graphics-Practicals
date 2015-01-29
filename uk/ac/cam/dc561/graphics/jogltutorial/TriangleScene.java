package uk.ac.cam.dc561.graphics.jogltutorial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.Timer;

import com.google.sites.justinscsstuff.jogl.AWTWindowProgram;
import com.google.sites.justinscsstuff.jogl.DisplayListRenderer;
import com.google.sites.justinscsstuff.jogl.GLInfo;
import com.google.sites.justinscsstuff.jogl.ImmediateRenderer;
import com.google.sites.justinscsstuff.jogl.VBORenderer;
import com.google.sites.justinscsstuff.jogl.VertexArrayRenderer;

public class TriangleScene extends AWTWindowProgram implements KeyListener {
	private List<TriangleRenderer> renderers;
    private Triangle[]             triangles;
    private double                 cameraTheta      = 0;
    private int                    framesRendered   = 0;
    private int                    selectedRenderer = 0;

    public TriangleScene(String title, int w, int h, GLCapabilities caps,
            int numTriangles) {
        super(title, w, h, caps);

        // create triangle blob
        triangles = new Triangle[numTriangles];
        for (int i = 0; i < numTriangles; i++)
            triangles[i] = new Triangle();
        
        getCanvas().addKeyListener(this);
    }
    
    public void update(GL gl) {}

    public void dispose(GLAutoDrawable drawable) {
        for (TriangleRenderer tr : renderers)
            tr.dispose(drawable.getGL().getGL2());
    }
    
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // get OpenGL support / extension information
        GLInfo info = new GLInfo(drawable.getGL());
        info.print();

        renderers = new ArrayList<TriangleRenderer>();
        renderers.add(new ImmediateRenderer());
        renderers.add(new VertexArrayRenderer());
        renderers.add(new DisplayListRenderer());
        if (info.extSupported("GL_ARB_vertex_buffer_object"))
            renderers.add(new VBORenderer());
        for (TriangleRenderer renderer : renderers)
            renderer.init(gl, triangles);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
    }

    public void update(GL gl, double elapsedMS) {
        cameraTheta += 0.001;
    }
    
    public void render(GL glObj) {
        GL2 gl = glObj.getGL2();

        // perspective projection
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, getWindowDimensions().aspect, 0.1, 100);
        
        // rotate camera position around origin
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(Math.sin(cameraTheta) * 30, 10,
                Math.cos(cameraTheta) * 30, 0, 0, 0, 0, 1, 0);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        renderers.get(selectedRenderer).render(gl);

        framesRendered++;
    }
    
    /** Updates window title to display renderer name and last FPS */
    public void updateFPS() {
        String renderer = renderers.get(selectedRenderer).toString();
        getFrame().setTitle(renderer + " | FPS: " + framesRendered);
        framesRendered = 0;
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            selectedRenderer = (selectedRenderer + 1) % renderers.size();
            updateFPS();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
    
public static void main(String[] args) { 
        // if user enters a number in command-line args, use it for number of
        // triangles; otherwise, use 10,000 triangles
        int numTriangles = args.length > 0 ? Integer.parseInt(args[0]) : 10000;
        
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        final TriangleScene prog = new TriangleScene("Triangle Blob",
                600, 600, caps, numTriangles);
        
        // timer thread that gives a rough estimate of FPS
        Timer fpsTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prog.updateFPS();
            }
        });
        fpsTimer.setRepeats(true);
        fpsTimer.start();
    }
    
}
