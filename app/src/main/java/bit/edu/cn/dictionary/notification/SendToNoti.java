package bit.edu.cn.dictionary.notification;

import android.util.Log;

import bit.edu.cn.dictionary.bean.RecentWord;

import static bit.edu.cn.dictionary.MainActivity.NotiWord;

/*
@function 选择在notification中 推送的单词
 */
public class SendToNoti {
    private   String Noti_word;
    private String Noti_interpret;
    private static final String TAG="SendToNoti";

    public  String getNoti_word()
    {
        RecentWord re=NotiWord.LoadNotiWordFromDB();
        Log.v(TAG,re.getWord());
        Log.v(TAG,re.getInterpret());
        if(re!=null)
        {
            Noti_interpret=re.getInterpret();
            return re.getWord();
        }
        return null;
    }

    public  String getNoti_interpret()
    {
        return Noti_interpret;
    }
}
