package uk.ac.cam.dc561.graphics.sup4;

import java.util.ArrayList;

import easel.Algorithm2D;
import easel.Renderer;

class Point {
	double x,y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y +")";
	}
}


class Polygon {
	Point[] vertices;
	int degree;
	
	public Polygon(Point[] vertices) {
		this.vertices = vertices;
		this.degree = vertices.length;
	}
	
	public void draw(int red, int green, int blue) {
		double x0,x1,y0,y1;
		for (int i=0; i<vertices.length-1; i++) {
			x0 = vertices[i].x;
			x1 = vertices[i+1].x;
			y0 = vertices[i].y;
			y1 = vertices[i+1].y;	
			LineDrawerMid2.MidpointLine(x0,y0,x1, y1, red,green,blue);
		}
		
		if (degree > 2) {
			x0 = vertices[degree-1].x;
			y0 = vertices[degree-1].y;
			x1 = vertices[0].x;
			y1 = vertices[0].y;	
			LineDrawerMid2.MidpointLine(x0, y0, x1, y1, red,green,blue);
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Point p: vertices) {
			sb.append(p.toString() + "\n");
		}
		return sb.toString();
	}
}


public class Clipper implements Algorithm2D  {
	Polygon clipPolygon, subjectPolygon, clipped;
	
	public Clipper(Polygon clip) {
		this.clipPolygon = clip;
	}
	
	public Clipper(Polygon clip, Polygon subject) {
		this.clipPolygon = clip;
		this.subjectPolygon = subject;
	}
	
	public void runAlgorithm(int width, int height) {
		clipPolygon.draw(250, 0, 0);
		subjectPolygon.draw(0, 250, 0);
		
		ArrayList<Point> output = new ArrayList<Point>(subjectPolygon.degree);
		for (int i=0; i<subjectPolygon.degree; i++) {
			output.add(subjectPolygon.vertices[i]);
		}
				
		int size1 = clipPolygon.degree;
		for (int i=0; i<size1; i++) {			
			Point A,B, P,Q;			
			
			// A and B are the vertices of one clipper edge
			A = clipPolygon.vertices[(i + size1-1) %  size1];
			B = clipPolygon.vertices[i];
			
			ArrayList<Point> input = output;
			output = new ArrayList<Point>(input.size());

			int size2 = input.size();
			for (int j=0; j<size2; j++) {
				// P and Q are the vertices of a subejct Polygon edge
				P = input.get( (j + size2-1) %  size2 );
				Q = input.get(j);
						
				if (isInside(A,B,Q)) {
					if (!isInside(A,B,P)) {
						output.add(intersection(A,B,P,Q));
					}
					output.add(Q);
				} else if (isInside(A,B,P)) {
					output.add(intersection(A,B,P,Q));
				}
			}
		}
		
		Point[] clippedPoints = new Point[output.size()];
		for (int i=0; i<clippedPoints.length; i++) {
			clippedPoints[i] = output.get(i);
		}
		
		clipped = new Polygon(clippedPoints);		
		clipped.draw(0, 0, 250);
	
	}
	
	private boolean isInside(Point A, Point B, Point P) {
		double a = B.y - A.y;
		double b = A.x - B.x;
		double c = B.x * A.y - A.x * B.y;
		double k = a * P.x + b * P.y + c; 
		
		return (k<0); 
	}
	
	private Point intersection(Point A, Point B, Point P, Point Q) {		
		double a = B.y - A.y;
		double b = A.x - B.x;
		double c = B.x * A.y - A.x * B.y;
		
		double t = (a*P.x + b*P.y + c)/(a*(P.x - Q.x) + b*(P.y - Q.y));
		
		double x = (1-t)*P.x + t*Q.x;
		double y = (1-t)*P.y + t*Q.y;
		return new Point(x,y);

	}
	
	public static void main(String[] args) {
		Point[] clipVertices = {	
									new Point(10,10),
									new Point(40,10),
									new Point(45,20),
									new Point(40,30),
									new Point(20,30),
									new Point(10,15)};
		
		Point[] subjVertices = {	new Point(20,5),
									new Point(35,15),
									new Point(35,30),
									new Point(25,25),
									new Point(15,35),
									new Point(10,20)};
		
		Clipper clipper = new Clipper(new Polygon(clipVertices), new Polygon(subjVertices));		
		
		Renderer.init2D(50, 50, clipper);
	}
}
