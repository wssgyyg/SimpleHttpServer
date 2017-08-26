package cn.wssgyyg.server;
import cn.wssgyyg.server.util.CloseUtil;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * encapsulate response info
 * Created by bruce on 3/8/2017.
 */
public class Response {
    public static final String CRLF = "\r\n";
    public static final String BLANK = " ";

    private BufferedWriter bw;
    //Body
    StringBuilder content = new StringBuilder();

    //store head info
    private StringBuilder headInfo;

    //store bodylength
    private int length;

    public Response() {
        headInfo = new StringBuilder();
        content = new StringBuilder();
        length = 0;
    }

    public Response(OutputStream os) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os, "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "GBK"));
        } catch (IOException e) {
            e.printStackTrace();
            headInfo = null;
            close();
        }
    }

    public Response print(String info) {
        content.append(info);
        try {
            length += info.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return this;
    }

    public Response println(String info) {
        content.append(info).append(CRLF);
        try {
            length += (info + CRLF).getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return this;
    }
    private void createHeadInfo(int code) {
        //1)protocal version, status code, description
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code){
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
                break;
        }

        headInfo.append(CRLF);

        //2)response head
        headInfo.append("Server:yyg Server/0.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
        //3)response body content-length Byte length
        headInfo.append("Content-Length:").append(length).append(CRLF);
        //4)before body
        headInfo.append(CRLF);
    }

    public void pushToClient(int code) throws IOException {
        if (null == headInfo) {
            code = 500;
        }
        createHeadInfo(code);
        //head info + split
        bw.append(headInfo.toString());


        //body
        bw.append(content.toString());
        bw.flush();

    }

    public void close() {
        CloseUtil.closeAll(bw);

    }

}
