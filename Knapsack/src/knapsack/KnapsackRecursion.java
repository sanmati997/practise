package knapsack;

public class KnapsackRecursion {

	public static void main(String[] args)
	{
		 int val[] = { 60, 100, 120,90 };
		    int wt[] = { 10, 20, 30 ,20 };
		    int W = 50;
		    int m= val.length;
		    int n = wt.length;
		    int result=knapsack(wt,val,W,n,m);
		    System.out.println(result);
	}
	
	public static int knapsack(int []wt,int []val ,int W ,int n, int m)
	{
		if(W==0 ||n==0)
		{
			return 0;
		}
	
		if(wt[n-1]<=W)
			return Math.max(val[n-1]+knapsack(wt,val,W-wt[n-1],n-1,m-1) ,knapsack(wt,val,W,n-1,m-1));
			else
				return knapsack(wt,val,W,n-1,m-1);
			
	}

}
