package bit.edu.cn.dictionary.utils;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bit.edu.cn.dictionary.bean.AWord;

/**
 * @author Wang Kexin
 * @date 2019.03.20  10:30
 * @function  将XMl中的信息存入AWord中
 */

public class ContentHandler extends DefaultHandler {
    public AWord word=null;
    private String interpret=null;
    private String TAG="SAX";
    private boolean isChinese=false;

    private String nodename=null;

    public ContentHandler(){
        word = new AWord();
        isChinese=false;
    }

    public AWord getWord(){
        return word;
    }

    //开始解析XML文件
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

    }

    //开始解析节点
    @Override
    public void startElement(String uri, String localnName,
                             String qName, Attributes attributes) {
        nodename=localnName;  //把当前的标签记录下来
    }

    //开始提取节点中的内容
    @Override
    public void characters(char[] ch,int start,int length) throws SAXException{
        super.characters(ch, start, length);
        if(length<=0)
            return;
        for(int i=start;i<start+length;i++){
            if(ch[i]=='\n')
                return;
        }

        String value=new String(ch,start,length);
        //对具体的每一行进行解析
        if("key".equals(nodename)){

            word.setKey(value);
        }
        //ps为发音，有两种情况
        else if("ps".equals(nodename)){
            if(word.getPsE().length()<=0){
                Log.v(TAG,value);
                word.setPsE("/"+value+"/");
            }else{

                word.setPsA("/"+value+"/");
            }
        }

        else if("pron".equals(nodename)){
            if (word.getPronE().length()<=0){
                Log.v(TAG,value);
                word.setPronE(value);
            }else{
                Log.v(TAG,value);
                word.setPronA(value);
            }

        }
        //pos是词性
        else if("pos".equals(nodename)){
            isChinese=false;
            interpret=value+" ";

        }
        //acception是翻译
        else if("acceptation".equals(nodename)){
            int i=value.indexOf("；");
            if(i>0)
                interpret+=value+'\n';
            else
                interpret+=value;
            interpret=word.getInterpret()+interpret;
            word.setInterpret(interpret);
            interpret="";
        }
        //orig是例句
        else if("orig".equals(nodename)){
            word.setOrigs(value+"\n");
        }
        //trans是例句的翻译
        else if("trans".equals(nodename)){
            word.setTrans(value+"\n");
        }
        //TODO 中文翻译成英文这一块暂缺
    }

    //在完成整个XML解析时调用
    @Override
    public void endDocument() throws SAXException{
        super.endDocument();
        //TODO 目前还不能翻译中文
        if(isChinese) return;
        String interpret=word.getInterpret();
        //去掉解释的最后一个换行符
        if(interpret.length()>0&&interpret!=null){
            char[] valueArray=interpret.toCharArray();
            word.setInterpret(new String(valueArray,0,
                    interpret.length()-1));
        }
    }
}