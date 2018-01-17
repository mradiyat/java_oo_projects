import java.io.*;
import java.util.*;

//Test change comment

/**
 * Adjacency list implementation of a weighted directed graph.
 * Parallel edges NOT allowed.
 * 
 * @author konstantinos
 *
 */
public class AdjListWDigraph {
	int numVertices;
	ArrayList<Hashtable<Integer,Double>> edges;
	
	public AdjListWDigraph(int n) {
		numVertices = n;
		edges = new ArrayList<Hashtable<Integer,Double>>(n);
		for (int i=0; i<n; i++)
			edges.add(new Hashtable<Integer,Double>());
	}
	
	public int getNumVertices() {
		return numVertices;
	}

	public boolean isThereEdge(int u, int v) {
		return edges.get(u).containsKey(v);
	}
	
	public boolean addEdge(int u, int v) {
		return addEdge(u,v,1.0);
	}

	public boolean addEdge(int u, int v, double weight) {
		if (edges.get(u).containsKey(v)) return false;
		edges.get(u).put(v,weight);
		return true;
	}
	
	public double getEdgeWeight(int u, int v) {
		if (!isThereEdge(u,v)) return -1;
		return edges.get(u).get(v);
	}
	
	// Checks if the graph is symmetric.
	public boolean isSymmetric() {
		for (int i=0; i<numVertices; i++) {
			Set<Map.Entry<Integer,Double>> successors = edges.get(i).entrySet();
			for (Map.Entry<Integer,Double> entry: successors) {
				int j = entry.getKey();
				double w = entry.getValue();
				if (!isThereEdge(j,i)) return false;
				if (getEdgeWeight(j,i) != w) return false;
			}
		}
		
		return true;
	}
	
	private class Triple implements Comparable<Triple> {
		public int vertex;
		public double d;
		public int prev;
		
		public Triple (int v, double d, int prev) {
			vertex = v;
			this.d = d;
			this.prev = prev;
		}
		
		public int compareTo(Triple o) {
			if (d < o.d)
				return -1;
			else if (d == o.d)
				return (vertex - o.vertex);
			else // d > o.d
				return 1;
		}
		
		public String toString() {
			return "(" + vertex + "," + d + "," + prev + ")";
		}
	}
	
	public boolean[] reachable(int u) {
		boolean[] reachables = new boolean[numVertices];
		if (u < 0 || u >= numVertices) {
			return reachables;
		}
		//search through our reachable vertices using a stack
		Stack<Integer> stack = new Stack<Integer>(); 
		//keep all vertices visited so we don't repeat (i.e. in the case of a cycle
		Set<Integer> visited = new HashSet<Integer>();
		stack.add(u);
		reachables[u] = true;
		while(!stack.isEmpty()) {
			int i = stack.pop();
			Set<Map.Entry<Integer,Double>> successors = edges.get(i).entrySet();
			for (Map.Entry<Integer,Double> entry: successors) {
				if (visited.contains(entry.getKey())) {
					continue;
				}
				int x = entry.getKey();
				visited.add(x);
				stack.push(x);
				reachables[x] = true;
			}
		}
		return reachables;
	}
	
