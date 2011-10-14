package no.itera.sbuddy.activity;

import no.itera.sbuddy.activity.R;
import no.itera.sbuddy.activity.service.ProductService;
import no.itera.sbuddy.database.ProductDbAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ProductListActivity extends ListActivity{
	private ProductDbAdapter dbHelper;
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private Cursor cursor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_list);
		this.getListView().setDividerHeight(2);
		dbHelper = new ProductDbAdapter(this);
		dbHelper.open();
		fillData();
		registerForContextMenu(getListView());
		Button scanButton = (Button) findViewById(R.id.check_scan_button);
		scanButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
				startActivityForResult(intent, 0);
			}

		});
	}

	// Create the menu based on the XML defintion
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.listmenu, menu);
		return true;
	}

	// Reaction to the menu selection
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.insert:
			createProduct();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.insert:
			createProduct();
			return true;
		case R.id.startService:
			startService(new Intent(this, ProductService.class ));
			break;
		case R.id.stopService:
			stopService(new Intent(this, ProductService.class));
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			dbHelper.deleteProduct(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void createProduct() {
		Intent i = new Intent(this, ProductDetailsActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	// ListView and view (row) on which was clicked, position and
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ProductDetailsActivity.class);
		i.putExtra(ProductDbAdapter.KEY_ROWID, id);
		// Activity returns an result if called with startActivityForResult
		
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	// Called with the result of the other activity
	// requestCode was the origin request code send to the activity
	// resultCode is the return code, 0 is everything is ok
	// intend can be use to get some data from the caller
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
		  if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
//		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         
		       Cursor cRecepies = dbHelper.fetchAllProducts();
		       cRecepies.moveToFirst();
		        while (cRecepies.isAfterLast() == false) {
		            if(contents.equals(cRecepies.getString(3))){
		            	dbHelper.updateProduct(cRecepies.getInt(0),cRecepies.getString(1), cRecepies.getString(2),cRecepies.getString(3), true);
		            	Toast.makeText(getApplicationContext(), "Scanned: "+ contents + " found: "+ cRecepies.getString(3) +" - "+ cRecepies.getString(1) , Toast.LENGTH_SHORT).show();
		            }
		            cRecepies.moveToNext();
		        }
		        fillData();
		         // Handle successful scan
		        // mScanText.setText(contents);
		         //barcode = contents;
		         
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }

	}

	private void fillData() {
		cursor = dbHelper.fetchAllProducts();
		startManagingCursor(cursor);

		String[] from = new String[] { ProductDbAdapter.KEY_NAME, ProductDbAdapter.KEY_CHECKED };
		int[] to = new int[] { R.id.label, R.id.row_bought_checked };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.item_row, cursor, from, to);
		setListAdapter(notes);
	}
	
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
