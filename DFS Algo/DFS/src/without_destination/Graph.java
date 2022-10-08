package without_destination;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

class Graph {
	  private LinkedList<Integer> adj[];
	  private boolean visited[];

	  // Graph creation
	  Graph(int vertices) {
	    adj = new LinkedList[vertices];


	    for (int i = 0; i < vertices; i++)
	      adj[i] = new LinkedList<Integer>();
	  }

	  // Add edges
	  void addEdge(int src, int dest) {
	    adj[src].add(dest);
	  }

	  // DFS algorithm
	  void DFS(int vertex) {
		   visited = new boolean[adj.length];
	    visited[vertex] = true;
	    System.out.print(vertex + " ");

	    Iterator<Integer> ite = adj[vertex].listIterator();
	    while (ite.hasNext()) {
	      int neighbour = ite.next();
	      if (!visited[neighbour])
	        DFS(neighbour);
	    }
	  }
	  
	  public void DFSstack(int source)
	  {
		  boolean [] vis =new boolean[adj.length];
		  Stack<Integer> stack = new Stack();
		  stack.push(source);
		  System.out.println();
		  while(!stack.isEmpty())
		  {
			  int cur=stack.pop();
			  System.out.print(cur+"->");
			  
			  for(int neighbour : adj[source])
			  {
				  if(!vis[neighbour])
					{
					 vis[neighbour]=true;
					 stack.push(neighbour);
				  }
			  }
			  
		  }
 		  
	  }

	  public static void main(String args[]) {
	    Graph g = new Graph(4);

	    g.addEdge(0, 1);
	    g.addEdge(0, 2);
	    g.addEdge(1, 2);
	    g.addEdge(2, 3);

	    System.out.println("Following is Depth First Traversal");

	    g.DFS(2);
	    g.DFSstack(2);
	  }
	}