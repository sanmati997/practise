package binary;

public class BinaryRecursion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] a= {1,2,3,4,5,6,7,8};
		int target=4;
		
		
		int result=binary(a,0,a.length-1,target);
		System.out.println(result);

	}

	private static int binary(int[] a, int i, int length, int target) {
		// TODO Auto-generated method stub
		int start =i;
		int end =length;
		if(start<= end) {
		int pivot=(start+end)/2;
		
		if(a[pivot]==target) {
			return pivot;
		}
		if(target<a[pivot]) {
			return binary(a,start,pivot-1,target);}
		else if(target>a[pivot]) {
			return binary(a,pivot+1,end,target);}
		}
		return -1;
	}
	

}
