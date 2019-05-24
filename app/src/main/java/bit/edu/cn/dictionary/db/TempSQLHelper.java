package bit.edu.cn.dictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static bit.edu.cn.dictionary.db.tempcontract.SQL_CREATE_ENTRIES;
import static bit.edu.cn.dictionary.db.tempcontract.SQL_DELETE_ENTRIES;

public class TempSQLHelper extends SQLiteOpenHelper {
    private static final String TAG="temp";
    private static int VERSION=1;    //数据库版本

    public TempSQLHelper(Context context) {

        super(context, "temp", null, VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        Log.v(TAG,"Creat");
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"update");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
