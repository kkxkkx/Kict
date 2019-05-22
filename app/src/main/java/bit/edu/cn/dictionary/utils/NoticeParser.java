package bit.edu.cn.dictionary.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.MainActivity;

public class NoticeParser {
    public static String word=null;
    public static String interpret=null;
    public static void pull(InputStream is) throws XmlPullParserException, IOException {
        String msg=null;

        XmlPullParser parser= Xml.newPullParser();
        parser.setInput(is,"utf-8");
        int type=parser.getEventType();
        while(type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    Log.v("parse",parser.getName());
                    if("messsage".equals(parser.getName())){
                    }else if("string".equals(parser.getName())){
                        word=parser.nextText();
                    }else if("interpre".equals(parser.getName())){
                        if(interpret!=null)
                        interpret+=parser.nextText()+"\n";
                        else
                            interpret=parser.nextText()+"\n";
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("message".equals(parser.getName())) {
                        MainActivity.noticeDB.SaveWord(word,interpret);
                        interpret=null;
                    }
                    break;
            }
            type=parser.next();
        }
    }
}
