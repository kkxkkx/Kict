package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class RecentContract {
    private RecentContract() { }
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+ RecentInfo.TABLE_NAME+"("+
                    RecentInfo._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    RecentInfo.COlUMN_DATE+" TEXT,"+
                    RecentInfo.COLUMN_WORD+" TEXT UNIQUE,"+
                    RecentInfo.COLUMN_USED+" BOOLEAN,"+
                    RecentInfo.COLUMN_INTERPRET+" TEXT)";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ RecentInfo.TABLE_NAME;

    public static  class RecentInfo implements BaseColumns
    {
        public static String  TABLE_NAME="RecentWord";
        public static String  COlUMN_DATE="date";
        public static String  COLUMN_WORD="word";
        public static String  COLUMN_INTERPRET="interpret";
        public static boolean COLUMN_USED=false;
    }
}
