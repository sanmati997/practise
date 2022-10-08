package leetcode;

import java.util.Arrays;

public class Island {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int M[][] = new int[][] { { 1, 1, 0, 0, 0 },
					              { 0, 1, 0, 0, 1 },
					              { 1, 0, 0, 1, 1 },
					              { 0, 0, 0, 0, 0 },
					              { 1, 0, 1, 0, 1 } };
					            
		int row=5, col=5;;
		boolean [][] vis =new boolean[row][col];					            
            
		 for (boolean[] rows : vis) 
	         Arrays.fill(rows, false); 
		
	int count =0;
	
	for(int i=0;i<M.length;i++)
	{
		for(int j=0;j<M[i].length;j++)
		{
			if(!vis[i][j] &&M[i][j]==1)
				count++;
			dfs(i,j,M,vis);
		}
	}
	
	System.out.println("number of island = "+count);
		
		
	}

	private static void dfs(int i, int j, int[][] m, boolean[][] vis) {
		// TODO Auto-generated method stub
		
		if(i<0|| j<0 ||i>m.length-1 ||j>m[i].length-1 ||vis[i][j]==true ||m[i][j]==0)
			return ;
		
		vis[i][j]=true;
		dfs(i-1,j,m,vis);
		dfs(i+1,j,m,vis);
		dfs(i,j+1,m,vis);
		dfs(i,j-1,m,vis);
		
		/*to check diagonally also call below recursive call*/
		dfs(i-1,j-i,m,vis);
		dfs(i-1,j+1,m,vis);
		dfs(i+1,j-1,m,vis);
		dfs(i+1,j+1,m,vis);
	}

}
