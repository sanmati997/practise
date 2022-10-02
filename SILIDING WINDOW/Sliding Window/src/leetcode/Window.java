package leetcode;

public class Window {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		int arr[] = {1, 4, 2, 10, 23, 3, 1, 0, 20};
		int k = 4 ; // window size
//				Output : 39
		
		int i=0,j=0,sum=0;
		int max=Integer.MIN_VALUE;
		
		while(j<arr.length)
		{
		  sum=sum+arr[j];
		  
		  if(j-i+1<k) //to check window always use(j-i+1)
		  {
			  j++;
		  }
		  else
		  {
			  if(j-i+1==k)
			  {
				  max=Math.max(sum, max);
				  sum= sum-arr[i];
				  i++;
				  j++;
			  }
		  }
		}
		System.out.println(max);
		
	}

}
