package AI.Mtkl;

import java.io.Serializable;
import java.util.List;

/**
 * Node¿‡
 *
 * @author HanShuo
 * @Date 2020/5/3 15:07
 */
public class Node implements Serializable {

    private Node father;
    private List<Node> son;

    private int SimulationTime;
    private int winTime;

    public Node() {

    }

    public void addWin(){
        winTime++;
        SimulationTime++;
    }

    public void addLose(){
        SimulationTime++;
    }

    public Node getFather() {
        return father;
    }

    public List<Node> getSon() {
        return son;
    }

    public int getSimulationTime() {
        return SimulationTime;
    }

    public int getWinTime() {
        return winTime;
    }
}