	/**Returns number of distinct connected components based off of the
	 * undirected, unweighted version of the graph.
	 * 
	 * @return
	 */
	public int connectedComponents() {
		AdjListWDigraph copy = simpleClone();
		//tracks which component each vertex is in
		int[] componentTracker = new int[copy.numVertices]; 
		int components = 1;
		/*Iterate through the vertices. If a vertex is already part of 
		 * a component, skip it. 
		 * 
		 */
		for (int vertex = 0; vertex < numVertices; vertex++) {
			if (componentTracker[vertex] != 0) {
				continue;
			}
			/*Check which vertices are reachable via this vertex, and 
			 * make them all part of component i. 
			 */
			boolean[] connecteds = copy.reachable(vertex);
			for (int i = 0; i < connecteds.length; i++) {
				if (connecteds[i]) {
					componentTracker[i] = components;
				}
			}
			components++;
		}
		
		return components - 1;
	}
	/**Makes an undirected, unweighted clone of this graph and
	 * returns it
	 * 
	 * @return an unweighted undirected copy of this graph
	 */
	public AdjListWDigraph simpleClone() {
		AdjListWDigraph copy = new AdjListWDigraph(getNumVertices());
		for (int i=0; i<numVertices; i++) {
			Set<Map.Entry<Integer,Double>> successors = edges.get(i).entrySet();
			for (Map.Entry<Integer,Double> entry: successors) {
				int j = entry.getKey();
				copy.addEdge(i, j);
				copy.addEdge(j, i);
			}
		}
		return copy;
	}
	
	
	/**Returns shortest path between source and destination. 
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public double shortestPath(int source, int destination) {
		if (source < 0 || source >= numVertices || 
				destination < 0 || destination >= numVertices) {
			return -1;
		}
		//keep track of processed vertices
		boolean[] marker = new boolean[numVertices];
		double[] distances = new double[numVertices];
		//set distance to each as infinity
		for (int i = 0; i < distances.length; i++) {
			distances[i] = Double.POSITIVE_INFINITY;
		}
		distances[source] = 0;
		//Triples will keep track of next vertices to be processed
		PriorityQueue<Triple> q = new PriorityQueue<Triple>();
		q.add(new Triple(source, 0, source));
		while (!q.isEmpty()) {
			Triple nextVertex = q.poll();
			//if the polled vertex is already processed, ignore it
			if (marker[nextVertex.vertex]) {
				continue;
			}
			marker[nextVertex.vertex] = true;
			//add all of the vertex's successors to the queue
			//also update their min distances 
			Set<Map.Entry<Integer,Double>> successors = 
					edges.get(nextVertex.vertex).entrySet();
			for (Map.Entry<Integer, Double> entry : successors) {
				if (!marker[entry.getKey()]) {
					distances[entry.getKey()] = 
							Math.min(entry.getValue() + distances[nextVertex.vertex], distances[entry.getKey()]);
					q.add(new Triple(entry.getKey(), 
							distances[entry.getKey()], 
							nextVertex.vertex));
				}
			}
		}
		
		return distances[destination];
	}

	public LinkedList<Integer> tour(LinkedList<Integer> toVisit) {
		return null;
	}
	
	/**Returns a minimum spanning tree graph of the component that includes
	 * vertex u. Uses an average weighted clone of the graph. 
	 * 
	 * @param u Vertex to start with
	 * @return MST that includes u
	 */
	public AdjListWDigraph MST(int u) {
		AdjListWDigraph averagedGraph = averagedClone(); 
		AdjListWDigraph minSpanTree = new AdjListWDigraph(numVertices);
		//keep track of vertices that have been fully processed
		boolean[] marker = new boolean[numVertices];
		marker[u] = true;
		PriorityQueue<Triple> q = new PriorityQueue<Triple>();
		Set<Map.Entry<Integer,Double>> initSucc = averagedGraph.edges.get(u).entrySet();
		//initially add all of the vertices adjacent to u to a queue
		for (Map.Entry<Integer, Double> entry : initSucc) {
			q.add(new Triple(entry.getKey(), entry.getValue(), u));
		}
		//Prim's algorithm: poll vertex with smallest connection to the connected component
		while (!q.isEmpty()) {
			Triple nextVertex = q.poll();
			//if the polled vertex is already processed into tree, ignore it
			if (marker[nextVertex.vertex]) {
				continue;
			}
			marker[nextVertex.vertex] = true;
			minSpanTree.addEdge(nextVertex.prev, 
					nextVertex.vertex, nextVertex.d);
			//add all of the vertex's successors to the queue
			Set<Map.Entry<Integer,Double>> successors = 
					averagedGraph.edges.get(nextVertex.vertex).entrySet();
			for (Map.Entry<Integer, Double> entry : successors) {
				if (!marker[entry.getKey()]) {
					q.add(new Triple(entry.getKey(), 
							entry.getValue(), nextVertex.vertex));
				}
			}
		}
		return minSpanTree;
	}
	
