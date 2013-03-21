package Core.tetriminos;

import java.awt.*;

public class L extends TetriminoAdapter {

    int[][] getArray(int orientation) {
        switch (orientation) {
            case 0:  return new int[][]{getPair(0,0), getPair(1,0), getPair(2,0), getPair(2,1)};
            case 1:  return new int[][]{getPair(1,0), getPair(1,1), getPair(1,2), getPair(2,0)};
            case 2:  return new int[][]{getPair(0,0), getPair(0,1), getPair(1,1), getPair(2,1)};
            default: return new int[][]{getPair(0,2), getPair(1,0), getPair(1,1), getPair(1,2)};
        }
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
}
