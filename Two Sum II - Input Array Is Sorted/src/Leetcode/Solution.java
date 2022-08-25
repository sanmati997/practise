package Leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
	
	public static void main(String [] args)
	{
		int[] numbers = {2,7,11,15};
				int target = 9;

		Solution s =new Solution();
		int [] result;
		result=s.twoSum(numbers, target);
		
		System.out.println(Arrays.toString(result));
	}
	
	 
    public int[] twoSum(int[] numbers, int target) 
    {
        Map m= new HashMap<>();
   
        for(int i = 0 ;i<numbers.length ;i++ )
        {   
            if(m.containsKey(numbers[i])) 
                return new int[]{(int)m.get(numbers[i]),i+1};
            else
                m.put(target-numbers[i],i+1);
             
            }
         return  new int[]{};
            
        }
    }
