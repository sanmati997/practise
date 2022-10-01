package LCS;

public class LcsTopDown {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String X = "abcdxyz";
        String Y = "xyzabcd";
        int m = X.length();
        int n = Y.length();
        int [][] dp= new int[m+1][n+1];
        
        for(int i=0; i<=m ;i++)
        	for(int j=0; j<=n;j++)
        		if(i==0 ||j==0)
        			dp[i][j]=0;
        
	
        for(int i=1; i<=m; i++)
        	for(int j=1 ; j<=n ;j++)
        	{
        		if(X.charAt(i-1)==Y.charAt(j-1))
        			dp[i][j]=dp[i-1][j-1]+1;
        		else
        			dp[i][j]=0;
        	}
        int result =Integer.MIN_VALUE;
        for(int i=0 ;i<=m ;i++)
        {
        for(int j=0 ;j<=n ;j++)
	       if(result<dp[i][j])
	    	   result=dp[i][j];
        }
        
   	 System.out.println(result);

	
	}

}
