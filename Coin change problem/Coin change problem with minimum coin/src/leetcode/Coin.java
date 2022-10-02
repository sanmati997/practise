package leetcode;

public class Coin {

	public static void main(String[] args) {
		// similar to unbounded knapsack 
		
		int coin [] = {9, 6, 5, 1};
		int sum=11;
		
		int n= coin.length;
		int m =sum;
		int [][] dp =new int [n+1][m+1];
		
		
		for(int i=0 ;i<=n;i++)
		{
			for(int j=0 ;j<=m;j++)
			{
				if(j==0)
					dp[i][j]=0;
				if(i==0)
					dp[i][j]= Integer.MAX_VALUE-1;
			}
		}

		//used to initialize  1 row
		for(int j=1 ;j<=m ;j++)
		{
			if(j%coin[0]==0)
				dp[1][j]=j%coin[0];
			else
				dp[1][j]=Integer.MAX_VALUE-1;
		}
		
		for(int i=2;i<=n;i++)
		{
			for(int j=1;j<=m;j++)
			{
				if(coin[i-i]<=j)
					dp[i][j]=Math.min(dp[i][j-coin[i-1]]+1,dp[i-1][j]);
				else
                   dp[i][j]=dp[i-1][j];			    
			}
		}
		
		System.out.println(dp[n][m]);
	}

}
