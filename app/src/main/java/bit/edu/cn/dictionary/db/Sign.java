package bit.edu.cn.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static bit.edu.cn.dictionary.db.SignContract.SignInfo.COLUMN_DAYS;
import static bit.edu.cn.dictionary.db.SignContract.SignInfo.COlUMN_DATE;

public class Sign {
    private android.content.Context Context=null;
    private SignSQLHelper helper=null;
    private SQLiteDatabase db=null;
    private final static String TAG="sign";
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

    public void sign(String date,int days)
    {
        if(db==null)
        {
            return ;
        }
        ContentValues contentValue=new ContentValues();
        contentValue.put(COlUMN_DATE,date);
        contentValue.put(COLUMN_DAYS,days);
    }

    public int LoadSignDays(String date_now) {
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
                        null, HistoryContract.HistoryInfo.COlUMN_DATE + " DESC");

                if(cursor.getCount()>0) {
                    Long date = cursor.getLong(cursor.getColumnIndex(SignContract.SignInfo.COlUMN_DATE));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    long diff = 0;

                    diff = df.parse(date_now).getTime() - df.parse(String.valueOf(date)).getTime();
                    long day = diff / nd;
                    while (cursor.moveToNext()) {
                        if (day == 1) {
                            int num = cursor.getInt(cursor.getColumnIndex(SignContract.SignInfo.COLUMN_DAYS));
                            sign(date_now, 1 + num);
                            return 1 + num;
                        } else {
                            sign(date_now, 1);
                            return 1;
                        }
                    }
                }else
                {
                    sign(date_now, 1);
                    return 1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        return 1;
    }


}
