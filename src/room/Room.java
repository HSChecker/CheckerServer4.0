package room;

import manager.AllManager;
import player.Player;
import tcp.NewConnect;

/**
 * Room¿‡
 *
 * @author HanShuo
 * @Date 2020/4/26 8:54
 */
public class Room implements Runnable{

    private int id;
    private Player white;
    private Player Black;

    public Room(Player white, Player black) {
        AllManager.getRoomList().add(this);
        this.white = white;
        Black = black;
    }

    @Override
    public void run() {
        try {
            white.waitReady();
            white.waitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(NewConnect white) {
        this.white = white;
    }

    public Player getBlack() {
        return Black;
    }

    public void setBlack(NewConnect black) {
        Black = black;
    }
}
