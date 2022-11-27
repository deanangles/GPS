import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Edge {
	public Vertex fromVertex;
	public Vertex toVertex;
	public int timeCost;
	public int distCost;

	public Edge(Vertex fromVertex, Vertex toVertex, int timeCost, int distCost) {
		super();
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.timeCost = timeCost;
		this.distCost = distCost;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s (%d %s)", fromVertex, toVertex, Graph.useDistCost ? distCost : timeCost,
				Graph.useDistCost ? "miles" : "minutes");
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		int off = fromVertex.width / 2;
		g.drawLine(fromVertex.x + off, fromVertex.y + off, toVertex.x + off, toVertex.y + off);
		fromVertex.draw(g);
		toVertex.draw(g);
	}
}
