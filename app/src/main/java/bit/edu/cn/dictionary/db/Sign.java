package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import bit.edu.cn.dictionary.notification.SetTime;

import static bit.edu.cn.dictionary.db.SignContract.SignInfo.COLUMN_DAYS;
import static bit.edu.cn.dictionary.db.SignContract.SignInfo.COlUMN_DATE;

public class Sign {
    private android.content.Context Context=null;
    private SignSQLHelper helper=null;
    private SQLiteDatabase db=null;
    private final static String TAG="signnnnn";
    public long nd=1000 * 24 * 60 * 60;

    public Sign(Context context){
        Context=context;
        helper=new SignSQLHelper(Context);  //实例化WordSQLHelper类
        db=helper.getWritableDatabase();  //写下数据，writ
    }

    protected void finalize() throws Throwable{
        helper.close();
        db.close();
        super.finalize();
    }

    public void sign(int days)
    {
        if(db==null)
        {
            return ;
        }
        ContentValues contentValue=new ContentValues();
        contentValue.put(COlUMN_DATE,System.currentTimeMillis());
        contentValue.put(COLUMN_DAYS,days);
        long rowID=db.insert(SignContract.SignInfo.TABLE_NAME,null,contentValue);

        Log.v(TAG, String.valueOf(days));
    }

    public int LoadSignDays(Date date_now) {
        if (db == null) {
            return 1;
        }
        Cursor cursor = null;
            try {
                cursor = db.query(SignContract.SignInfo.TABLE_NAME,
                        new String[]{
                                SignContract.SignInfo.COlUMN_DATE,
                                SignContract.SignInfo.COLUMN_DAYS,
                                SignContract.SignInfo._ID},
                        null, null, null,
                        null, SignContract.SignInfo.COlUMN_DATE + " DESC");

                Log.v(TAG,"cursor"+cursor.getCount());

                if(cursor.getCount()>0) {
                    cursor.moveToNext();
                    Long date = cursor.getLong(cursor.getColumnIndex(SignContract.SignInfo.COlUMN_DATE));
                    Log.v(TAG, String.valueOf(date));
                    if(date==0)
                    {
                        sign(1);
                        return 1;
                    }

                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(new Date(date));
                    SetTime.setZeroTime(calendar);

                    Calendar calendar_now=Calendar.getInstance();
                    calendar_now.setTime(date_now);
                    SetTime.setZeroTime(calendar_now);

                    Log.v(TAG, String.valueOf(System.currentTimeMillis()));
                    Log.v(TAG,date.toString());
                    long diff = 0;


                    diff=calendar_now.getTime().getTime()-calendar.getTime().getTime();

                    Log.v(TAG, "dfif"+String.valueOf(diff));

                    if(diff<nd)
                    {
                        return 0;
                    }
                    long day = diff / nd;



                    while (cursor.moveToNext()) {
                        if (day == 1) {
                            int num = cursor.getInt(cursor.getColumnIndex(SignContract.SignInfo.COLUMN_DAYS));
                            sign( 1 + num);
                            return 1 + num;
                        } else {
                            sign( 1);
                            return 1;
                        }
                    }
                }else
                {
                    sign( 1);
                    return 1;
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        return 1;
    }


}
