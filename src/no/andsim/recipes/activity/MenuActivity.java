package no.andsim.recipes.activity;

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
					startActivity(new Intent("no.andsim.recipes.SHOPPINGLIST"));					
				}
			});
		 
		 Button newRecipeBtn = (Button) findViewById(R.id.newItemsBtn);
		 newRecipeBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.andsim.recipes.EDIT_ITEM"));					
				}
			});
		 
		 Button wsBtn = (Button) findViewById(R.id.serviceBtn);
		 wsBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.andsim.recipes.VARESERVICE"));					
				}
			});
	 }
	 
}
