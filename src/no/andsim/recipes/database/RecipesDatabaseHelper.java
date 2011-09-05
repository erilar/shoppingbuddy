package no.andsim.recipes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecipesDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "applicationdata";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table recipe (_id integer primary key autoincrement, "
			+ "name text not null, description text not null, barcode integer not null, checked boolean not null);";

	public RecipesDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(RecipesDatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS recipe");
		onCreate(database);

	}

}
