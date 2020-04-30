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
 * NewConnect类
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
