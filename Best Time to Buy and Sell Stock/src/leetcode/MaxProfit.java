package leetcode;

public class MaxProfit {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int []prices = {4,7,1,2};
				//      [7,1,5,3,6,4]
				//[7,6,4,3,1]
       MaxProfit m=new MaxProfit();
       int max =m.profit(prices);
       
       System.out.println(max);
	}

	private int profit(int[] prices) {
		// TODO Auto-generated method stub
		int max=0,s=prices[0];
		
		for(int i=1 ;i<prices.length;i++)
		{
			if(prices[i]-s<s && prices[i]<s)
				s= prices[i];
			else
			{
				if(max<prices[i]-s)
					max=prices[i]-s;
			}
		}
		return max;
	}

}
