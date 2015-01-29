package uk.ac.cam.dc561.graphics.jogltutorial;

import javax.media.opengl.GL2;

public interface TriangleRenderer {
	public void init(GL2 gl, Triangle[] triangles);
	public void render(GL2 gl);
	public void dispose(GL2 gl);
}
