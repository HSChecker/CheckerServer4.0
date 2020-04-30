package manager;

import player.PlayerInform;
import room.Room;
import tcp.NewConnect;
import tools.FILE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static tools.PARAMETER.PATH;

/**
 * AllManager¿‡
 *
 * @author HanShuo
 * @Date 2020/4/26 8:52
 */
public class AllManager {

    private static List<NewConnect> newConnectList = new ArrayList<NewConnect>();

    private static List<Room> roomList = new ArrayList<Room>();

    private static List<PlayerInform> playerInformList;

    public static List<PlayerInform> getPlayerInformList(){
        if(playerInformList == null){
            try {
                if(FILE.createFile(PATH+"/PlayerInform.txt")){
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH+"/PlayerInform.txt"));
                    oos.writeObject(new ArrayList<PlayerInform>());
                    oos.flush();
                    oos.close();
                }
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH+"/PlayerInform.txt"));
                playerInformList = (ArrayList)ois.readObject();
                ois.close();
                return playerInformList;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return playerInformList;
    }

    public static List<NewConnect> getNewConnectList() {
        return newConnectList;
    }

    public static List<Room> getRoomList() {
        return roomList;
    }
}
