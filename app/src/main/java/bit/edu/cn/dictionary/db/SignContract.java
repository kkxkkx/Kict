package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class SignContract {
    private SignContract(){}
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+ SignInfo.TABLE_NAME+"("+
                    SignInfo._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SignInfo.COlUMN_DATE+" TEXT UNIQUE,"+
                    SignInfo.COLUMN_DAYS+" INTEGER)";

    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ SignInfo.TABLE_NAME;

    public static  class SignInfo implements BaseColumns
    {
        public static String  TABLE_NAME="SignDays";
        public static String  COlUMN_DATE="date";
        public static String COLUMN_DAYS="int";
    }


}
