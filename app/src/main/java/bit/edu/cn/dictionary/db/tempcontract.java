package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class tempcontract {
    private tempcontract(){}
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+ tempInfo.TABLE_NAME+"("+
                    tempInfo._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    tempInfo.COlUMN_DATE+" TEXT,"+
                    tempInfo.COlUMN_WORD+" TEXT UNIQUE)";

    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ tempInfo.TABLE_NAME;

    public static  class tempInfo implements BaseColumns
    {
        public static String  TABLE_NAME="dailyword";
        public static String  COlUMN_WORD="word";
        public static String  COlUMN_DATE="date";
    }
}
