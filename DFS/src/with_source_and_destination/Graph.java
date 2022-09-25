package with_source_and_destination;

import java.util.LinkedList;
import java.util.Stack;

public class Graph {

	LinkedList<Integer>adj[]; 
	Graph(int n)
	{
		adj= new LinkedList [n];
		
		for(int i=0;i<n;i++)
		{
			adj[i]=new LinkedList<Integer>();
		}
	}
	/****************adjancy list creation **************************************************/
	public void addEdge(int source,int destination)
	{   
		adj[source].add(destination);//for one way traversal
//		adj[destination].add(source); for two way traversal
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub


        Graph g = new Graph(4);
 
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
 
        System.out.println(
            "Following is Depth First Traversal "
            + "(starting from vertex 0)");
 
        // Function call
        System.out.println("\nusing recursion\n "+g.DFS(0,3));
        System.out.println("=========================================================");
        System.out.println("\nusing STACK \n"+g.DFSstack(0,3));
	}
	
	/*********************STACK************************************************/
	private boolean DFSstack(int source ,int destination) {
	
		boolean []vis =new boolean[adj.length];
		vis[source]=true;
		Stack<Integer>stack=new Stack<Integer>();
		stack.push(source);
		while(!stack.isEmpty())
		{
			int cur =stack.pop();
		    System.out.print(cur+"->");		
			if(cur==destination)
				return true;
			for(int neighbour:adj[cur])
			{
				if(!vis[neighbour])
				{
					vis[neighbour]=true;
					stack.push(neighbour);
					
				}
			}
		}
		return false;
	}
	
	
	/*********************RECURSION************************************************/
	private boolean DFS(int source,int destination) {		
		boolean [] vis =new boolean [adj.length];
		vis[source]=true;
		return DFSutil(source,destination,vis);
	}
	
	
	private boolean DFSutil(int source, int destination, boolean[] vis) {
		 System.out.print(source+"->");
		if(source==destination)
			return true;
		
		for(int neighbour: adj[source])
		{
			if(!vis[neighbour])
			{
				vis[neighbour]=true;
	             boolean isConnected =DFSutil(neighbour,destination,vis);
	             if(isConnected)
	             {
	            	
	            	 return true;
	             }
			}
		}
		
		return false;
				
	}
	

}
