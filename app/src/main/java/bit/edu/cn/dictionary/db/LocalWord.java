package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bit.edu.cn.dictionary.bean.RecentWord;


/**
 * @author Wang Kexin
 * @date 2019.03.31  22:04
 * @function  将单词写入数据库，从数据库中读取单词
 */

public class LocalWord {
    private Context Context=null;
    private  String TableName=null;
    private WordSQLHelper helper=null;
    private SQLiteDatabase litedb_R=null,litedb_W=null,db=null;
    public LocalWord(Context context){
        Context=context;
        helper=new WordSQLHelper(Context);  //实例化WordSQLHelper类
        litedb_R=helper.getReadableDatabase();  //得到数据，read
        litedb_W=helper.getWritableDatabase();  //写下数据，writ
    }

    //销毁对象时释放创建的R,W对象
    protected void finalize() throws Throwable{
        litedb_R.close();
        litedb_W.close();
        helper.close();
        super.finalize();
    }


    //每查询一个插入Recycleview
    public void InsertWordLocal(RecentWord recentWord)
    {

        ContentValues contentValues=new ContentValues();
        contentValues.put("date", String.valueOf(recentWord.getDate()));
        contentValues.put("word",recentWord.getWord());
        contentValues.put("interpret",recentWord.getInterpret());
        String selection=Recent.RencentWord.COLUMN_WORD+"=?";
        String[] selectionArgs={String.valueOf(recentWord.id)};
        int count=db.update(Recent.RencentWord.TABLE_NAME,contentValues,selection,selectionArgs);
        if(count!=0)
        {
            //TODO 刷新UI
        }
    }


    //查询数据放在RecentWord中
    public List<RecentWord> LoadWordsFromDatabase()
    {
        if(db==null)
        {
            return Collections.emptyList();
        }
        List<RecentWord> result=new LinkedList<>();
        Cursor cursor=null;
        try{
            cursor=db.query(Recent.RencentWord.TABLE_NAME,
                    new String[]{
                    Recent.RencentWord.COLUMN_WORD,
                    Recent.RencentWord.COLUMN_INTERPRET,
                    Recent.RencentWord._ID},
                    null,null,null,
                    Recent.RencentWord.COlUMN_DATE,"DESC");
            while(cursor.moveToNext())
            {
                long int_now=cursor.getLong(cursor.getColumnIndex(Recent.RencentWord._ID));
                long date_now=cursor.getLong(cursor.getColumnIndex(Recent.RencentWord.COlUMN_DATE));
                String word_now=cursor.getString(cursor.getColumnIndex(Recent.RencentWord.COLUMN_WORD));
                String interpret_now=cursor.getString(cursor.getColumnIndex(Recent.RencentWord.COLUMN_INTERPRET));

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
        String selection=Recent.RencentWord._ID+" =?";
        String[] selectiopnArgs={String.valueOf(word.id)};
        int deletedRow=db.delete(Recent.RencentWord.TABLE_NAME,selection,selectiopnArgs);
        if(deletedRow>0)
        {
            //TODO 刷新UI
        }
    }

}
