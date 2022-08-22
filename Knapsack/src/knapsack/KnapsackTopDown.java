package knapsack;

public class KnapsackTopDown {
	

	public static void main(String[] args) {
		
		
		 int val[] = { 60, 100, 120,90 ,7};
		    int wt[] = { 10, 20, 30 ,20 ,70};
		    int W = 50;
		    int m= val.length;
		    int n = wt.length;
		     int [][]dp= new int [n+1][W+1];
		     
		     for (int i=0; i<=n;i++)
		    	 for(int j=0;j<=W;j++) {
		    		 if(i==0)
		    			 dp[i][j]=0;
		    			 if(j==0)
		    			 dp[i][j]=0;
	}
		     for (int i=1; i<=n;i++)
	    	 for(int j=1;j<=W;j++)
	    	 {
	    		 if(wt[i-1]<=j)
	    			 dp[i][j]=Math.max(val[i-1]+dp[i-1][j-wt[i-1]], dp[i-1][j]);
	    		 else
	    			 dp[i][j]=dp[i-1][j];
	    			 
	    	 }
		     System.out.println(dp[n][W]);
	
	}
}
