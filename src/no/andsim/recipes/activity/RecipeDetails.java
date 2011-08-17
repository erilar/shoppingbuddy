package no.andsim.recipes.activity;

import no.andsim.recipes.database.RecipeDbAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecipeDetails extends Activity {
	private EditText mNameText;
	private EditText mBodyText;
	private Long mRowId;
	private RecipeDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new RecipeDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.recipes_edit);
		mNameText = (EditText) findViewById(R.id.recipe_edit_name);
		mBodyText = (EditText) findViewById(R.id.recipe_edit_description);

		Button confirmButton = (Button) findViewById(R.id.recipe_edit_button);
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
				finish();
				}
			}

		});
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor todo = mDbHelper.fetchRecipe(mRowId);
			startManagingCursor(todo);

			mNameText.setText(todo.getString(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_NAME)));
			mBodyText.setText(todo.getString(todo.getColumnIndexOrThrow(RecipeDbAdapter.KEY_DESCRIPTION)));
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

		if (mRowId == null) {
			long id = mDbHelper.createRecipe(name, description, 0);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateRecipe(mRowId, name, description, 0);
		}

	}
}
