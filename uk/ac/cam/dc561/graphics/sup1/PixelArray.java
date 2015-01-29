package uk.ac.cam.dc561.graphics.sup1;

import java.awt.Color;

public class PixelArray {

	private int pixW;
	private int pixH;
	private Color[][] pixels;

	public PixelArray(int pixW, int pixH) {
		this.pixW = pixW;
		this.pixH = pixH;
		pixels = new Color[pixH][pixW];
	}

	public int getPixW() {return pixW;}
	public int getPixH() {return pixH;}


	public Color getPixel(int col, int row) {
		if (row < 0 || row >= pixH) return null;
		if (col < 0 || col >= pixW) return null;

		return pixels[row][col];
	}

	public void setPixel(int col, int row, Color c) {
		if (row < 0 || row >= pixH) return;
		if (col < 0 || col >= pixW) return;

		pixels[row][col] = c;
	}
}
