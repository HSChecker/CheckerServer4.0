package tcp;

import manager.AllManager;
import player.Player;
import player.PlayerInform;
import room.Room;
import tools.LOCK;
import tools.NET;
import tools.PARAMETER;

import java.io.*;
import java.net.Socket;

/**
 * NewConnect��
 *
 * @author HanShuo
 * @Date 2020/4/24 20:40
 */
public class NewConnect implements Runnable, Player {

    private Socket socket;
    private PrintWriter bw;
    private BufferedReader br;
    private Room inRoom;
    private PlayerInform playerInform;

    public NewConnect(Socket socket) {
        AllManager.getNewConnectList().add(this);
        this.socket = socket;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("�ѽ������̴߳���"+socket.getInetAddress()+"������");
        try {
            bw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("�Ѵ�������"+socket.getInetAddress()+"��������������ڷ���ȷ�ϰ�");
            bw.println("sure "+ PARAMETER.version);
            bw.flush();
            System.out.println("����"+socket.getInetAddress()+"����ȷ�ϰ����ȴ��ذ�");
            String[] sure = NET.turnPacketData(br.readLine());
            if(sure[0].equals("sure")) {
                System.out.println("�յ�" + socket.getInetAddress() + "�Ļذ���ȷ�ϳɹ����ͻ��˰汾��" + sure[1]);
            }
            else {
                System.out.println(socket.getInetAddress() + "�ذ���֤ʧ�ܣ������رս��̣�");
                return;
            }

            String[] loginResult = NET.turnPacketData(br.readLine());
            if(loginResult[0].equals("login")) {
                boolean cf = false;
                for(PlayerInform pi:AllManager.getPlayerInformList()){
                    if((pi.getID() == Integer.parseInt(loginResult[1]) || pi.getName().equals(loginResult[1])) && pi.getPassWord().equals(loginResult[2])){
                        playerInform = pi;
                        bw.println("loginResult "+"true");
                        bw.flush();
                        cf = true;
                        break;
                    }
                }
                if(!cf){
                    bw.println("loginResult "+"false "+"�˻������ڻ��������");
                    bw.flush();
                    return;
                }
            }else if(loginResult[0].equals("register")){
                boolean cf = false;
                for(PlayerInform pi:AllManager.getPlayerInformList()){
                    if(pi.getID() == Integer.parseInt(loginResult[1])){
                        bw.println("loginResult "+"false "+"ע���˻��Ѵ��ڣ����¼");
                        bw.flush();
                        cf = true;
                        return;
                    }
                }
                if(!cf){
                    playerInform = new PlayerInform(Integer.parseInt(loginResult[1]),loginResult[2]);
                    bw.println("loginResult "+"true");
                    bw.flush();
                }
            } else {
                System.out.println(socket.getInetAddress()+"�Ƿ���¼,��ǿ�ƶϿ�����");
                return;
            }


            while(true){
                String[] packet = NET.turnPacketData(br.readLine());
                if(packet[0].equals("createRoom")){

                }
            }
        } catch (IOException e) {
            System.out.println(socket.getInetAddress()+"Զ�������ѹر�����");
        } catch (NullPointerException e) {
            System.out.println(socket.getInetAddress()+"�ذ���֤ʧ�ܣ������رս��̣�");
        }
    }

    @Override
    public String playChess() throws InterruptedException {
        synchronized (LOCK.add("playChess"+inRoom.getId())){
            LOCK.get("playChess"+inRoom.getId()).wait();
        }
        return LOCK.get("playChess"+inRoom.getId()).getInform();
    }

    @Override
    public boolean waitReady() throws InterruptedException {
        synchronized (LOCK.add("waitReady"+inRoom.getId())){
            LOCK.get("waitReady"+inRoom.getId()).wait();
        }
        return true;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getBw() {
        return bw;
    }

    public BufferedReader getBr() {
        return br;
    }
}
