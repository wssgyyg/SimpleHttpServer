package cn.wssgyyg.server;


import cn.wssgyyg.server.util.CloseUtil;
import cn.wssgyyg.servlet.Servlet;

import java.io.IOException;
import java.net.Socket;

/**
 * 一个请求与响应成为Dispatcher
 * Created by bruce on 3/8/2017.
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            this.request = new Request(client.getInputStream());
            this.response = new Response(client.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            code = 500;
            return;
        }

    }

    @Override
    public void run() {
        try {
            Servlet servlet = WebApp.getServlet(request.getUrl());
            System.out.println(request.getUrl());
            if (null == servlet) {
                this.code = 404;
            } else {

                servlet.service(request, response);
            }
            response.pushToClient(code);
        } catch (Exception e) {
            this.code = 500;
        }

        CloseUtil.closeAll(client);
    }
}
