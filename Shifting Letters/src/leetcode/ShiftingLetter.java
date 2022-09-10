package leetcode;

public class ShiftingLetter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//      String s ="abc";
//    	int [] shifts = {3,5,9};
    	String s="mkgfzkkuxownxvfvxasy";
    	int [] shifts	={505870226,437526072,266740649,224336793,532917782,311122363,567754492,595798950,81520022,684110326,137742843,275267355,856903962,148291585,919054234,467541837,622939912,116899933,983296461,536563513};
    	String result=letters(s,shifts);
    	System.out.println(result);
	}
	 public static String letters(String s, int[] shifts) 
	    {
	            StringBuffer sb=new StringBuffer(s);
	        
	        long x=0;
	        for(int i=0;i<shifts.length;i++)
	        {
	            x+=shifts[i];
	        }
	        int m=0, n=0;
	        
	            for(int j=0 ;j<s.length();j++)
	            {
	                 m=(int)(x%26);
	                 n= sb.charAt(j)+m;
	                if(n>122)
	                {
	                    m=m%26;
	                    n=n-123;
	                    n=n+97;
	                }
	                char c=(char)n;
	                sb.setCharAt(j,c);
	               x-=shifts[j]; 
	            }
	        
	        return sb.toString();
	    }
}
