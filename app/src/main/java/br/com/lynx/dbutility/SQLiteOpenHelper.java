package br.com.lynx.dbutility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

	private String[] sqlCreate;
	private String[] sqlDrop;

	public SQLiteOpenHelper(Context context, String databaseName, int version,
	    String[] sqlCreate, String[] sqlDrop) {
		super(context, databaseName, null, version);

		this.sqlCreate = sqlCreate;
		this.sqlDrop = sqlDrop;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (int i = 0; i < sqlCreate.length; i++)
			db.execSQL(this.sqlCreate[i]);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = 0; i < sqlDrop.length; i++)
			db.execSQL(this.sqlDrop[i]);

		onCreate(db);
	}
}
