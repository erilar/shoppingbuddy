package no.andsim.recipes.activity;

import no.andsim.recipes.ws.VareServiceClientRS;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VareServiceClientActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.service);
		TextView lblResult = (TextView) findViewById(R.id.wsresult);
		super.onCreate(savedInstanceState);

		String returnMessage = VareServiceClientRS.connect("http://vareservice.herokuapp.com/VareServiceRS/varer");
	
		lblResult.setText(returnMessage);
		

	}

}
