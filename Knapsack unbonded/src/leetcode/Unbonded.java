package leetcode;

public class Unbonded {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int W = 9;
		int	val[] = {1, 4, 5, 7};
		int  wt[] = {1, 5, 4, 3};

		int n=wt.length;
		int m=W;
		int [][]dp =new int[n+1][m+1];
		
		for(int i=0 ;i<=n ;i++)
			for(int j=0 ;j<=m;j++)
			{
				if(i==0 && j==0)
					dp[i][j]=0;
			}
		
		  
		for(int i=1 ;i<=n;i++)
			for(int j=1 ;j<=m;j++)
			{
				if(wt[i-1]<=j)
				{
					dp[i][j]=Math.max(val[i-1]+dp[i][j-wt[i-1]], dp[i-1][j]);
				}
				else
					dp[i][j]=dp[i-1][j];
				}
	
	System.out.println(dp[n][m]);
	}

}
