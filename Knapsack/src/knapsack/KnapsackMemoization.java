package knapsack;

import java.util.Arrays;

public class KnapsackMemoization {

	static int [][] mem;
	public static void main(String[] args) {
	
		
		 int val[] = { 60, 100, 120,90 };
		    int wt[] = { 10, 20, 30 ,20 };
		    int W = 50;
		    int m= val.length;
		    int n = wt.length;
		     mem= new int [n+1][W+1];
		     
		     for(int x=0;x<mem.length;x++)
		    	    Arrays.fill( mem[x], -1 );
		    
		    int result=knapsack(wt,val,W,n);
		    System.out.println(result);

	}
	private static int knapsack(int[] wt, int[] val, int W, int n) {
		// TODO Auto-generated method stub
		if(n==0 || W==0)
			return 0;
		if(mem[n][W]!=-1)
			return mem[n][W];
		if(wt[n-1]<=W)
			return mem[n][W]= Math.max(val[n-1]+knapsack(wt,val,W-wt[n-1],n-1), knapsack(wt,val,W,n-1));
		else
			return mem[n][W]= knapsack(wt,val,W,n-1);
	}

}
