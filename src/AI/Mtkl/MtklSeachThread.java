package AI.Mtkl;

import AI.Mtkl.SeachLevelContrally.LevelContrally;
import chess.CheckBoard;

/**
 * MtchSeachThread¿‡
 *
 * @author HanShuo
 * @Date 2020/5/3 15:59
 */
public class MtklSeachThread implements Runnable{

    LevelContrally levelContrally;
    CheckBoard fatherNode;

    public MtklSeachThread(LevelContrally levelContrally, CheckBoard fatherNode) {
        this.levelContrally = levelContrally;
        this.fatherNode = fatherNode;
        Tree.es.submit(this);
    }

    @Override
    public void run() {

        //for(var i:Tree.tree.get(fatherNode).){

        //}

    }
}
