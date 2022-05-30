package utils;

public class Coordinates {
    public int column;
    public int line;

    public Coordinates(int column, int line) {
        this.column = column;
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
