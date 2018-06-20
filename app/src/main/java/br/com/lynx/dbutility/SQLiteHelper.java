package br.com.lynx.dbutility;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.lynx.util.SQLScriptUTIL;

public abstract class SQLiteHelper {

	private static final String DATABASE_NAME = "lynxme.db";
	private static final int DATABASE_VERSION = 1;

	private static String[] scriptCreate = null;
	private static String[] scriptDelete = null;
	private static int instancias = 0;

	private static SQLiteDatabase db;

	public static void initDB(Context context) {
		SQLiteOpenHelper openHelper = new SQLiteOpenHelper(context, DATABASE_NAME,
		    DATABASE_VERSION, getScriptCreate(context), getScriptDelete(context));
		db = openHelper.getWritableDatabase();
		openHelper.onUpgrade(db, 1, 1);
	}

	public static SQLiteDatabase getDB(Context context) {
		if (db == null) {
			SQLiteOpenHelper openHelper = new SQLiteOpenHelper(context,
			    DATABASE_NAME, DATABASE_VERSION, getScriptCreate(context),
			    getScriptDelete(context));
			db = openHelper.getWritableDatabase();
		}

        instancias = instancias + 1;
		return db;
	}

	public static void closeDB(Context context) {
		if (getDB(context).isOpen() && instancias == 1) {
			getDB(context).close();
			db = null;
		}
	}

	// SELECT * FROM TABLE
	public static Cursor getCursor(Context context, String table) {

		return getDB(context).query(table, null, null, null, null, null, null);
	}

	// SELECT Columns FROM TABLE
	public static Cursor getCursor(Context context, String table, String[] columns) {

		return getDB(context).query(table, columns, null, null, null, null, null);
	}

	// SELECT * FROM TABLE WHERE Columns = ?
	public static Cursor getCursor(Context context, String table,
	    String selection, String[] selectionArgs) {

		return getDB(context).query(table, null, selection, selectionArgs, null,
		    null, null);
	}

	// SELECT Columns TABLE WHERE Columns = ?
	public static Cursor getCursor(Context context, String table,
	    String[] columns, String selection, String[] selectionArgs) {

		return getDB(context).query(table, columns, selection, selectionArgs, null,
		    null, null);
	}

	// SELECT DISTINCT Column FROM TABLE
	public static Cursor getCursor(Context context, boolean distinct,
	    String table, String[] columns) {

		return getDB(context).query(distinct, table, columns, null, null, null,
		    null, null, null);
	}

	// SELECT * FROM WHERE Column = X
	public static Cursor getCursor(Context context, String table, String selection) {

		return getDB(context).query(table, null, selection, null, null, null, null);
	}

	// INSERT INTO VALUES (?, ?, ..., ?)
	public static long insert(Context context, String table, ContentValues values) {
		long id = 0;

		id = getDB(context).insert(table, null, values);

		return id;
	}

	// DELETE TABLE WHERE Column = ?
	public static void delete(Context context, String table, String whereClause,
	    String[] whereArgs) {

		getDB(context).delete(table, whereClause, whereArgs);
	}

	// DELETE TABLE
	public static void delete(Context context, String table) {

		getDB(context).delete(table, null, null);
	}

	// UPDATE TABLE WHERE Column = ?
	public static void update(Context context, String table,
	    ContentValues values, String whereClause, String[] whereArgs) {

		getDB(context).update(table, values, whereClause, whereArgs);
	}

	// Executa um script sql
	public static Cursor execSQL(Context context, String sql) {

		return getDB(context).rawQuery(sql, null);
	}

	// Executa instruções SQL
	public static void execSQL(Context context, String[] sql) {

		for (int i = 0; i < sql.length; i++)
			getDB(context).execSQL(sql[i]);
	}

	public static void execSQL(Context context, List<String> sql) {
		SQLiteDatabase banco = getDB(context);

		for (int i = 0; i < sql.size(); i++)
			banco.execSQL(sql.get(i).toString());
	}

	// Recriar o banco de dados
	public static void onRecreate(Context context) {

		// Exclui o banco de dados
		execSQL(context, getScriptDelete(context));
		// Cria o banco de dados novamente
		execSQL(context, getScriptCreate(context));
	}

	private static String[] getScriptCreate(Context context) {

		if (scriptCreate == null) {
			SQLScriptUTIL script = new SQLScriptUTIL(context.getAssets());
			scriptCreate = script.lerScript("scriptCreate.sql");
		}
		return scriptCreate;
	}

	private static String[] getScriptDelete(Context context) {

		if (scriptDelete == null) {
			SQLScriptUTIL script = new SQLScriptUTIL(context.getAssets());
			scriptDelete = script.lerScript("scriptDelete.sql");
		}
		return scriptDelete;
	}

	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	public static int getDatabaseVersion() {
		return DATABASE_VERSION;
	}
}