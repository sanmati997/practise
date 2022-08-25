package com.scm.email.main;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import com.scm.email.serviceprovider.commonUtility;

public class MainExecutor {

	static String delaytime;
	
	public static void main(String[] args) {
		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		System.out.println("project started succesfully....");
		System.out.println(new Date());

		/*-------------------------FOR DAILY----------------------------------*/

		TimerTask task =new TimerTask()
		{
			public void run() 
			{
				System.out.println("Inside DAILY Scheduler....");

				try 
				{
					Date date = new Date();
					long time = date.getTime();
					Timestamp ts = new Timestamp(time);
					System.out.println("Current Time Stamp: " + ts);
					CarsDaily.executeApiBatch();
				} 
				catch (SQLException var5) {
					var5.printStackTrace();
				} catch (InterruptedException var6) {
					var6.printStackTrace();
				} catch (MessagingException var7) {
					var7.printStackTrace();
				}

			}
		};
 
		Calendar c = Calendar.getInstance();
		int delayTimeForDay = Integer.parseInt(prop.getProperty("delayTimeDay"));
		int startTimeHoursForDay = Integer.parseInt(prop.getProperty("startTimeHoursForDay"));
		int startTimeMinuteForDay = Integer.parseInt(prop.getProperty("startTimeMinuteForDay"));
		c.set(Calendar.HOUR_OF_DAY, startTimeHoursForDay);
		c.set(Calendar.MINUTE, startTimeMinuteForDay);
		c.set(Calendar.SECOND, 00);
		
		  Date firstTime = c.getTime();
//		  System.out.println(firstTime);
		  
		 if(firstTime.before(new Date()))
		   {
	            c.add(Calendar.DATE, 1);
	            firstTime = c.getTime();
	       }
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate( task, firstTime, (60000*delayTimeForDay));
		
		/*-----------------------FOR WEEKLY-------------------------*/
		
		
		TimerTask task1 =new TimerTask()
				{
					public void run() 
					{
						System.out.println("Inside WEEKLY Scheduler....");
		
						try 
						{
							Date date = new Date();
							long time = date.getTime();
							Timestamp ts = new Timestamp(time);
							System.out.println("Current Time Stamp: " + ts);
							CarsWeekly.executeApiBatch();
						} 
						catch (SQLException var5) {
							var5.printStackTrace();
						} catch (InterruptedException var6) {
							var6.printStackTrace();
						} catch (MessagingException var7) {
							var7.printStackTrace();
						}
					}
			   };
		
		

					Calendar c1 = Calendar.getInstance();
					int delayTimeForWeek = Integer.parseInt(prop.getProperty("delayTimeWeek"));
					int startTimeHoursForWeek = Integer.parseInt(prop.getProperty("startTimeHoursForWeek"));
					int startTimeMinuteForWeek = Integer.parseInt(prop.getProperty("startTimeMinuteForWeek"));
					c1.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
					c1.set(Calendar.HOUR_OF_DAY,startTimeHoursForWeek);
					c1.set(Calendar.MINUTE, startTimeMinuteForWeek);
					c1.set(Calendar.SECOND, 00);
			           
					  Date firstTime1 = c1.getTime();
//					  System.out.println(firstTime);
					  
					 if(firstTime1.before(new Date()))
					   {
				            c1.add(Calendar.DATE, 7);
				            firstTime1 = c1.getTime();
				       }
//					 System.out.println(firstTime1);
					
					Timer timer2 = new Timer();
					timer2.scheduleAtFixedRate( task1,firstTime1 , (60000*delayTimeForWeek));  
			   
			   
	}

}
