package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.bean.RecentWord;

import static bit.edu.cn.dictionary.search.RecentFragment.ReAdapter;


/**
 * @author Wang Kexin
 * @date 2019.03.31  22:04
 * @function  将单词写入数据库，从数据库中读取单词
 */

public class LocalWord {
    private Context Context=null;
    private WordSQLHelper helper=null;
    private SQLiteDatabase db=null;
    private final static String TAG="LocalWord";

    public LocalWord(Context context){
        Context=context;
        helper=new WordSQLHelper(Context);  //实例化WordSQLHelper类
        db=helper.getWritableDatabase();  //写下数据，writ
    }

    //销毁对象时释放创建的R,W对象
    protected void finalize() throws Throwable{
        helper.close();
        db.close();
        super.finalize();
    }



    //查询数据放在RecentWord中
    public List<RecentWord> LoadWordsFromDatabase()
    {

        if(db==null)
        {
            Log.v(TAG,"db is empty");
            return Collections.emptyList();
        }
        Log.v(TAG,"db is not empty");
        List<RecentWord> result=new LinkedList<>();
        Cursor cursor=null;
        try{
            cursor=db.query(RecentContract.RecentInfo.TABLE_NAME,
                    new String[]{
                    RecentContract.RecentInfo.COLUMN_WORD,
                    RecentContract.RecentInfo.COLUMN_INTERPRET,
                            RecentContract.RecentInfo.COlUMN_DATE,
                            RecentContract.RecentInfo._ID},
                    null,null,null,
                    null, RecentContract.RecentInfo.COlUMN_DATE+" DESC");
            while(cursor.moveToNext())
            {
                long int_now=cursor.getLong(cursor.getColumnIndex(RecentContract.RecentInfo._ID));
                long date_now=cursor.getLong(cursor.getColumnIndex(RecentContract.RecentInfo.COlUMN_DATE));
                String word_now=cursor.getString(cursor.getColumnIndex(RecentContract.RecentInfo.COLUMN_WORD));
                String interpret_now=cursor.getString(cursor.getColumnIndex(RecentContract.RecentInfo.COLUMN_INTERPRET));

                RecentWord recentWord=new RecentWord(int_now);
                recentWord.setWord(word_now);
                recentWord.setDate(new Date(date_now));
                recentWord.setInterpret(interpret_now);
                result.add(recentWord);
            }

        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }


    public void deleteWord(RecentWord word)
    {
        String selection=RecentContract.RecentInfo._ID+" =?";
        String[] selectiopnArgs={String.valueOf(word.id)};
        int deletedRow=db.delete(RecentContract.RecentInfo.TABLE_NAME,selection,selectiopnArgs);
        if(deletedRow>0)
            ReAdapter.refresh(LoadWordsFromDatabase());
    }

    public boolean IsExistDB(AWord w)
    {
        String selection=RecentContract.RecentInfo.COLUMN_WORD+" = ?";
        String[] selectionArgs={w.getKey()};
        Cursor cursor=null;
        try {
                cursor = db.query(RecentContract.RecentInfo.TABLE_NAME,
                    new String[]{
                            RecentContract.RecentInfo.COLUMN_WORD,
                            RecentContract.RecentInfo.COLUMN_INTERPRET,
                            RecentContract.RecentInfo.COlUMN_DATE,
                            RecentContract.RecentInfo._ID},
                    selection,
                    selectionArgs,
                    null,
                    null, null);
            if (cursor.getCount() == 0)
                return false;
            ContentValues contentValues = new ContentValues();
            contentValues.put(RecentContract.RecentInfo.COLUMN_WORD, w.getKey());
            contentValues.put(RecentContract.RecentInfo.COLUMN_INTERPRET, w.getInterpret());
            contentValues.put(RecentContract.RecentInfo.COlUMN_DATE, System.currentTimeMillis());
            db.update(RecentContract.RecentInfo.TABLE_NAME, contentValues, selection, selectionArgs);
        }finally {
            if(cursor!=null)
                cursor.close();
        }
        Log.v(TAG,"word exits");
        return true;
    }

    //将一个单词存入数据库
    public void saveInfoDatabase(AWord w)
    {
        if(db==null|| TextUtils.isEmpty(w.getKey()))
        {
            return ;
        }
        ContentValues contentValue=new ContentValues();
        contentValue.put(RecentContract.RecentInfo.COlUMN_DATE,System.currentTimeMillis());
        contentValue.put(RecentContract.RecentInfo.COLUMN_INTERPRET,w.getInterpret());
        contentValue.put(RecentContract.RecentInfo.COLUMN_WORD,w.getKey());
        long rowId=db.insert(RecentContract.RecentInfo.TABLE_NAME,null,contentValue);
    }

}
