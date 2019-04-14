package bit.edu.cn.dictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static bit.edu.cn.dictionary.db.RecentContract.SQL_CREATE_ENTRIES;
import static bit.edu.cn.dictionary.db.RecentContract.SQL_DELETE_ENTRIES;


/**
 * @author Wang Kexin
 * @date 2019.03.31  22:07
 * @function  创建一个数据库
 */

public class WordSQLHelper extends SQLiteOpenHelper {
    private static final String TAG="WORDDB";
    private static int VERSION=1;    //数据库版本

    public WordSQLHelper(Context context) {

        super(context, "Recent", null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.v(TAG,"Creat");
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"update");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
