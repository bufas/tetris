package Core.tetriminos;

import java.awt.*;

public class T extends TetriminoAdapter {

    int[][] getArray(int orientation) {
        switch (orientation) {
            case 0:  return new int[][]{getPair(0,1), getPair(1,1), getPair(1,2), getPair(2,1)};
            case 1:  return new int[][]{getPair(1,0), getPair(1,1), getPair(1,2), getPair(2,2)};
            case 2:  return new int[][]{getPair(0,1), getPair(1,0), getPair(1,1), getPair(2,1)};
            default: return new int[][]{getPair(0,1), getPair(1,0), getPair(1,1), getPair(1,2)};
        }
    }

    @Override
    public int getInitRow() { return 1; }

    @Override
    public Color getColor() {
        return new Color(255, 0, 255);
    }
}
