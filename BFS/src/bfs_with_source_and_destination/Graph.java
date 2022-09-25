package bfs_with_source_and_destination;


import java.util.LinkedList;
import java.util.Queue;
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
            "Following is Breath First Traversal "
            + "(starting from vertex 0)");
 
        // Function call
        System.out.println("\ndistance = "+g.BFS(0,3));
        System.out.println("=========================================================");
//        System.out.println("\nusing STACK \n"+g.DFSstack(0,3));
	}
	
	
	private int BFS(int source, int destination) {
		boolean []vis =new boolean[adj.length];
		int [] parent =new int[adj.length]; //to identify who introduced whom
		Queue<Integer>queue =new LinkedList<Integer>();
		
		vis[source]=true;
		queue.add(source);
		parent[source]=-1; 
		
		while(!queue.isEmpty())
		{
			int cur=queue.poll();
			
			for(int neighbour :adj[cur])
			{
                 if(!vis[neighbour])
                 {
                	 vis[neighbour]=true;
                	 queue.add(neighbour);
                	 parent[neighbour]=cur;
                 }
                 
           	}
		}
			int curr =destination;
			int distance=0;
			while(parent[curr]!=-1)
			{
				System.out.print(curr+"->");
				distance++;
				curr=parent[curr];
			}
		
			System.out.print(curr+"->");
		
		return distance ;
	}

	}
	





