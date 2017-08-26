package cn.wssgyyg.servlet;

import cn.wssgyyg.server.Request;
import cn.wssgyyg.server.Response;

/**
 * Created by bruce on 3/8/2017.
 */
public class LoginServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {
//        response.println("<html>");
//        response.println("<head>");
//        response.println("<title>HTTP response demo</title>");
//        response.println("</head>");
//        response.println("<body>");
//
//        System.out.println(request.getParameter("name"));
//        response.println("welcom Mr " + request.getParameter("name"));
//        response.println("</body>");
//        response.println("</html>");
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        if (null == name || null == pwd) {
            response.println("name or pwd not provided");
        } else if (login(name, pwd)) {
            response.println("login success");
        } else {

            response.println("login failed");
        }
    }

    public boolean login(String name, String pwd) {
        return name.equals("yyg") && pwd.equals("123456");
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {

    }

}
