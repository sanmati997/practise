package Lcs;

import java.util.Arrays;

public class LcsMemoization {

	static int [][]mem;
	public static void main(String[] args) {

		 String s1 = "AGGTAB";
        String s2 = "GXTXAYB";
		 
//		    char[] X=s1.toCharArray();
//		    char[] Y=s2.toCharArray();
		    int m = s1.length();
		    int n = s2.length();

		    mem= new int[m+1][n+1];
		    
		    for(int i=0 ;i<mem.length ;i++)
		    	Arrays.fill(mem[i],-1);
		    
		    int result=lcs(s1,s2,m,n);
		    System.out.println(result);
	}

	private static int lcs(String s1, String s2, int m, int n) {

		if(m==0 ||n==0)
		return 0;
		if(mem[m][n]!=-1)
			return  mem[m][n];
		if(s1.charAt(m-1)==s2.charAt(n-1))
			return mem[m][n] =1+lcs(s1,s2,m-1,n-1);
		else
			return mem[m][n]= Math.max(lcs(s1,s2,m,n-1), lcs(s1,s2,m,n-1));
	}
}