	/**Makes an undirected, average-weighted clone of this graph and
	 * returns it. If two vertices are connected by two edges of 
	 * unequal weight, the cloned weight will be the average of 
	 * the two. 
	 * 
	 * @return an unweighted undirected copy of this graph
	 */
	public AdjListWDigraph averagedClone() {
		AdjListWDigraph copy = new AdjListWDigraph(getNumVertices());
		for (int i=0; i<numVertices; i++) {
			Set<Map.Entry<Integer,Double>> successors = edges.get(i).entrySet();
			for (Map.Entry<Integer,Double> entry: successors) {
				int j = entry.getKey();
				if (isThereEdge(j,i)) {
					double averageD = (getEdgeWeight(i, j) + 
							getEdgeWeight(j, i)) / 2;
					copy.addEdge(i, j, averageD);
					copy.addEdge(j, i, averageD);
				}
				else {
					copy.addEdge(i, j, getEdgeWeight(i, j));
					copy.addEdge(j, i, getEdgeWeight(i, j));
				}
			}
		}
		return copy;
	}
	public String toString() {
		StringBuilder str = new StringBuilder(
		        "n = " + numVertices + "\n");
		for (int k = 0; k < numVertices; k++) {
			str.append("vtx " + k + ": [");
			Map.Entry<Integer, Double>[] succ = new Map.Entry[0];
			succ = edges.get(k).entrySet().toArray(succ);
			if (succ.length > 0) {
				int v = succ[0].getKey();
				double weight = succ[0].getValue();
				str.append(v + ":" + weight);
			}
			for (int i = 1; i < succ.length; i++) {
				int v = succ[i].getKey();
				double weight = succ[i].getValue();
				str.append(", " + v + ":" + weight);
			}
			str.append("]\n");
		}
		return str.toString();
	}
		
	
	public static void main(String[] args) throws IOException{
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String inputFile = null;
		if (args.length < 1){
			System.out.println("Provide path to txtfile for the graph:");
			inputFile = console.readLine().trim();
		} else {
			inputFile = args[0];
		}
		
		Scanner graphScanner = null;
		try {
			graphScanner= new Scanner(new File(inputFile));
		} catch(FileNotFoundException e){
			e.printStackTrace();
			return;
		}
		
		int numberOfVertices = 0;
		if(graphScanner.hasNext()){
			String nv = graphScanner.nextLine();
			if(!nv.equals(""))
				numberOfVertices = Integer.parseInt(nv);
		}
		
		AdjListWDigraph g1 = new AdjListWDigraph(numberOfVertices);
		while(graphScanner.hasNextLine()){
			String line = graphScanner.nextLine();
			String[] terms = line.split("\t");
			if (terms.length < 2) continue;
			int fromNode = Integer.parseInt(terms[0]);
			int toNode = Integer.parseInt(terms[1]);
			double weight = -1.0;
			if (terms.length > 2){
				weight = Double.parseDouble(terms[2]);
			}
			if (weight>0.0)	g1.addEdge(fromNode, toNode, weight);
			else g1.addEdge(fromNode, toNode);
		}
		
		//System.out.println(g1);

		while (true) {
			System.out.println("\nInput your command:\n"
					+ "\treachable <node ID>          - Finds the nodes reachable from this node.\n"
					+ "\tconnected                	- Prints number of connected components in graph.\n"
					+ "\tshortestpath <source ID> <destination ID>       - Prints length of shortest path from source to dest.\n"
					+ "\tmst <source ID>				- Creates the minimum spanning tree of the component containing source and prints it.\n"
					+ "\ttour <node1> <node2> ...       - Prints tour going through node1, node2 etc.\n"
					+ "\texit                           - Exits the program."
					+ "");
			String temp = console.readLine().trim();
			String[] splitted = temp.split(" ");
			
			if (splitted[0].equals("reachable")) {
				if (splitted.length <2) continue;
				int index = Integer.parseInt(splitted[1]);
				boolean[] reached = g1.reachable(index);
				System.out.println(Arrays.toString(reached));
			} else if (splitted[0].equals("connected")) {
				int numComponents = g1.connectedComponents();
				System.out.println("Number of connected components:"+numComponents);
			} else if (splitted[0].equals("shortestpath")) {
				if (splitted.length <3) continue;
				int source = Integer.parseInt(splitted[1]);
				int dest = Integer.parseInt(splitted[2]);
				double shortestDist = g1.shortestPath(source, dest);
				System.out.println("Shortest path from "+source+" to "+dest+" is "+shortestDist);
			} else if (splitted[0].equals("mst")) {
				if (splitted.length <2) continue;
				int source = Integer.parseInt(splitted[1]);
				AdjListWDigraph mst = g1.MST(source);
				if (mst!=null)
					System.out.println(mst.toString());
			} else if (splitted[0].equals("tour")) {
				LinkedList<Integer> inputList = new LinkedList<Integer>();
				for(int i=1; i<splitted.length;i++){
					int nextNode = Integer.parseInt(splitted[i]);
					inputList.add(nextNode);
				}
				LinkedList<Integer> outputList = g1.tour(inputList);
				System.out.println(outputList.toString());
			} else if (splitted[0].equals("exit")) {
					break;
			} else {
				System.out.println("Incorrect input. Please try again.");
			}

		}
	}
	
}
