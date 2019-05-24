package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.NoticeContract.NoticeInfo;

public class NoticeDB {
    private android.content.Context Context=null;
    private NoticeSQLHelper helper=null;
    private SQLiteDatabase db=null;
    private final static String TAG="Noticeeee";
    private int noti_state=0;

    public NoticeDB(android.content.Context context){
        Context=context;
        helper=new NoticeSQLHelper(Context);  //实例化WordSQLHelper类
        db=helper.getWritableDatabase();  //写下数据，writ
    }

    protected void finalize() throws Throwable{
        helper.close();
        db.close();
        super.finalize();
    }

    public void SaveWord(String word,String inter)
    {
        if(db==null|| word==null||inter==null)
        {
            return ;
        }
        Log.v(TAG,word);
        Log.v(TAG,inter);
        ContentValues contentValue=new ContentValues();
        contentValue.put(NoticeInfo.COLUMN_INTERPRET,inter);
        contentValue.put(NoticeInfo.COLUMN_WORD,word);
        contentValue.put(NoticeInfo.COLUMN_USED,false);
        long rowId=db.insert(NoticeInfo.TABLE_NAME,null,contentValue);
        Log.v(TAG, String.valueOf(rowId));
    }

    public boolean exist()
    {
        Cursor cursor=db.query(NoticeInfo.TABLE_NAME,
                new String[]{
                        NoticeInfo.COLUMN_WORD,
                        NoticeInfo.COLUMN_INTERPRET,
                        NoticeInfo.COLUMN_USED,
                        NoticeInfo._ID},
                null,null,null,
                null, null);
        if(cursor.getCount()>0)
        {
            return true;
        }else
        {
            return false;
        }
    }

    public void Saved(String word)
    {
        String selection= NoticeInfo.COLUMN_WORD+" = ?";
        String[] selectionArgs={word};
        Cursor cursor=db.query(NoticeInfo.TABLE_NAME,
                new String[]{
                        NoticeInfo.COLUMN_WORD,
                        NoticeInfo._ID},
                selection,
                selectionArgs,
                null,
                null, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put(NoticeInfo.COLUMN_USED, 1);
            db.update(NoticeInfo.TABLE_NAME, contentValues, selection, selectionArgs);
    }

    public RecentWord loadNoticeWord()
    {
        if(db==null)
        {
            return null;
        }
        Log.v(TAG,"save db is not empty");
        Cursor cursor=null;
        try{
            cursor=db.query(NoticeInfo.TABLE_NAME,
                    new String[]{
                            NoticeInfo.COLUMN_WORD,
                            NoticeInfo.COLUMN_INTERPRET,
                            NoticeInfo.COLUMN_USED,
                            NoticeInfo._ID},
                    null,null,null,
                    null, null);
            Log.v(TAG, String.valueOf(cursor.getCount()));
            if(cursor.getCount()<=0)
                return null;
            Log.v(TAG,"getcount");
            while(cursor.moveToNext())
            {
                Log.v(TAG,"moveToNext");
                long int_now=cursor.getLong(cursor.getColumnIndex(NoticeInfo._ID));
                String word_now=cursor.getString(cursor.getColumnIndex(NoticeInfo.COLUMN_WORD));
                Log.v(TAG,word_now);
                String interpret_now=cursor.getString(cursor.getColumnIndex(NoticeInfo.COLUMN_INTERPRET));
                Log.v(TAG,interpret_now);
                Long used_now=cursor.getLong(cursor.getColumnIndex(NoticeInfo.COLUMN_USED));
                if(used_now==null)
                {
                    Saved(word_now);
                    RecentWord recentWord=new RecentWord(int_now);
                    recentWord.setWord(word_now);
                    recentWord.setInterpret(interpret_now);
                    Log.v(TAG,"1111"+recentWord.getWord());
                    Log.v(TAG,"1111"+recentWord.getInterpret());
                    return recentWord;
                }
                else if(used_now==noti_state)
                {
                    Saved(word_now);
                    RecentWord recentWord=new RecentWord(int_now);
                    recentWord.setWord(word_now);
                    recentWord.setInterpret(interpret_now);
                    return recentWord;
                }
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        if(noti_state==1)
            noti_state=0;
        else
            noti_state=1;
        loadNoticeWord();
        Log.v(TAG,"2222");
        return null;
    }



}
