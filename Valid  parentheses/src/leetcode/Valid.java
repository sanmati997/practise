package leetcode;

import java.util.Stack;

public class Valid {

	public static void main(String[] args)
	{
		String s = "{[[}]][";
		
		Valid v =new Valid();
		boolean result=v.paranthesis(s);
		
		System.out.println(result);
	}

	private boolean paranthesis(String s) 
	{
		// TODO Auto-generated method stub
		Stack st = new Stack();
        for(int i=0 ;i<s.length() ;i++)
        {
        	
        	if(s.charAt(i)=='[' ||s.charAt(i)=='{' || s.charAt(i)=='(')
        	{	
        		st.push(s.charAt(i));
        		continue;
        	}
        	if(st.empty())
        	{
        		return false;
        	}
        	
        	char c=(char)st.pop();
        	
        if(c=='[' && s.charAt(i)==']')
        	continue;
        if(c =='(' &&s.charAt(i)==')')
            continue;
        if(c =='{' &&s.charAt(i)=='}')
            continue;
			
			return false;
        }
		
		
		return st.empty();
	}

}
