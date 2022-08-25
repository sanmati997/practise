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

public class MainExecutor{

	static String daytime;
	static String weektime;
	public static void main(String[] args) throws SQLException {
		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		
		/*-------------------------FOR DAILY----------------------------------*/
		TimerTask task =new TimerTask(){
			public void run() {
				System.out.println("Inside DAILY Scheduler....");
				try {
					Date date = new Date();
					long time = date.getTime();
					Timestamp ts = new Timestamp(time);
					System.out.println("Current Time Stamp: " + ts);
					CSFMDaily.executeApiBatch();
				} catch (SQLException var5) {
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
		int startTimeHourForDay = Integer.parseInt(prop.getProperty("startTimeHourForDay"));
		int startTimeMinuteForDay = Integer.parseInt(prop.getProperty("startTimeMinuteForDay"));
		
		System.out.println("delayTimeForDay "+delayTimeForDay+" startTimeHourForDay "+startTimeHourForDay+" startTimeMinuteForDay "+startTimeMinuteForDay);
		
		
		c.set(Calendar.HOUR_OF_DAY, startTimeHourForDay);
		c.set(Calendar.MINUTE, startTimeMinuteForDay);
		c.set(Calendar.SECOND, 00);
		
		Date firstTime = c.getTime();
		System.out.println(firstTime);
		if(firstTime.before(new Date())){
		c.add(Calendar.DATE, 1);
		firstTime = c.getTime();
		}
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate( task, firstTime, (60000*delayTimeForDay));
		
		/*-----------------------FOR WEEKLY-------------------------*/
		TimerTask task1 =new TimerTask()
				{
					public void run() {
						System.out.println("Inside WEEKLY Scheduler....");
		
						try {
							Date date = new Date();
							long time = date.getTime();
							Timestamp ts = new Timestamp(time);
							System.out.println("Current Time Stamp: " + ts);
							CSFMWeekly.executeApiBatch();
						} catch (SQLException var5) {
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
			int startTimeHourForWeek = Integer.parseInt(prop.getProperty("startTimeHourForWeek"));
			int startTimeMinuteForWeek = Integer.parseInt(prop.getProperty("startTimeMinuteForWeek"));
			System.out.println("delayTimeForWeek "+delayTimeForWeek+" startTimeHourForWeek "+startTimeHourForWeek+" startTimeMinuteForWeek "+startTimeMinuteForWeek);
			c1.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			c1.set(Calendar.HOUR_OF_DAY,startTimeHourForWeek);
			c1.set(Calendar.MINUTE, startTimeMinuteForWeek);
			c1.set(Calendar.SECOND, 00);
			
			Date firstTime1 = c1.getTime();
			System.out.println(firstTime1);
			if(firstTime1.before(new Date())){
				c1.add(Calendar.DATE, 7);
				firstTime1 = c1.getTime();
			}
			
			Timer timer2 = new Timer();
			timer2.scheduleAtFixedRate( task1, firstTime1, (60000*delayTimeForWeek));
			
		
	}
}

