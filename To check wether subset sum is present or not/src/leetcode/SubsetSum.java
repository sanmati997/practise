package leetcode;

public class SubsetSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int []s = {1,2,3,4};
//		 int sum =4;
		int sum=0;
		 int m= sum;
		 int n=s.length;
		 boolean [][]dp =new boolean[n+1][m+1];
		 
		 for(int i=0;i<=n;i++)
			 for(int j=0;j<=m;j++)
			 {
				 if(i==0)
					 dp[i][j]=false;
				 if(j==0)
					 dp[i][j]=true;
			 }
		 
		 for(int i=1;i<=n;i++)
			 for(int j=1;j<=m;j++)
			 {
				 if(s[i-1]<=j)
					 dp[i][j]=dp[i-1][j-s[i-1]]||dp[i-1][j];

				 else
					 dp[i][j]=dp[i-1][j];
			 }
		 
		 System.out.println(dp[n][m]); 

	}

}
