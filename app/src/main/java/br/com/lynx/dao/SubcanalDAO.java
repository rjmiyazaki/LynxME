package br.com.lynx.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.vo.SubcanalVO;

public class SubcanalDAO {
	
	private Context context;
		
	public SubcanalDAO(Context context){
	  this.context  = context;	
	}

	public SubcanalVO retornaSubcanal(SQLiteDatabase db, int subcanalID){
		SubcanalVO subcanal = new SubcanalVO(context);
		Cursor c = db.query("Subcanal", null, "SubcanalID = ?", new String[]{String.valueOf(subcanalID)}, null, null, null);
		try{
			if(c.moveToFirst()){
				subcanal.setSubcanalID(subcanalID);
				subcanal.setDescricao(c.getString(1));
			}			
		}finally{
			c.close();
		}
		return subcanal;
	}

	public void load(SubcanalVO subcanal){
		Cursor c;
		
		c = SQLiteHelper.getCursor(this.context, "Subcanal", "SubcanalID = ?", new String[]{String.valueOf(subcanal.getSubcanalID())});
		try
		{
		  if (c.moveToFirst()){
		  	subcanal.setDescricao(c.getString(1));		  	
		  }
		}
		finally{
			c.close();
			SQLiteHelper.closeDB(this.context);
		}
	}
	
	public void carregaItensParaPesquisa(List<SubcanalVO> itens){
		Cursor c = SQLiteHelper.getCursor(context, "SubcanaisPesquisa");
		try{
			if (c.moveToFirst())
				do{
					SubcanalVO  item = new SubcanalVO(context);
					item.setSubcanalID(c.getInt(0));
					item.setDescricao(c.getString(1));

					itens.add(item);
				}while (c.moveToNext());

		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}
	}
}
