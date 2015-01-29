package uk.ac.cam.dc561.graphics.sup1;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


@SuppressWarnings("serial")
public class Framework extends JFrame {
	private PixelPanel pixelPanel;
	private PixelArray pixelArray;
	private int pixW, pixH, zoom;
	public Color BACKGROUND = java.awt.Color.BLACK;

	
	public Framework(PixelArray pixelArray, int zoom) {
		super();
		
		this.pixelArray = pixelArray;
		this.pixW = pixelArray.getPixW();
		this.pixH = pixelArray.getPixH();
		this.zoom = zoom;

		setSize(pixW*zoom+40, pixH*zoom+50);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JComponent pixelPanel = createPixelPanel();
		add(pixelPanel, BorderLayout.CENTER);
		
		this.resetScreen();
		this.setVisible(true);
	}
	
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch,title);
		component.setBorder(tb);
	}
	
	private JComponent createPixelPanel() {
		JPanel result = new JPanel();
		pixelPanel = new PixelPanel(pixelArray, zoom);
		addBorder(result, "");
		result.add(pixelPanel);
		return new JScrollPane(result);
	}

	public void resetScreen() {
		pixelPanel.display(pixelArray);
		repaint();
	}
}
