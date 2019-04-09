package bit.edu.cn.dictionary.utils;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Wang Kexin
 * @date 2019.03.17  21:04
 * @function  解析Xml文件
 */

public class parseXML {

    public SAXParserFactory factory=null;
    public XMLReader  reader=null;

    public parseXML() throws ParserConfigurationException, SAXException {
        //得到SAX解析工厂
        factory=SAXParserFactory.newInstance();
        reader=factory.newSAXParser().getXMLReader();
    }

    public void paraseXMLwithSAX(ContentHandler handler,InputSource inputSource)
            throws  SAXException, IOException {
        //将contentHandler的实例设置到XMLReader中
        reader.setContentHandler(handler);
        //开始解析
        reader.parse(inputSource);

    }
}
