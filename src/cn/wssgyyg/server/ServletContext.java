package cn.wssgyyg.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文
 * Created by bruce on 3/8/2017.
 */
public class ServletContext {
    //为每一个Servlet取一个别名
    //login --> LoginServlet
    //url --> servlet;
    private Map<String, String> servlet;

    //servlet --> class
    private Map<String, String> mappings;

    public ServletContext() {
        servlet = new HashMap<>();
        mappings = new HashMap<>();
    }

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }
}
