package bit.edu.cn.dictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static bit.edu.cn.dictionary.db.SignContract.SQL_CREATE_ENTRIES;
import static bit.edu.cn.dictionary.db.SignContract.SQL_DELETE_ENTRIES;


public class SignSQLHelper extends SQLiteOpenHelper {
    private static final String TAG="SIGN_DB";
    private static int VERSION=1;    //数据库版本

    public SignSQLHelper(Context context) {

        super(context, "Sign", null, VERSION);
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


