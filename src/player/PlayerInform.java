package player;

import manager.AllManager;

/**
 * playerInform¿‡
 *
 * @author HanShuo
 * @Date 2020/4/26 16:46
 */
public class PlayerInform {

    private int ID;
    private String passWord;
    private String name;
    private int playNum;
    private int winNum;
    private int loseNum;
    private int head;

    public PlayerInform(int ID, String passWord) {
        this.ID = ID;
        this.passWord = passWord;
        name = "";
        playNum = 0;
        winNum = 0;
        loseNum = 0;
        head = 0;
        AllManager.getPlayerInformList().add(this);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public int getWinNum() {
        return winNum;
    }

    public void setWinNum(int winNum) {
        this.winNum = winNum;
    }

    public int getLoseNum() {
        return loseNum;
    }

    public void setLoseNum(int loseNum) {
        this.loseNum = loseNum;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }
}
