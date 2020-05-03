package chess;

public record roadProbably(long notEmoty,long isBlack,long isKing) {

    public long notEmoty() {
        return notEmoty;
    }

    public long isBlack() {
        return isBlack;
    }

    public long isKing() {
        return isKing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof roadProbably)) return false;

        roadProbably that = (roadProbably) o;

        if (notEmoty != that.notEmoty) return false;
        if (isBlack != that.isBlack) return false;
        return isKing == that.isKing;
    }
}
