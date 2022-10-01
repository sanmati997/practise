package Lcs;

public class lcsTopDown {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int [][] dp;
		
		String s1 = "AGGTAB";
        String s2 = "GXTXAYB";

        int n= s1.length();
        int m= s2.length(); 
        
        dp =new int[n+1][m+1];
        
        for(int i=0; i<=n ;i++)
        	for(int j=0 ;j<=m ;j++)
        		if(i==0 ||j==0)
        			dp[i][j]=0;
        
        
        for (int i=1 ;i<=n ;i++)
        {
        	for (int j=1 ;j<=m ;j++)
        	{
        		if(s1.charAt(i-1)==s2.charAt(j-1))
        			dp[i][j] =1+dp[i-1][j-1];
        		else
        			dp[i][j]=Math.max(dp[i-1][j], dp[i][j-1]);
        	}
        }
        
        System.out.println(dp[n][m]+"\n");
        
        for(int i=0; i<=n ;i++)
        {	System.out.println();
        	for(int j=0 ;j<=m ;j++)
        		System.out.print(dp[i][j]+" ");
        }
        
        while(n>0 && m>0)
        {
        	if(s1.charAt(n-1)==s2.charAt(m-1))
        	{
        		System.out.print(s1.charAt(n-1));
	        	n--;
	        	m--;
        	}
        	else
        	{
        	if(dp[n-1][m]>dp[n][m-1])
        		n--;
        	else
        		m--;
        	}
        }
        
	}

}
