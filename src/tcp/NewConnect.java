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
 * NewConnect类
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
        standardPrint("已建立监听新线程\""+th.getName()+"\"的链接");
        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new PrintWriter(socket.getOutputStream());

            /**
             * 此处禁止套娃！
             * hs手动滑稽
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
                                        standardPrint("已登陆账户："+(pi.getName().equals("")?pi.getID():pi.getName()));
                                        playerInform = pi;
                                        bw.println("loginResult true");
                                        bw.flush();
                                        y = true;
                                        break;
                                    }
                                }
                                if(y == false){
                                    bw.println("loginResult false 密码错误");
                                    bw.flush();
                                    closeLink();
                                }
                            }else{
                                for (PlayerInform pi : AllManager.getPlayerInformList()) {
                                    if (pi.getID() == Integer.parseInt(packet[2])) {
                                        bw.println("loginResult false 该账户已存在，请登录");
                                        bw.flush();
                                        closeLink();
                                    }
                                }
                                standardPrint("已登陆账户："+packet[2]);
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
            standardPrint("断开连接！");
        }

        /**
        System.out.println("已建立新线程处理"+socket.getInetAddress()+"的连接");
        try {
            bw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("已创建链接"+socket.getInetAddress()+"输入输出流，正在发送确认包");
            bw.println("sure "+ PARAMETER.version);
            bw.flush();
            System.out.println("已向"+socket.getInetAddress()+"发送确认包，等待回包");
            String[] sure = NET.turnPacketData(br.readLine());
            if(sure[0].equals("sure")) {
                System.out.println("收到" + socket.getInetAddress() + "的回包，确认成功，客户端版本：" + sure[1]);
            }
            else {
                System.out.println(socket.getInetAddress() + "回包验证失败，即将关闭进程：");
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
                    bw.println("loginResult "+"false "+"账户不存在或密码错误");
                    bw.flush();
                    return;
                }
            }else if(loginResult[0].equals("register")){
                boolean cf = false;
                for(PlayerInform pi:AllManager.getPlayerInformList()){
                    if(pi.getID() == Integer.parseInt(loginResult[1])){
                        bw.println("loginResult "+"false "+"注册账户已存在，请登录");
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
                System.out.println(socket.getInetAddress()+"非法登录,已强制断开链接");
                return;
            }


            while(true){
                String[] packet = NET.turnPacketData(br.readLine());
                if(packet[0].equals("createRoom")){

                }
            }
        } catch (IOException e) {
            System.out.println(socket.getInetAddress()+"远程主机已关闭连接");
        } catch (NullPointerException e) {
            System.out.println(socket.getInetAddress()+"回包验证失败，即将关闭进程：");
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
