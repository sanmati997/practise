package leetcode;

public class Solution {

	public static void main(String[] args) {
		String a="bbbab";
		
//				Input: s = "bbbab"
//				Output: 4
//				Explanation: One possible longest palindromic subsequence is "bbbb".
//	it is similar to LCS but in LPS only 1 string is given so we have to reverse that string & store in a variable & perform lcs  	
		StringBuffer sb= new StringBuffer(a);
        sb=sb.reverse();
        String b=sb.toString();
        
        
        int n=a.length();
        int m=n;
        int [][]dp= new int[n+1][m+1];
        
        for(int i=0;i<=n;i++)
        {
            for(int j=0;j<=m;j++)
            {
                if(i==0 ||j==0)
                    dp[i][j]=0;
            }
        }
        
         for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=m;j++)
            {
                if(a.charAt(i-1)==b.charAt(j-1))
                    dp[i][j]=dp[i-1][j-1]+1;
                else
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
            }
         }   
        
        System.out.println("length of planidromic subsequence is =" +dp[n][m]);

	}

}
