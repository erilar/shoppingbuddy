package no.andsim.sbuddy.activity.service;

import java.util.List;

import no.andsim.sbuddy.model.Product;
import no.andsim.sbuddy.ws.ProductWSClientRS;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ProductService extends Service {

	private static final String TAG = ProductService.class.getSimpleName();

	private ProductGetter vareGetter;
	
	private final ProductWSClientRS clientRS = ProductWSClientRS.getInstance();
	

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		vareGetter = new ProductGetter();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy()");
		if (vareGetter.isRunning()) {
			super.onDestroy();
			vareGetter.interrupt();
			vareGetter = null;
		}
	}

	@Override
	public synchronized void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "onStart()");
		if (!vareGetter.isRunning()) {
			vareGetter.start();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	class ProductGetter extends Thread {
		
		public ProductGetter(){
			super("ProductGetter");
		}
		
		private static final long DELAY = 2000;
		private boolean isRunning = false;
		@Override
		public void run() {
			isRunning = true;
			while (isRunning) {
				try {
					Log.d(TAG, "ProductGetter running");
					List<Product> varer =clientRS.getProductsFromService();
					Log.d(TAG, "Got: "+ varer.size());
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					isRunning = false;
				}
			}
		}
		public boolean isRunning(){
			return this.isRunning;
		}
	}
	
	

}
