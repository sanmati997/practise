package com.scm.kafka.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SFDCPublisherMain {
	
	public static void main(String[] args) {
		
		final SFDCPublisher sfdcPublisher = new SFDCPublisher();
		ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
		Runnable task = new Runnable() {
			public void run() {
				try {
					sfdcPublisher.sfdcRequest();
				} catch ( Exception e) {
					e.printStackTrace();
				}

			}
		};
		int delay=60;
		System.out.println("Delay::"+delay);
		scheduler.scheduleWithFixedDelay(task, 0, delay, TimeUnit.SECONDS);

	}

}
