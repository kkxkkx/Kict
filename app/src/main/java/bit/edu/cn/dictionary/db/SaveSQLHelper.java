package bit.edu.cn.dictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static bit.edu.cn.dictionary.db.SaveContract.SAVE_CREATE_ENTRIES;
import static bit.edu.cn.dictionary.db.SaveContract.SAVE_DELETE_ENTRIES;


public class SaveSQLHelper extends SQLiteOpenHelper {
    private static final String TAG="SAVE DB";
    private static int VERSION=1;    //数据库版本

    public SaveSQLHelper(Context context) {

        super(context, "Save", null, VERSION);
        Log.v(TAG,"SAVESQLHElPER");
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.v(TAG,"creat");
        db.execSQL(SAVE_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"update");
        db.execSQL(SAVE_DELETE_ENTRIES);
        onCreate(db);
    }
}
