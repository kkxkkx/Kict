package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class Recent {
    private Recent() { }
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+ RencentWord.TABLE_NAME+"("+
                    RencentWord._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    RencentWord.COlUMN_DATE+" TEXT,"+
                    RencentWord.COLUMN_WORD+" TEXT,"+
                    RencentWord.COLUMN_INTERPRET+" TEXT)";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ RencentWord.TABLE_NAME;

    public static  class RencentWord implements BaseColumns
    {
        public static String  TABLE_NAME="RecentWord";
        public static String  COlUMN_DATE="date";
        public static String  COLUMN_WORD="word";
        public static String  COLUMN_INTERPRET="interpret";
    }
}
