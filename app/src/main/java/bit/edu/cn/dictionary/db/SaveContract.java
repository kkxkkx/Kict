package bit.edu.cn.dictionary.db;

import android.provider.BaseColumns;

public class SaveContract {
    private SaveContract() { }
    public static final String SAVE_CREATE_ENTRIES=
            "CREATE TABLE "+ SaveInfo.TABLE_NAME+"("+
                    SaveInfo._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SaveInfo.COLUMN_WORD+" TEXT UNIQUE,"+
                    SaveInfo.COLUMN_USED+" BOOLEAN,"+
                    SaveInfo.COLUMN_INTERPRET+" TEXT)";
    public static final String SAVE_DELETE_ENTRIES=
            "DROP TABLE IF EXITS "+ SaveInfo.TABLE_NAME;

    public static  class SaveInfo implements BaseColumns
    {
        public static String  TABLE_NAME="SavedWord";
        public static String  COLUMN_WORD="word";
        public static String  COLUMN_INTERPRET="interpret";
        public static boolean COLUMN_USED=false;
    }
}
