package no.andsim.recipes.activity;

import no.andsim.recipes.model.Vare;
import no.andsim.recipes.ws.VareServiceClient;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VareServiceClientActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.service);
		TextView lblResult = (TextView) findViewById(R.id.wsresult);
		VareServiceClient vareService = new VareServiceClient();
		super.onCreate(savedInstanceState);

		Vare vare = new Vare("123123123", "Melk");
		
		String returnMessage = vareService.sendVareToWS(vare).toString();
		lblResult.setText(returnMessage);
		

	}

}
