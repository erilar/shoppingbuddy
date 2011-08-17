package no.andsim.recipes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RecipeDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_BARCODE = "barcode";
	public static final String DATABASE_TABLE = "recipe";

	private Context context;
	private SQLiteDatabase database;
	private RecipesDatabaseHelper dbHelper;

	public RecipeDbAdapter(Context context) {
		this.context = context;
	}

	public RecipeDbAdapter open() throws SQLException {
		dbHelper = new RecipesDatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createRecipe(String name, String description, int barcode) {
		ContentValues initialValues = createContentValues(name, description, barcode);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean updateTodo(long rowId, String name, String description, int barcode) {
		ContentValues updateValues = createContentValues(name, description, barcode);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean deleteRecipe(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public Cursor fetchAllRecipes() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_DESCRIPTION, KEY_BARCODE }, null, null, null, null, null);
	}

	public Cursor fetchRecipe(long rowId) throws SQLException {
		Cursor mCursor = database.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_DESCRIPTION, KEY_BARCODE }, KEY_ROWID + "=" + rowId,
				null, null, null, null);
			if(mCursor != null){
				mCursor.moveToFirst();
			}
			return mCursor;
	}

	private ContentValues createContentValues(String name, String description, int barcode) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_BARCODE, barcode);
		return values;
	}

}
