package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PixelPanel extends JPanel {
	private int pixW, pixH, zoom;
	private int width, height;
	private PixelArray pixelArray;
	
	public PixelPanel(PixelArray pixelArray, int zoom) {
		this.pixelArray = pixelArray;
		this.pixW = pixelArray.getPixW();
		this.pixH = pixelArray.getPixH();
		this.zoom = zoom;
		this.width = pixW*zoom;
		this.height = pixH*zoom;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	protected void paintComponent(Graphics g) {
		// Background colour
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, width, height);
		// Draw the pixel array
		render(g);
		
		// Draw lines at pixel edges
		if (zoom > 2) {
			g.setColor(java.awt.Color.LIGHT_GRAY);
			for (int i = 0; i<pixW; i++) {
				g.drawLine(i*zoom, 0, i*zoom, height);
			}
			for (int j = 0; j<pixH; j++) {
				g.drawLine(0, j*zoom, width, j*zoom);
			}
		}
	}	
	
	private void render(Graphics g) {
		for (int y = 0; y < pixH; ++y){
			for(int x = 0; x < pixW; ++x){
				renderPixel(g, x, y);
			}
			repaint(); // repaint at the end of each row
		}
	}
	
	private void renderPixel(Graphics g, int x, int y) {
		if (g.hitClip(x*zoom, y*zoom, zoom, zoom)) {
			Color c = pixelArray.getPixel(x, y);
			g.setColor(c==null ? java.awt.Color.white : c);
			g.fillRect(x*zoom, y*zoom, zoom, zoom);
		}
	}

	public void display(PixelArray pixelArray) {
		this.pixelArray = pixelArray;
		revalidate();
		repaint();
	}	
}
