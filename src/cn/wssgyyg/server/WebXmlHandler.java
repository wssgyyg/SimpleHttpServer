package cn.wssgyyg.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 4/8/2017.
 */
public class WebXmlHandler extends DefaultHandler {
    private List<Entity> entityList;
    private List<Mapping> mappingList;
    private Entity entity;
    private Mapping mapping;
    private String beginTag;

    private boolean isMap;

    @Override
    public void startDocument() throws SAXException {
        entityList = new ArrayList<>();
        mappingList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (null != qName) {
            beginTag = qName;
            if (qName.equals("servlet")){
                isMap = false;
                entity = new Entity();
            } else if (qName.equals("servlet-mapping")) {
                isMap = true;
                mapping = new Mapping();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (null != beginTag) {
            String str = new String(ch, start, length);
            if (isMap) {
                if (beginTag.equals("servlet-name")) {
                    mapping.setName(str);
                } else if (beginTag.equals("url-pattern")) {
                    mapping.getUrlPattern().add(str);
                }
            } else {
                if (beginTag.equals("servlet-name")) {
                    entity.setName(str);
                } else if (beginTag.equals("servlet-class")) {
                    entity.setClz(str);
                }
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (null != qName) {
            if (qName.equals("servlet")){
                entityList.add(entity);
            } else if (qName.equals("servlet-mapping")) {
                mappingList.add(mapping);
            }
        }
        beginTag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

//    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//        //获取解析工厂
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        //从工厂生成解析器
//        SAXParser parser = factory.newSAXParser();
//        //指定XML处理器
//        WebXmlHandler handler = new WebXmlHandler();
//        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/wssgyyg/server/demo04/web.xml"), handler);
//
//        List<Mapping> mappings = handler.getMappingList();
//        for (Mapping mapping : mappings) {
//            System.out.print(mapping.getName() + "-->");
//            for (String url : mapping.getUrlPattern()) {
//                System.out.println(url);
//            }
//        }
//
//        for (Entity entity : handler.getEntityList()) {
//            System.out.println(entity.getName() + "-->" + entity.getClz());
//        }
//    }
}
