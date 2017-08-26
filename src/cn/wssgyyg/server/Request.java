package cn.wssgyyg.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * encapsulate request
 * Created by bruce on 3/8/2017.
 */
public class Request {
    //request method
    private String method;

    //request resource
    private String url;

    //request arguments
    private Map<String, List<String>> parametersMapValues;

    //内部
    private static final String CRLF = "\r\n";
    private InputStream is;
    private String requestInfo;

    public Request() {
        method = "";
        url = "";
        parametersMapValues = new HashMap<>();

    }

    public Request(InputStream is) {
        this();
        this.is = is;
        try {
            byte[] data = new byte[20480];
            int len = 0;
            len = is.read(data);
            requestInfo = new String(data, 0, len);

        } catch (IOException e) {
            return ;
        }

        parseRequestInfo();
    }

    public String getUrl() {
        return this.url;
    }

    private void parseRequestInfo() {
        if (null == requestInfo || (requestInfo = requestInfo.trim()).equals("")) {
            return;
        }
        /**
         * ================================
         * 从信息的首行分解出：请求方式   请求路径    请求参数（get可能存在）
         *  如Get /index.html?name=123&pwd=545 HTTP/1.1
         *
         *  如果为post方式，请求参数可能在最后正文中
         *
         * ================================
         */
        String paramString = "";

        //1.获取请求方式
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
        int idx = requestInfo.indexOf("/");
        this.method = firstLine.substring(0, idx).trim();
        String urlString = firstLine.substring(idx, firstLine.indexOf("HTTP/")).trim();
        if (this.method.equalsIgnoreCase("post")) {
            this.url = urlString;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        } else {
            if (urlString.contains("?")) {
                String[] urlArray = urlString.split("\\?");
                this.url = urlArray[0];
                paramString = urlArray[1];
            }
        }
        parseParams(paramString);
    }

    private void parseParams(String paramString){
        if (paramString.equals("")) return;
        String[] params = paramString.split("&");
        for (String param : params) {
            int idxEqual = param.indexOf("=");
            String key = param.substring(0, idxEqual).trim();
            String value = decode(param.substring(idxEqual + 1).trim(), "utf-8");
            List<String> values = parametersMapValues.get(key);
            if(null == values){
                values = new ArrayList<>();
            }
            values.add(value);
            parametersMapValues.put(key, values);
        }
    }

    /**
     * solve Chinese problem
     * @param value target
     * @param code  charset
     * @return  decoded string
     */
    private String decode(String value, String code) {
        try {
            return URLDecoder.decode(value, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getParameterValues(String name) {
        List<String> values = null;
        if ((values = parametersMapValues.get(name)) == null) {
            return null;
        } else {
            return values.toArray(new String[values.size()]);
        }
    }

    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (null == values) {
            return null;
        }
        return values[0];
    }

    public static void main(String[] args) {
        new Request().parseParams("name=&pass=123");
    }

}
