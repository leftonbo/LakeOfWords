package models;

public class Color {
	public double r;
	public double g;
	public double b;
	
	public int encode() {
		int rr = (int)(r * 255);
		int rg = (int)(g * 255);
		int rb = (int)(b * 255);
		
		return (rr << 16) + (rg << 8) + rb;
	}
	
	public static Color decode(int c) {
		Color n = new Color();
		n.r = (double)((c >> 16) & 0xFF) / 255;
		n.g = (double)((c >>  8) & 0xFF) / 255;
		n.b = (double)( c        & 0xFF) / 255;
		return n;
	}
	
	public static Color blendWith(int c, int d, double leftper) {
		Color nc = decode(c);
		Color nd = decode(d);
		Color n = new Color();
		n.r = nc.r * leftper + nd.r * (1-leftper);
		n.g = nc.g * leftper + nd.g * (1-leftper);
		n.b = nc.b * leftper + nd.b * (1-leftper);
		return n;
	}
}
