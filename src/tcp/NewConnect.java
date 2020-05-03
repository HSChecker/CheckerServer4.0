package tcp;

import manager.AllManager;
import player.Player;
import player.PlayerInform;
import room.Room;
import tcp.connectPackage.ConnectIntyerface;
import tcp.connectPackage.ConnectListen;
import tcp.connectPackage.permanentPacket.createRoom;
import tools.LOCK;
import tools.NET;
import tools.PARAMETER;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * NewConnect��
 *
 * @author HanShuo
 * @Date 2020/4/24 20:40
 */
public class NewConnect implements Runnable, Player {

    private NewConnect T = this;
    private Socket socket;
    private PrintWriter bw;
    private Room inRoom;
    private PlayerInform playerInform;

    private Thread th;

    private List<ConnectIntyerface> Listen = new ArrayList<ConnectIntyerface>();

    public NewConnect(Socket socket) {
        AllManager.getNewConnectList().add(this);
        this.socket = socket;
        th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        standardPrint("�ѽ����������߳�\""+th.getName()+"\"������");
        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new PrintWriter(socket.getOutputStream());

            /**
             * �˴���ֹ���ޣ�
             * hs�ֶ�����
             */
            addListen(new ConnectListen("requseConnect") {
                @Override
                public void run(String[] packet) {
                    bw.println("connect true");
                    bw.flush();
                    addListen(new ConnectListen("login") {
                        @Override
                        public void run(String[] packet) {
                            boolean y = false;
                            if(packet[1].equals("login")) {
                                for (PlayerInform pi : AllManager.getPlayerInformList()) {
                                    if ((pi.getID() == Integer.parseInt(packet[2]) || pi.getName().equals(packet[2])) && pi.getPassWord().equals(packet[3])) {
                                        standardPrint("�ѵ�½�˻���"+(pi.getName().equals("")?pi.getID():pi.getName()));
                                        playerInform = pi;
                                        bw.println("loginResult true");
                                        bw.flush();
                                        y = true;
                                        break;
                                    }
                                }
                                if(y == false){
                                    bw.println("loginResult false �������");
                                    bw.flush();
                                    closeLink();
                                }
                            }else{
                                for (PlayerInform pi : AllManager.getPlayerInformList()) {
                                    if (pi.getID() == Integer.parseInt(packet[2])) {
                                        bw.println("loginResult false ���˻��Ѵ��ڣ����¼");
                                        bw.flush();
                                        closeLink();
                                    }
                                }
                                standardPrint("�ѵ�½�˻���"+packet[2]);
                                playerInform = new PlayerInform(Integer.parseInt(packet[2]),packet[3]);
                                bw.println("loginResult true");
                                bw.flush();
                            }
                            addListen(new createRoom(T));
                        }
                    });
                }
            });

            while(true){
                String m = br.readLine();
                if(m!=null) {
                    String[] packet = NET.turnPacketData(m);
                    for (ConnectIntyerface li : Listen) {
                        if (li.whetherRun(packet[0])) {
                            li.run(packet);
                            if (!li.isPermanent())
                                Listen.remove(li);
                        }
                    }
                } else {
                    return;
                }
            }


        } catch (ConnectException e){

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br!=null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bw!=null) {
                bw.flush();
                bw.close();
            }
            standardPrint("�Ͽ����ӣ�");
        }

        /**
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
         */
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

    public void closeLink(){
        try {
            if(socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            th.stop();
        }
    }

    public void standardPrint(String massage){
        Date a = new Date();
        String m = "";
        m += "["+a.getYear()+"/"+a.getMonth()+"/"+a.getDay()+" "+a.getHours()+":"+a.getMinutes()+":"+a.getSeconds()+"] ";
        if(playerInform == null || playerInform.getName().equals(""))
            m += "("+socket.getInetAddress()+":"+socket.getLocalPort()+"):";
        else
            m += "("+playerInform.getName()+"):";
        System.out.println(m+massage);
    }

    public void addListen(ConnectIntyerface ct){
        Listen.add(ct);
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getBw() {
        return bw;
    }
}
