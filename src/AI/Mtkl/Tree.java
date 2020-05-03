package AI.Mtkl;

import chess.CheckBoard;
import tools.FILE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static tools.PARAMETER.PATH;

/**
 * Tree¿‡
 *
 * @author HanShuo
 * @Date 2020/5/3 15:08
 */
public class Tree {

    public static Map<CheckBoard,Node> tree;

    public static CheckBoard fatherNode = CheckBoard.newBoard();

    public static ExecutorService es;

    private static boolean Running = false;

    public Tree(int threadNum) throws IOException {

        if(Running)
            return;
        else Running = true;

        if(FILE.createFile(PATH+"/Mtkl/tree.txt"))
            tree = new HashMap<CheckBoard,Node>();
        else {
            var o = new ObjectInputStream(new FileInputStream(PATH+"/Mtkl/tree.txt"));
            tree = (Map<CheckBoard, Node>)o;
            o.close();
        }

        es = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {

            //
        }

    }

    public static void addGameStrengthenThread(){

    }

}
