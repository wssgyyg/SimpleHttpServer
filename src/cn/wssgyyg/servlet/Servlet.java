package cn.wssgyyg.servlet;

import cn.wssgyyg.server.Request;
import cn.wssgyyg.server.Response;

/**
 * Created by bruce on 3/8/2017.
 */
public abstract class Servlet {
    public void service(Request request, Response response) throws Exception {
            this.doGet(request, response);
            this.doPost(request, response);
    }

    protected abstract void doGet(Request request, Response response) throws Exception;

    protected abstract void doPost(Request request, Response response) throws Exception;

}
