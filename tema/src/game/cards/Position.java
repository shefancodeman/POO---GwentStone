package game.cards;

public class Position {
    public int row;
    public int col;

    public Position() {
        this.row = 0;
        this.col = 0;
    }

    public Position(int x, int y) {
        this.row = x;
        this.col = y;
    }
    @Override
    public String toString() {
        return "Position{" +
                "x=" + row +
                ", y=" + col +
                '}';
    }
}
