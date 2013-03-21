package Core.tetriminos;

import java.awt.*;

public class O extends TetriminoAdapter {

    int[][] getArray(int orientation) {
        return new int[][] {getPair(0,0), getPair(0,1), getPair(1,0), getPair(1,1)};
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public int getInitRow() { return 1; }

    @Override
    public int getInitCol() { return 4; }
}
