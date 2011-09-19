package no.andsim.sbuddy.util;

import android.app.Activity;
import android.content.Intent;

public class SplashThread extends Thread {
	
	private Activity creator;
	private Intent nextActivity;
	private int duration;
	
	public SplashThread(Activity creator, Intent nextActivity, int duration){
		this.creator = creator;
		this.nextActivity = nextActivity;
		this.duration = duration;
	}
	
	@Override
	public void run(){
		super.run();
		try {
			int logoTimer = 0;
			while (logoTimer < duration) {
				sleep(100);
				logoTimer = logoTimer + 100;
			}
			 creator.startActivity(nextActivity);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			creator.finish();
		}
	}

}
