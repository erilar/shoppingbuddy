package no.itera.sbuddy.activity;

import no.itera.sbuddy.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		
		 Button overviewBtn = (Button) findViewById(R.id.showItemsBtn);
		 overviewBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.itera.sbuddy.PRODUCTLIST"));					
				}
			});
		 
		 Button newRecipeBtn = (Button) findViewById(R.id.newItemsBtn);
		 newRecipeBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.itera.sbuddy.EDIT_ITEM"));					
				}
			});
		 
		 Button wsBtn = (Button) findViewById(R.id.serviceBtn);
		 wsBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.itera.sbuddy.PRODUCTSERVICE"));					
				}
			});
	 }
	 
	 
}
