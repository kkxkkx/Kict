package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.SaveContract.SaveInfo;


public class SaveWord {

    private android.content.Context Context=null;
    private SaveSQLHelper helper=null;
    private SQLiteDatabase db=null;
    private final static String TAG="SaveWord";

    public SaveWord(Context context){
        Context=context;
        helper=new SaveSQLHelper(Context);  //实例化WordSQLHelper类
        db=helper.getWritableDatabase();  //写下数据，writ
    }

    //销毁对象时释放创建的R,W对象
    protected void finalize() throws Throwable{
        helper.close();
        db.close();
        super.finalize();
    }

    //将一个单词存入数据库
    public void SaveWord(AWord w)
    {
        if(db==null|| TextUtils.isEmpty(w.getKey()))
        {
            return ;
        }
        Log.v(TAG,w.getKey());
        ContentValues contentValue=new ContentValues();
        contentValue.put(SaveInfo.COLUMN_INTERPRET,w.getInterpret());
        contentValue.put(SaveInfo.COLUMN_WORD,w.getKey());
        contentValue.put(SaveInfo.COLUMN_PRON,w.getPsA());
        long rowId=db.insert(SaveInfo.TABLE_NAME,null,contentValue);
    }

    //查询数据放在RecentWord中
    public List<RecentWord> LoadFromSavedDB()
    {

        if(db==null)
        {
            return Collections.emptyList();
        }
        Log.v(TAG,"save db is not empty");
        List<RecentWord> result=new LinkedList<>();
        Cursor cursor=null;
        try{
            cursor=db.query(SaveInfo.TABLE_NAME,
                    new String[]{
                            SaveInfo.COLUMN_WORD,
                            SaveInfo.COLUMN_PRON,
                            SaveInfo.COLUMN_INTERPRET,
                            SaveInfo._ID},
                    null,null,null,
                    null, null);
            while(cursor.moveToNext())
            {
                long int_now=cursor.getLong(cursor.getColumnIndex(SaveInfo._ID));
                String word_now=cursor.getString(cursor.getColumnIndex(SaveInfo.COLUMN_WORD));
                String pron_now=cursor.getString(cursor.getColumnIndex(SaveInfo.COLUMN_PRON));
                String interpret_now=cursor.getString(cursor.getColumnIndex(SaveInfo.COLUMN_INTERPRET));

                RecentWord recentWord=new RecentWord(int_now);
                recentWord.setWord(word_now);
                recentWord.setInterpret(interpret_now);
                recentWord.setpron(pron_now);
                result.add(recentWord);
            }

        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public boolean IsSaved(String word)
    {
        String selection=SaveInfo.COLUMN_WORD+" = ?";
        String[] selectionArgs={word};
        Cursor cursor=db.query(SaveInfo.TABLE_NAME,
                new String[]{
                        SaveInfo.COLUMN_WORD,
                        SaveInfo._ID},
                selection,
                selectionArgs,
                null,
                null, null);
        if(cursor.getCount()==0)
            return false;
        Log.v(TAG,"word saves");
        return true;
    }

    public void deleteWord(String word)
    {
        String selection=SaveInfo.COLUMN_WORD+" =?";
        String[] selectiopnArgs={word};
        db.delete(SaveInfo.TABLE_NAME,selection,selectiopnArgs);
    }
}
