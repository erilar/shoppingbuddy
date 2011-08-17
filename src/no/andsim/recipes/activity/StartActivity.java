package no.andsim.recipes.activity;

import no.andsim.recipes.util.SplashThread;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class StartActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		Intent nextActivity = new Intent("no.andsim.recipes.MENU");
		SplashThread logoSplash = new SplashThread(this, nextActivity, 5000);
		logoSplash.start();
	}

}