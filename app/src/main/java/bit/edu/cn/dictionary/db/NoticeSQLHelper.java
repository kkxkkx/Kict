package bit.edu.cn.dictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static bit.edu.cn.dictionary.db.NoticeContract.NOTICE_CREATE_ENTRIES;
import static bit.edu.cn.dictionary.db.NoticeContract.NOTICE_DELETE_ENTRIES;

public class NoticeSQLHelper extends SQLiteOpenHelper {
    private static final String TAG="WORDDB";
    private static int VERSION=1;    //数据库版本

    public NoticeSQLHelper(Context context) {

        super(context, "Notcie", null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.v(TAG,"Creat");
        db.execSQL(NOTICE_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"update");
        db.execSQL(NOTICE_DELETE_ENTRIES);
        onCreate(db);
    }
}
