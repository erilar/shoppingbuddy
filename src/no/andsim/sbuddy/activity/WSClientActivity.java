package no.andsim.sbuddy.activity;

import no.andsim.sbuddy.activity.R;
import no.andsim.sbuddy.ws.ProductWSClientRS;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WSClientActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.service);
		TextView lblResult = (TextView) findViewById(R.id.wsresult);
		super.onCreate(savedInstanceState);

		String returnMessage = ProductWSClientRS.connect("http://vareservice.herokuapp.com/ProductServiceRS/products");
	
		lblResult.setText(returnMessage);
		

	}

}
