package Leetcode;

import java.util.Arrays;

public class Solution {

	
	public static void main(String [] args)
	{
		int[] numbers = {1,3,5,6};
				int target = 5;

		Solution s =new Solution();
		int result;
		result=s.searchInsert(numbers, target);
		
		System.out.println(result);
	}
	
	  public int searchInsert(int[] nums, int target)
	    {
	        int i=0;
	        int j=nums.length-1;
	        
	        
	        while(i<=j)
	        {
	            int pivot=(i+j)/2;
	            if(nums[pivot]==target)
	                return pivot;
	            if(nums[pivot]>target)
	                j=pivot-1;
	            else
	                i=pivot+1;
	        }
	        
	        for(int p=0 ;p<nums.length; p++)
	        {
	            if(nums[p]>target)
	            {
	                return p;
	                // break;
	            }
	                
	        }
	        // if(result==-1)
	            return nums.length;
	        // else
	        //     return result;
	        
	    }

}
