package cn.wssgyyg.servlet;

import cn.wssgyyg.server.Request;
import cn.wssgyyg.server.Response;

/**
 * Created by bruce on 3/8/2017.
 */
public class RegisterServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {

    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.println("<html>");
        response.println("<head>");
        response.println("<title>Register</title>");
        response.println("</head>");
        response.println("<body>");

        System.out.println(request.getParameter("name"));
        response.println("你的用户名为 " + request.getParameter("name"));
        response.println("</body>");
        response.println("</html>");
    }
}
