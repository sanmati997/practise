package binary;

public class BinaryIteration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] a= {1,2,3,4,5,6,7,8};
		int target=55;
		
		
		int result=binary(a,target);
		System.out.println(result);

	}

	private static int binary(int[] a, int target) {
		// TODO Auto-generated method stub
		int start=0;
		int end=a.length-1;
	
		  while(start<=end) 
		  {
			  int pivot=(start+end)/2;
			  if(a[pivot]==target)
				  return pivot;
			  if(target<a[pivot])
				  end=pivot-1;
			  else
				  start=pivot+1;
		  }
			
		return -1;
	}

}
