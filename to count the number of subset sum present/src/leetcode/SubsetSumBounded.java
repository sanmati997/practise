package leetcode;

public class SubsetSumBounded {
	public static void main(String[] args) {
		//similar to unbonded knapsackwith smaller changes
		int sum = 4;
		int	coin[] = {1,2,3,4};
		
//		Output: 4
//		Explanation: there are four solutions: . {} , {4}
	

		int n=coin.length;
		int m=sum;
		int [][]dp =new int[n+1][m+1];
		
		for(int i=0 ;i<=n ;i++)
			for(int j=0 ;j<=m;j++)
			{
				if(i==0 )
					dp[i][j]=0;
					if(j==0)
					dp[i][j]=1;
			}
		
		  
		for(int i=1 ;i<=n;i++)
			for(int j=1 ;j<=m;j++)
			{
				if(coin[i-1]<=j)
				{
					dp[i][j]=dp[i-1][j-coin[i-1]]+ dp[i-1][j];
				}
				else
					dp[i][j]=dp[i-1][j];
				}
	
	System.out.println(dp[n][m]);
	}


}
