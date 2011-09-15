package no.andsim.recipes.activity;

import java.util.ArrayList;
import java.util.List;

import no.andsim.recipes.model.Vare;
import no.andsim.recipes.util.SAXMarshaller;
import no.andsim.recipes.ws.VareServiceClientRS;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class VareServiceClientActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.service);
		TextView lblResult = (TextView) findViewById(R.id.wsresult);
		super.onCreate(savedInstanceState);


		List<Vare> varer = new ArrayList<Vare>();
		String returnMessage = VareServiceClientRS.connect("http://vareservice.herokuapp.com/VareServiceRS/varer");
		SAXMarshaller m = new SAXMarshaller();
		try {
			varer = m.unmarshall(returnMessage);
			System.out.println("VARER ADDED: "+varer.size());
		} catch (Exception e) {
			
			Log.e("VareServiceClient",e.getMessage());
		}
				
				//vareService.sendVareToWS(vare).toString();
		lblResult.setText(varer.toString() +"\n"+" Antall varer = "+varer.size());
		

	}

}
