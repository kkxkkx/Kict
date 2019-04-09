package bit.edu.cn.dictionary.utils;

import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Wang Kexin
 * @date 2019.03.25  16：30
 * @function  将发音的文件流下载下来
 */

public class AudioUtils {
    public static  final String TAG="mp3";
    public static void getAudio(String mp3_URL,String name) throws IOException {
        Log.v(TAG,mp3_URL);
        if(mp3_URL==null)
            return;
        InputStream inputStream=null;
        HttpURLConnection connection = null;
        URL url = new URL(mp3_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        //设置连接超时
        connection.setConnectTimeout(8000);
        //设置读取超时
        connection.setReadTimeout(10000);
        //发送请求与服务器建立连接
        connection.connect();
        if (connection.getResponseCode() == 200) {

            inputStream = connection.getInputStream();
        }

        Log.v(TAG,"false");
        ResourceUtils resourceUtils=new ResourceUtils();
        if(resourceUtils.SaveInputStream(inputStream,name))
            Log.v(TAG, "true");
        Log.v(TAG, String.valueOf(Environment.getExternalStorageDirectory()));
    }
}
