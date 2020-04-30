package tcp;

import tools.PARAMETER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Connect��
 *
 * @author HanShuo
 * @Date 2020/4/24 19:23
 */
public class Connect {

    public Connect() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PARAMETER.prot);
            System.out.println("���������ɹ�����ʼ�����ڣ�"+PARAMETER.prot);
        } catch (IOException e) {
            System.out.println("��������ʧ�ܣ�"+PARAMETER.prot+"�˿��ѱ�ռ��");
        }
        Socket socket;
        while(true){
            socket = serverSocket.accept();
            System.out.println("���������ӣ�"+socket.getInetAddress());
            new NewConnect(socket);
        }

    }

}
