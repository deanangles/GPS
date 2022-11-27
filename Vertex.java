import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Vertex extends Rectangle{
	private final static int SIZE = 28;

	public enum enuState {
		UNSELECTED, START, END
	}

	private static int longestAddress = 1;
	public String symbol;
	public String address;
	public enuState state = enuState.UNSELECTED;

	public Vertex(String symbol, String address, int x, int y) {
		super(x, y, SIZE, SIZE);
		this.symbol = symbol;
		this.address = address;
		longestAddress = Math.max(longestAddress, address.length());
	}

	public void setSize(int size) {
		width = size;
		height = size;
	}

	@Override
	public String toString() {
		return String.format("%-" + (Graph.returnAddress ? longestAddress : 1) + "s",
				Graph.returnAddress ? address : symbol);
		// ...("%-1s", "A")
		// ...("%-15s", "2100 S St.")
	}

	public void draw(Graphics g) {
		if (state == enuState.UNSELECTED)
			g.setColor(Color.WHITE);
		else if (state == enuState.START)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
		g.drawString(symbol, x + SIZE / 2 - 3, y + SIZE / 2 + 3);
	}
}
