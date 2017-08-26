package cn.wssgyyg.server.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by bruce on 3/8/2017.
 */
public class CloseUtil {

    public static <T extends Closeable> void closeAll(T... io) {
        for (Closeable temp : io) {
            try {
                temp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
