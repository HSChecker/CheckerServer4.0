package chess;

public enum ChessType {
    Emotym,
    Black,
    White,
    BlackKing,
    WhiteKing,
    Obstacle;

    public boolean differentTeam(ChessType c){
        if(this.equals(Black) || this.equals(BlackKing))
            if(c.equals(White) || c.equals(WhiteKing))
                return true;
            else
                return false;
        else if(this.equals(White) || this.equals(WhiteKing))
            if(c.equals(BlackKing) || c.equals(Black))
                return true;
            else return false;
        else
            return false;
    }

    public boolean isKing(){
        if(this.equals(BlackKing) || this.equals(WhiteKing))
            return true;
        return false;
    }

    public boolean isWhite(){
        if(this.equals(White) || this.equals(WhiteKing))
            return true;
        return false;
    }

    public boolean isBlack(){
        if(this.equals(BlackKing) || this.equals(Black))
            return true;
        return false;
    }
}
