package bit.edu.cn.dictionary.bean;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Timer;

import static bit.edu.cn.dictionary.SearchActivity.StoragePath;


/**
 * @author Wang Kexin
 * @date 2019.03.31  21:04
 * @function  播放发音
 */

public class AudioPlay {
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private Context mcontext=null;
    private String TableName=null;
    boolean Ispermitted=true;
    public AudioPlay(Context context,String Name)
    {
        this.mcontext=context;
        TableName=Name;
        //LocalWord localWord=new LocalWord(mcontext);
        Ispermitted=true;
    }
    //TODO 播放

    public void Play(String name)
    {
        String path="/sdcard"+StoragePath+name+".mp3";
        if(path.isEmpty())
        {
            return;
        }
        mediaPlayer=new MediaPlayer();
        timer=new Timer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            mediaPlayer.start();
            //TODO 有错误不能播放
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(timer!=null)
                {
                    timer.cancel();
                    timer=null;
                }
                if(mediaPlayer!=null)
               {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
            }
       });
    }
}
