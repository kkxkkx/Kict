package bit.edu.cn.dictionary.search;

import android.util.Log;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.utils.NetworkUtils;

import static bit.edu.cn.dictionary.SearchActivity.Word_Now;
import static bit.edu.cn.dictionary.SearchActivity.searchword;

public class SearchFromInternet {
    private final static String TAG="SearchFromInternet";
    public static AWord aWord;

    //对输入的单词进行搜索
    public static AWord getWordFromInternet() {

        final String Word_temp = searchword;
        if (Word_temp == null || Word_temp.equals(""))
            return null;
        char[] array = Word_temp.toCharArray();
        //TODO 这里是中文需要进行修改
        if (array[0] > 256)
            return null;

        final String URL_temp = NetworkUtils.Search_Word1 + Word_temp + NetworkUtils.Search_Word2;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Log.v(TAG, "start thread");
                    Word_Now = NetworkUtils.getInputStreamByUrl(URL_temp, searchword);
                    aWord=Word_Now;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        return aWord;
    }
}
