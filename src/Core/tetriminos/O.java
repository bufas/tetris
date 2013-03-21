package Core.tetriminos;

import java.awt.*;

public class O extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        if (orientation < 0 || orientation > 3) {
            return new boolean[][]{
                    new boolean[]{false, true, true, false},
                    new boolean[]{false, true, true, false}};
        }

        return  new boolean[][]{
                new boolean[]{true, true},
                new boolean[]{true, true}};
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public int getInitY() { return 1; }

    @Override
    public int getInitX() { return 4; }
}
