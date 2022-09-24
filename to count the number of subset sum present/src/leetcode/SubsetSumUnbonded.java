package leetcode;

public class SubsetSumUnbonded {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [ ]s= {1,2,3};
		int sum=4;
		
//		Output: 4
//		Explanation: there are four solutions: {1, 1, 1, 1}, {1, 1, 2}, {2, 2}, {1, 3}. 
	


		int n=s.length;
		int m=sum;
		int [][]dp=new int[n+1][m+1];
		
	for(int i=0;i<=n;i++)
		for(int j=0;j<=m;j++)
		{
			if(i==0)
				dp[i][j]=0;
			if(j==0)
				dp[i][j]=1;
		}
	
	for (int i=1;i<=n;i++)
		for(int j=1;j<=m;j++)
		{
			if(s[i-1]<=j)
				dp[i][j]=dp[i][j-s[i-1]]+dp[i-1][j];
			else
				dp[i][j]=dp[i-1][j];
		}
	System.out.println(dp[n][m]);
	
	
	
	
	}

}
