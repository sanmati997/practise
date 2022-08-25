package Leetcode;

import java.util.Arrays;

public class Solution 
{
	public static void main(String [] args)
	{
		int[] numbers = {-4,-1,0,3,10};
		Solution s =new Solution();
		int [] result=s.sortedSquares(numbers);
		System.out.println(Arrays.toString(result));
	}
    public int[] sortedSquares(int[] nums) 
    {
        int [] result=new int[nums.length];
        for(int i=0;i<nums.length;i++)
        {
            result[i]=nums[i]*nums[i];
        }
        Arrays.sort(result);
      return result;
    }
}
