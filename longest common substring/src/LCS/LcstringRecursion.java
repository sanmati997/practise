package LCS;

public class LcstringRecursion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String X = "abcdxyz";
        String Y = "xyzabcd";
        int m = X.length();
        int n = Y.length();
        int result=lcs(X,Y,m,n,0);
        System.out.println("hello   "+result);
	}

	private static int lcs(String x, String y, int m, int n,int count) {
		// TODO Auto-generated method stub
		if(m==0||n==0) 
		{
			return count;		
		}
		
		if(x.charAt(m-1)==y.charAt(n-1)) {
			return count=lcs(x,y,m-1,n-1,count+1);
		}
		return	count= Math.max(count, Math.max(lcs(x,y,m-1,n,0), lcs(x,y,m,n-1,0)));
	}

}
