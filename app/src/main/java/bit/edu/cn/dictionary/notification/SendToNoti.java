package bit.edu.cn.dictionary.notification;

import android.util.Log;

import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.NoticeDB;

import static bit.edu.cn.dictionary.MainActivity.NotiWord;
import static bit.edu.cn.dictionary.MainActivity.noticeDB;

/*
@function 选择在notification中 推送的单词
 */
public class SendToNoti {
    private   String Noti_word;
    private String Noti_interpret;
    private static final String TAG="SendToNoti";

    public  String getNoti_word()
    {
        //先加载收藏单词
        RecentWord re=NotiWord.LoadNotiWordFromDB();
        if(re!=null)
        {
            Noti_interpret=re.getInterpret();
            return re.getWord();
        }
        else
        {
            RecentWord noti_word= noticeDB.loadNoticeWord();
            Noti_interpret=noti_word.getInterpret();
            return  noti_word.getWord();
        }
    }

    public  String getNoti_interpret()
    {
        return Noti_interpret;
    }
}
