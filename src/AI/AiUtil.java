package AI;

import chess.CheckBoard;
import chess.ChessType;
import chess.roadProbably;

import java.util.ArrayList;
import java.util.List;

/**
 * AiUtil类
 *
 * @author HanShuo
 * @Date 2020/5/3 18:13
 */
public class AiUtil {

    public static List<roadProbably> getProbably(CheckBoard checkBoard) {
        var rp = new ArrayList<roadProbably>();
        nextJump(checkBoard,rp,checkBoard);
        if(rp.isEmpty()){
            //单步不吃子判断(累了，明天记得写)
        }
        return rp;
    }

    public static void nextJump(CheckBoard rootCheckBoard,ArrayList<roadProbably> rp,CheckBoard checkBoard) {
        var cb = checkBoard.turnList();
        for(int i=0;i<50;i++){
            if((checkBoard.notEmoty()>>>i) % 2 == 1 && (checkBoard.isBlack()>>>i) % 2 == (checkBoard.blackPlay()?1:0))
                if(!canPlay(rootCheckBoard,rp,checkBoard,i,checkBoard.returnType(i).isKing()) && !checkBoard.equals(rootCheckBoard))
                    rp.add(new roadProbably(rootCheckBoard.notEmoty()^checkBoard.notEmoty(),rootCheckBoard.isBlack()^checkBoard.isBlack(),rootCheckBoard.isKing()^checkBoard.isKing()));
        }
    }

    private static boolean canPlay(CheckBoard rootCheckBoard,ArrayList<roadProbably> rp,CheckBoard checkBoard,int i,boolean isKing){
        boolean canPlay = false;
        int wa=checkBoard.turnEastNorth(i),as=checkBoard.turnEastSouth(i),sd=checkBoard.turnWestSouth(i),dw=checkBoard.turnWestNorth(i);
        for(int hight=1;hight<(isKing?9:2) && wa%5!=0;hight++){
            if(checkBoard.returnType(i).differentTeam(checkBoard.returnType(wa))) {
                int _wa = wa;
                wa=checkBoard.turnEastNorth(wa);
                for (int hight2 = 1; hight2 < (isKing ? 9 : 2); hight2++)
                    if (checkBoard.returnType(wa).equals(ChessType.Emotym)) {
                        //这里全部修改成在roadProbably类里加turn(int i,ChessType x,ChessType y)将i位置的x转换成y，统一操作，避免失误
                        nextJump(rootCheckBoard, rp, checkBoard.road(new roadProbably((1 << i) | (1 << wa), ((checkBoard.returnType(i).equals(ChessType.Obstacle)?1:0) << i) | (1 << _wa), 0)));
                        canPlay = true;
                    }
                break;
            }else
                wa=checkBoard.turnEastNorth(wa);
        }
        for(int hight=1;hight<(isKing?9:2) && as%5!=0;hight++){
            if(checkBoard.returnType(i).differentTeam(checkBoard.returnType(as))){
                int _as = as;
                as=checkBoard.turnEastSouth(as);
                for (int hight2 = 1; hight2 < (isKing ? 9 : 2); hight2++)
                    if (checkBoard.returnType(as).equals(ChessType.Emotym)) {
                        nextJump(rootCheckBoard, rp, checkBoard.road(new roadProbably((1 << i) | (1 << as), ((checkBoard.returnType(i).equals(ChessType.Obstacle)?1:0) << i) | (1 << as), 0)));
                        canPlay = true;
                    }
                break;
            }else
                as=checkBoard.turnEastSouth(as);
        }
        for(int hight=1;hight<(isKing?9:2) && sd%5!=4;hight++){
            if(checkBoard.returnType(i).differentTeam(checkBoard.returnType(sd))){
                int _sd = sd;
                sd=checkBoard.turnWestSouth(sd);
                for (int hight2 = 1; hight2 < (isKing ? 9 : 2); hight2++)
                    if (checkBoard.returnType(sd).equals(ChessType.Emotym)) {
                        nextJump(rootCheckBoard, rp, checkBoard.road(new roadProbably((1 << i) | (1 << sd), ((checkBoard.returnType(i).equals(ChessType.Obstacle)?1:0) << i) | (1 << sd), 0)));
                        canPlay = true;
                    }
                break;
            }else
                sd=checkBoard.turnWestSouth(sd);
        }
        for(int hight=1;hight<(isKing?9:2) && dw%5!=4;hight++){
            if(checkBoard.returnType(i).differentTeam(checkBoard.returnType(dw))){
                int _dw = dw;
                dw=checkBoard.turnWestNorth(dw);
                for (int hight2 = 1; hight2 < (isKing ? 9 : 2); hight2++)
                    if (checkBoard.returnType(dw).equals(ChessType.Emotym)) {
                        nextJump(rootCheckBoard, rp, checkBoard.road(new roadProbably((1 << i) | (1 << dw), ((checkBoard.returnType(i).equals(ChessType.Obstacle)?1:0) << i) | (1 << dw), 0)));
                        canPlay = true;
                    }
                break;
            }else
                dw=checkBoard.turnWestNorth(dw);
        }
        return canPlay;
    }

}
