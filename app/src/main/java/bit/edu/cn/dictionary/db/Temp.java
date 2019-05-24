package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bit.edu.cn.dictionary.bean.RecentWord;

import static bit.edu.cn.dictionary.db.tempcontract.tempInfo.COLUMN_INTERPRET;
import static bit.edu.cn.dictionary.db.tempcontract.tempInfo.COlUMN_DATE;
import static bit.edu.cn.dictionary.db.tempcontract.tempInfo.COlUMN_WORD;
import static bit.edu.cn.dictionary.db.tempcontract.tempInfo.TABLE_NAME;


public class Temp {
    private android.content.Context Context=null;
    private TempSQLHelper helper=null;
    private SQLiteDatabase db=null;
    private static final String TAG="tempppppp";

    public Temp(android.content.Context context){
        Context=context;
        helper=new TempSQLHelper(Context);  //实例化WordSQLHelper类
        db=helper.getWritableDatabase();  //写下数据，writ
    }

    protected void finalize() throws Throwable{
        helper.close();
        db.close();
        super.finalize();
    }

    public void add(String word,String interpret)
    {
        if(db==null)
        {
            return ;
        }
        ContentValues contentValue=new ContentValues();
        contentValue.put(COlUMN_WORD,word);
        contentValue.put(COLUMN_INTERPRET,interpret);
        contentValue.put(COlUMN_DATE,System.currentTimeMillis());
        long rowID=db.insert(TABLE_NAME,null,contentValue);
    }

    public RecentWord loadFromTemp()
    {
        if(db==null)
        {
            return null;
        }
        Cursor cursor=null;
        try{
            cursor=db.query(TABLE_NAME,
                    new String[]{
                            tempcontract.tempInfo.COlUMN_WORD,
                            tempcontract.tempInfo.COlUMN_DATE,
                            tempcontract.tempInfo.COLUMN_INTERPRET,
                            tempcontract.tempInfo._ID},
                    null,null,null,
                    null, COlUMN_DATE+" DESC");
            while(cursor.moveToNext())
            {
                String word_now=cursor.getString(cursor.getColumnIndex(tempcontract.tempInfo.COlUMN_WORD));
                String interpret_now=cursor.getString(cursor.getColumnIndex(tempcontract.tempInfo.COLUMN_INTERPRET));
                long int_now=cursor.getLong(cursor.getColumnIndex(tempcontract.tempInfo._ID));
                RecentWord re=new RecentWord(int_now);
                Log.v(TAG,word_now);
                Log.v(TAG,interpret_now);
                re.setWord(word_now);
                re.setInterpret(interpret_now);
                return re;
            }

        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return null;
    }

}
