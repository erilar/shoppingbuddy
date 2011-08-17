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
		
		 Button overviewBtn = (Button) findViewById(R.id.showRecipesBtn);
		 overviewBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.andsim.recipes.OVERVIEW"));					
				}
			});
		 
		 Button newRecipeBtn = (Button) findViewById(R.id.newRecipesBtn);
		 newRecipeBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent("no.andsim.recipes.EDIT"));					
				}
			});
	 }
	 
}
