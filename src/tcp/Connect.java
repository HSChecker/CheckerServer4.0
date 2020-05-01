package tcp;

import tools.PARAMETER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Connect类
 *
 * @author HanShuo
 * @Date 2020/4/24 19:23
 */
public class Connect {

    public Connect() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PARAMETER.prot);
            System.out.println("服务启动成功，开始监听于："+PARAMETER.prot);
        } catch (IOException e) {
            System.out.println("服务启动失败："+PARAMETER.prot+"端口已被占用");
        }
        Socket socket;
        while(true){
            socket = serverSocket.accept();
            new NewConnect(socket);
        }

    }

}
