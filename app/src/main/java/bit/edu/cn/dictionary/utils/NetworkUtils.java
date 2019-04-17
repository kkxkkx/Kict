package bit.edu.cn.dictionary.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.ParserConfigurationException;

import bit.edu.cn.dictionary.bean.AWord;

/**
 * @author Wang Kexin
 * @date 2019.03.01  21:04
 * @function  将单词信息从网络上下载
 */

public class NetworkUtils {

    //用于查词的URL
    public final static String Search_Word1 = "https://dict-" +
            "co.iciba.com/api/dictionary.php?w=";
    public final static String Search_Word2 = "&key=866085E8C4B3F7C1AB220880A51B031F";
    public final static String TAG="FetchXML";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static AWord getInputStreamByUrl(String urlStr, String searchword)
            throws IOException, ParserConfigurationException, SAXException {

        AWord Word_Now = null;
        //final赋值变量，只能赋值一次，不能被修改
        InputStream tempInput = null;
        URL url = null;
        HttpURLConnection connection = null;
        url = new URL(urlStr);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        //设置连接超时
        connection.setConnectTimeout(8000);
        //设置读取超时
        connection.setReadTimeout(10000);
        //发送请求与服务器建立连接
        connection.connect();
        if (connection.getResponseCode() == 200) {

            tempInput = connection.getInputStream();
        }

        if (tempInput != null) {
            parseXML parseXML = new parseXML();
            //将输入流放入reader中
            InputStreamReader reader = new InputStreamReader(tempInput, StandardCharsets.UTF_8);
            ContentHandler contentHandler = new ContentHandler();
            //解析
            parseXML.paraseXMLwithSAX(contentHandler, new InputSource(reader));
            Word_Now = contentHandler.getWord();
            Word_Now.setKey(searchword);
            Log.v(TAG,searchword);
            return Word_Now;
        }
        else return null;

    }
}





