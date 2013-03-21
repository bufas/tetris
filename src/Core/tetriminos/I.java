package Core.tetriminos;

import java.awt.*;

public class I extends TetriminoAdapter {

    int[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
            case 2:  return new int[][]{getPair(0,2), getPair(1,2), getPair(2,2), getPair(3,2)};
            default: return new int[][]{getPair(2,0), getPair(2,1), getPair(2,2), getPair(2,3)};
        }
    }

    @Override
    public int getInitRow() { return 1; }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
