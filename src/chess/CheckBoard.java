package chess;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.List;

/**
 * CheckBoard类
 *
 * @author HanShuo
 * @Date 2020/5/3 11:42
 */
public record CheckBoard(long notEmoty,long isBlack,long isKing,boolean blackPlay) implements Serializable {

    public static CheckBoard newBoard(){
        return new CheckBoard(0b11111111111111111111000000000011111111111111111111L,0b11111111111111111111L,0L,true);
    }

    public CheckBoard road(roadProbably r){
        return new CheckBoard(notEmoty^r.notEmoty(),isBlack^r.isBlack(),isKing^r.isKing(),!blackPlay);
    }

    //此处迫于无奈而写的方法，原计划仅用 & | ~ ^ 计算棋盘价值与下一步，奈何写不出来....只能转换再计算
    public ChessType[][] turnList(){
        ChessType[][] a = new ChessType[10][10];
        for(int i=0;i<50;i++){
            int y = (int)(i/5);
            int x = (i - y*5)*2 + ((y%2)==0?1:0);
            if((notEmoty>>>i) % 2 == 0)
                if((isBlack>>>i) % 2 == 1)
                    a[x][y] = ChessType.Obstacle;
                else
                    a[x][y] = ChessType.Emotym;
            else
                if((isBlack>>>i) % 2 == 1)
                    if((isKing>>>i) % 2 == 1)
                        a[x][y] = ChessType.BlackKing;
                    else
                        a[x][y] = ChessType.Black;
                else
                    if((isKing>>>i) % 2 == 1)
                        a[x][y] = ChessType.WhiteKing;
                    else
                        a[x][y] = ChessType.White;
        }
        return a;
    }

    public List<roadProbably> returnProbably(){
        return null;
    }

    public ChessType returnType(int i){
        if((notEmoty>>>i) % 2 == 0)
            if((isBlack>>>i) % 2 == 1)
                return ChessType.Obstacle;
            else
                return ChessType.Emotym;
        else
            if((isBlack>>>i) % 2 == 1)
                if((isKing>>>i) % 2 == 1)
                    return ChessType.BlackKing;
                else
                    return ChessType.Black;
            else
                if((isKing>>>i) % 2 == 1)
                    return ChessType.WhiteKing;
                else
                    return ChessType.White;

    }

    public int turnEastNorth(int i){
        return i-((((int)(i/5))%2 == 0)?5:6);
    }
    public int turnEastSouth(int i){
        return i+((((int)(i/5))%2 == 0)?5:4);
    }
    public int turnWestSouth(int i){
        return i+((((int)(i/5))%2 == 0)?6:5);
    }
    public int turnWestNorth(int i){
        return i-((((int)(i/5))%2 == 0)?4:5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckBoard)) return false;
        CheckBoard that = (CheckBoard) o;
        if (notEmoty != that.notEmoty) return false;
        if (isBlack != that.isBlack) return false;
        if (isKing != that.isKing) return false;
        return blackPlay == that.blackPlay;
    }

    /**
     *不重写哈希码方法，嗯....觉得可能用object的哈希方式更靠谱更快....
        @Override
        public int hashCode() {
            int result = (int) (notEmoty ^ (notEmoty >>> 32));
            result = 31 * result + (int) (isBlack ^ (isBlack >>> 32));
            result = 31 * result + (int) (isKing ^ (isKing >>> 32));
            result = 31 * result + (blackPlay ? 1 : 0);
            return result;
        }
    */
}
