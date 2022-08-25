package com.scm.kafka.bash;

import com.scm.kafka.main.AcatManualOrder;

public class App 
{
	public static void main( String[] args ) throws Exception
	{
		System.out.println( "Calling Consumer Main" );        
		AcatManualOrder.main(args);
	}
}
