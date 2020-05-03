import AI.AiUtil;
import chess.CheckBoard;
import chess.roadProbably;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Test¿‡
 *
 * @author HanShuo
 * @Date 2020/5/3 11:18
 */
public class Test {

    public static void main(String[] ar) {

        List a = AiUtil.getProbably(new CheckBoard(0b1000001,1,0,true));
        var b = new CheckBoard(0b1000001,1,0,true).road((roadProbably) a.get(0)).turnList();
        var c = new CheckBoard(0b1000001,1,0,true).road((roadProbably) a.get(1)).turnList();
        System.out.println("666");

    }

}
