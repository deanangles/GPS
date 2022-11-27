import java.util.ArrayList;

public class Path implements Comparable<Path> {

	public ArrayList<Vertex> path;
	public int totalCost;

	public Path() {
		path = new ArrayList<Vertex>();
		totalCost = 0;
	}

	public Vertex getLast() {
		if (path.size() == 0)
			return null;
		return path.get(path.size() - 1);
	}

	public void addDist(Edge e) {
		if (contains(e.toVertex))
			return;
		path.add(e.toVertex);
		totalCost += e.distCost;
	}

	public void addTime(Edge e) {
		if (contains(e.toVertex))
			return;
		path.add(e.toVertex);
		totalCost += e.timeCost;
	}

	public void addFirst(Vertex v) {
		path.add(v);
	}

	public boolean contains(Vertex v) {
		for (Vertex v2 : path)
			if (v.symbol.equals(v2.symbol))
				return true;
		return false;
	}

	@Override
	public int compareTo(Path o) {
		return o.totalCost - totalCost;
	}

	public String toStringSymbol() {
		String ret = "";
		for (Vertex v : path) {
			ret += v.symbol + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}

	public String toStringAddress() {
		String ret = "";
		for (Vertex v : path) {
			ret += v.address + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}

	public Path copy() {
		Path ret = new Path();
		ret.path = (ArrayList<Vertex>) path.clone();
		ret.totalCost = totalCost;
		return ret;
	}
}
