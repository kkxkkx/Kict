package bit.edu.cn.dictionary.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import bit.edu.cn.dictionary.bean.AWord;

import static bit.edu.cn.dictionary.utils.ResourceUtils.FilePath;

public class AudioPlayer {
    public final static int UK_ACCENT=0;
    public final static int US_ACCENT=1;
    public MediaPlayer mediaPlayer=null;
    public boolean IsPermitted=true;

    public Context mcontext=null;

    public AudioPlayer(Context context)
    {
        mcontext=context;
        IsPermitted=true;
    }

    public void playAndioByWord(String word,int accent){
        if(word==null||word.length()<=0)
            return;
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        String path_temp;
        if(accent==US_ACCENT)
            path_temp=FilePath+word+"_us.mp3";
        else
            path_temp=FilePath+word+"_uk.mp3";
        mediaPlayer= (MediaPlayer) MediaPlayer.create(mcontext, Uri.parse("file://"+path_temp));
        mediaPlayer.start();
    }
}
