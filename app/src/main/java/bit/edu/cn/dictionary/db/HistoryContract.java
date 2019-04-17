package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class HistoryContract {
    private HistoryContract() { }
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+ HistoryInfo.TABLE_NAME+"("+
                    HistoryInfo._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    HistoryInfo.COlUMN_DATE+" TEXT,"+
                    HistoryInfo.COLUMN_WORD+" TEXT UNIQUE,"+
                    HistoryInfo.COLUMN_USED+" BOOLEAN,"+
                    HistoryInfo.COLUMN_INTERPRET+" TEXT)";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ HistoryInfo.TABLE_NAME;

    public static  class HistoryInfo implements BaseColumns
    {
        public static String  TABLE_NAME="RecentWord";
        public static String  COlUMN_DATE="date";
        public static String  COLUMN_WORD="word";
        public static String  COLUMN_INTERPRET="interpret";
        public static boolean COLUMN_USED=false;
    }
}
