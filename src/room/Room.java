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
public class Room implements Runnable {

    private static int idNum = 10000;
    private int id;
    private Player white;
    private Player black;
    private boolean whiteReady = false;
    private boolean blackReady = false;

    public Room(Player black, Player white) {
        AllManager.getRoomList().add(this);
        this.white = white;
        this.black = black;
        id = ++idNum;
    }

    @Override
    public void run() {

    }

    public void whiteReady() {
        whiteReady = true;
        if (blackReady)
            new Thread(this).start();
    }

    public void blackReady() {
        blackReady = true;
        if (whiteReady)
            new Thread(this).start();
    }

    public void whiteJoin(Player w) {
        white = w;
    }

    public void blackJoin(Player b) {
        black = b;
    }

    public void whiteLeave(){
        white = null;
        whiteReady = false;
    }

    public void blackLeave(){
        black = null;
        blackReady = false;
    }

    public int getId() {
        return id;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }
}
