package no.andsim.sbuddy.activity;

import no.andsim.sbuddy.database.RecipeDbAdapter;
import no.andsim.sbuddy.model.Product;
import no.andsim.sbuddy.ws.ProductWSClientRS;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ItemDetails extends Activity {
	private EditText mNameText;
	private EditText mBodyText;
	private EditText mScanText;
	private CheckBox mBoughtCheck;
	private Long mRowId;
	private RecipeDbAdapter mDbHelper;
	private String barcode;
	private boolean checked;
	private final ProductWSClientRS clientRS = ProductWSClientRS.getInstance();
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new RecipeDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.item_edit);
		mNameText = (EditText) findViewById(R.id.item_edit_name);
		mBodyText = (EditText) findViewById(R.id.item_edit_description);
		mScanText = (EditText) findViewById(R.id.item_edit_barcode);
		mBoughtCheck = (CheckBox) findViewById(R.id.bought_check);
		
		Button confirmButton = (Button) findViewById(R.id.item_edit_button);
		Button scanButton = (Button) findViewById(R.id.item_scan_button);
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle.getSerializable(RecipeDbAdapter.KEY_ROWID);
		if (extras != null) {
			mRowId = extras.getLong(RecipeDbAdapter.KEY_ROWID);
		}
		populateFields();
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (mNameText.getText().toString().length() < 1) {
					Toast.makeText(getApplicationContext(), "You need to fill in a name", Toast.LENGTH_SHORT).show();
				} else {
				setResult(RESULT_OK);
				sendBarcodeToServer(mNameText.getText().toString());
				finish();
				}
			}

		});
		
		

		scanButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
				startActivityForResult(intent, 0);
			}

		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         
		         Toast.makeText(getApplicationContext(), "Content: "+ contents + " format: "+ format, Toast.LENGTH_SHORT).show();
		         // Handle successful scan
		         mScanText.setText(contents);
		         barcode = contents;
		         
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
		}

	
	private void populateFields() {
		if (mRowId != null) {
			Cursor todo = mDbHelper.fetchRecipe(mRowId);
			startManagingCursor(todo);

			mNameText.setText(todo.getString(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_NAME)));
			mBodyText.setText(todo.getString(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_DESCRIPTION)));
			mScanText.setText(todo.getString(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_BARCODE)));
			barcode = todo.getString(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_BARCODE));
			checked = 1 == todo.getInt(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_CHECKED));
			mBoughtCheck.setChecked(checked);
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(RecipeDbAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		String name = mNameText.getText().toString();
		String description = mBodyText.getText().toString();
		 checked = "1".equals(mBoughtCheck.getText().toString());

		if (mRowId == null) {
			long id = mDbHelper.createRecipe(name, description, barcode);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateRecipe(mRowId, name, description, barcode, checked);
		}

	}

	private void sendBarcodeToServer(String name) {
		Product vare = new Product(barcode,name);
		if(barcode != null && barcode.length()>1 && clientRS.putProductOnServer(vare)){
			Toast.makeText(getApplicationContext(), "New barcode registered on server: "+barcode +" with name "+name, Toast.LENGTH_SHORT).show();
		}
	}
}
