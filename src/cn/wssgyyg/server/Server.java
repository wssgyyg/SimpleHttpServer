package cn.wssgyyg.server;

import cn.wssgyyg.server.util.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * request and respond
 * Created by bruce on 3/8/2017.
 */
public class Server {
    private boolean isShutDown = false;
    private ServerSocket server;
    public static final String CRLF = "\r\n";
    public static final String BLANK = " ";
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    //指定端口启动
    public void start(int port){
        start(port);
    }
    //启动方法
    public void start() {
        try {
            server = new ServerSocket(8888);
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    //accept a client
    private void receive() {
        try {
            while (!isShutDown) {
                new Thread(new Dispatcher(server.accept())).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //stop the server;
    public void stop() {
        isShutDown = true;
        CloseUtil.closeAll(server);
    }


}

