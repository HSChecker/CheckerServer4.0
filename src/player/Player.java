package player;

/**
 * Player�ӿ�
 *
 * @author HanShuo
 * @Date 2020/4/26 9:08
 */
public interface Player {

    String playChess() throws InterruptedException;

    boolean waitReady() throws InterruptedException;
}
