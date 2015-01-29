package uk.ac.cam.dc561.graphics.sup4;

import easel.* ;

public class LineDrawerMid2 implements Algorithm2D{

	public static int Round( double a )
	{
		return (int) Math.round(a) ; // Math.round returns (long)
	}

	public static void MidpointLine( double x0, double y0, double x1, double y1, int red, int green, int blue){
		
		// make sure that x0 < x1 so in octant 1, 2, 7, 8
		if( x0 > x1 ) {
			double xt = x0 ; x0 = x1 ; x1 = xt ;
			double yt = y0 ; y0 = y1 ; y1 = yt ;
		}

		// setup line variables for k=ax+by+c
		double a = y1 - y0;
		double b = x0 - x1;
		double c = x1*y0 - y1*x0;
		
		//This was originally under each subcase (should it vary?)
		int x = Round(x0) ;
		//int y = Round(( -a * ((double) x) - c ) / b) ;
		int y = Round(y0);
		
		// first octant
		if( a > 0 ) { // octant 1 or 2
			if ( a < -b ) { // octant 1
				double d = a * (x+1) + b * (y+0.5) + c ;

				while ( x <= Round(x1) ) {
					Renderer.setPixel(x, y, red, green, blue);
					if ( d < 0 ) {
						d = d + a;
					}
					else {
						y = y + 1;
						d = d + a + b;
					}
					x = x + 1;
				}
			}
			else { // octant 2
				double d = a * (x+0.5) + b * (y+1) + c ;

				while ( y <= Round(y1) ) {
					Renderer.setPixel(x, y, red, green, blue);
					if (d > 0) {
						d = d + b;
					}
					else {
						x = x + 1;
						d = d + a + b;
					}
					y = y + 1;
				}
			}
		}
		else { // octant 7 or 8
			if ( a < b ) { // octant 7
				double d = a * (x+0.5) + b * (y-1) + c ;

				while (y >= Round(y1)) {
					Renderer.setPixel(x, y, red, green, blue);
					if (d < 0) {
						d = d - b;
					}
					else {
						x = x + 1;
						d = d + a - b;
					}
					y = y - 1;
				}
			} else { // octant 8
				double d = a * (x+1) + b * (y-0.5) + c ;

				while (x <= Round(x1)) {
					Renderer.setPixel(x, y, red, green, blue);
					if (d > 0) {
						d = d + a;
					} else {
						y = y - 1;
						d = d + a - b;
					}
					x = x + 1;
				}
			}
		}
	}


	public void runAlgorithm( int width, int height ) {
		MidpointLine(25,25,45,40, 250,0,0); //O1
		MidpointLine(25,25,40,45, 0,250,0); //O2
		MidpointLine(25,25,35,5, 0,0,250); //O7
		MidpointLine(25,25,45,20, 100,100,100); //O8

		MidpointLine(25,25,5,15, 250,0,0); //O5
		MidpointLine(25,25,10,5, 0,250,0); //O6
		MidpointLine(25,25,15,45, 0,0,250); //O3
		MidpointLine(25,25,5,30, 100,100,100); //O8
		
		MidpointLine(30,30,30,0, 100,50,0);
		MidpointLine(30,0,30,30, 100,0,50);
	}

	public static void main( String[] args ) {
		Renderer.init2D( 50, 50, new LineDrawerMid2() );
	}
}
