package scm_acat.serviceValidator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App 
{
	public static void main( String[] args ) throws Exception
	{
		final APIValidator apiValidator = new APIValidator();
		ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
		Runnable task = new Runnable() {
			public void run() {
				try {
					apiValidator.validate();
				} catch ( Exception e) {
					e.printStackTrace();
				}

			}
		};
		int delay=4;
		
		scheduler.scheduleWithFixedDelay(task, 0, delay, TimeUnit.HOURS);

	}

}


