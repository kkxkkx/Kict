package bit.edu.cn.dictionary.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ThemeParser {
    public static List<String> pull(InputStream is) throws XmlPullParserException, IOException {
        List<String> list=new ArrayList<String>();
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
                        String theme=parser.nextText();
                        msg=theme;
                    }
                    break;
                    case XmlPullParser.END_TAG:
                        if ("message".equals(parser.getName())) {
                            Log.v("add",msg);
                            list.add(msg);
                        }
                        break;
            }
            type=parser.next();
        }
        return list;
    }
}
