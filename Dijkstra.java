import java.util.ArrayList;

public class Dijkstra {
	public static int totalCost;
	
	public static Path shortestPath(Graph g, Vertex start, Vertex end) {
		
		Path first= new Path();
		first.addFirst(start);
		PriorityQueue<Path> pq= new PriorityQueue<Path>();
		Path ret= new Path();
		pq.add(first);
		ret.totalCost = Integer.MAX_VALUE;
		ArrayList<ArrayList<Vertex>> usedPaths= new ArrayList<ArrayList<Vertex>>();
		
		while(!pq.isEmpty()) {
			Path currPath= pq.remove();
			usedPaths.add(currPath.path);
			if(currPath.getLast().symbol.equals(end.symbol)) {
				if(currPath.totalCost<ret.totalCost) {
					ret= currPath;
				}
			}else if(currPath.totalCost<ret.totalCost){
				ArrayList<Edge> edges= g.getEdges(currPath.getLast());
				for(Edge e : edges) {
					Path newPath= currPath.copy();
					if(g.useDistCost) {
						newPath.addDist(e);
					}else {
						newPath.addTime(e);
					}
					if(!usedPaths.contains(newPath.path))
						pq.add(newPath);
				}
			}
		}
		totalCost= ret.totalCost;
		return ret;
	}
}
