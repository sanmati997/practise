package leetcode;

public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      // similar to LCS
		
		int [][] dp;
		
		String a = "heap";
        String b = "pea";

        int n= a.length();
        int m= b.length(); 
        
        dp =new int[n+1][m+1];
        
        for(int i=0; i<=n ;i++)
        	for(int j=0 ;j<=m ;j++)
        		if(i==0 ||j==0)
        			dp[i][j]=0;
        
        
        for (int i=1 ;i<=n ;i++)
        {
        	for (int j=1 ;j<=m ;j++)
        	{
        		if(a.charAt(i-1)==b.charAt(j-1))
        			dp[i][j] =1+dp[i-1][j-1];
        		else
        			dp[i][j]=Math.max(dp[i-1][j], dp[i][j-1]);
        	}
        }
        System.out.println("LCS = "+dp[n][m]);
        System.out.println("insertion to convert string a to b = "+(b.length()-dp[n][m]));
        System.out.println("deletion to convert string a to b = "+(a.length()-dp[n][m]));

	}

}
