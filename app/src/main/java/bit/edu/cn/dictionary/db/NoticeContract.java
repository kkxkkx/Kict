package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class NoticeContract {
    private NoticeContract() { }
    public static final String NOTICE_CREATE_ENTRIES=
            "CREATE TABLE "+ NoticeInfo.TABLE_NAME+"("+
                    NoticeInfo._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    NoticeInfo.COLUMN_WORD+" TEXT UNIQUE,"+
                    NoticeInfo.COLUMN_USED+" BOOLEAN,"+
                    NoticeInfo.COLUMN_INTERPRET+" TEXT)";
    public static final String NOTICE_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ NoticeInfo.TABLE_NAME;

    public static  class NoticeInfo implements BaseColumns
    {
        public static String  TABLE_NAME="NoticeWord";
        public static String  COLUMN_WORD="word";
        public static String  COLUMN_INTERPRET="interpret";
        public static String COLUMN_USED="IsUsed";
    }
}
