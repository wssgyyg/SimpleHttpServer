package cn.wssgyyg.server;

import cn.wssgyyg.servlet.Servlet;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by bruce on 3/8/2017.
 */
public class WebApp {

    private static ServletContext context;

    static{
        //获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //从工厂生成解析器
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        //指定XML处理器
        WebXmlHandler handler = new WebXmlHandler();
        try {
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB_INFO/web.xml"), handler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context = new ServletContext();
        Map<String, String> mappings = context.getMappings();
        List<Mapping> mappingList = handler.getMappingList();
        List<Entity> entityList = handler.getEntityList();
        for (Mapping mapping : mappingList) {
            String name = mapping.getName();
            for (String url : mapping.getUrlPattern()) {
                mappings.put(url, name);
            }
        }

        Map<String, String> servlet = context.getServlet();
        for (Entity entity : entityList) {
            servlet.put(entity.getName(), entity.getClz());
        }
    }

    public static Servlet getServlet(String url){
        if (null == url || url.equals("")) {
            return null;
        }
        String name = context.getServlet().get(context.getMappings().get(url));

        try {
            return (Servlet) Class.forName(name).newInstance();//确保空构造存在
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }

}
