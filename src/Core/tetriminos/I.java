package Core.tetriminos;

import java.awt.*;

public class I extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
            case 2:
                return  new boolean[][]{
                        new boolean[]{false, false, false, false},
                        new boolean[]{true,  true,  true,  true},
                        new boolean[]{false, false, false, false},
                        new boolean[]{false, false, false, false}};
            case 1:
            case 3:
                return  new boolean[][]{
                        new boolean[]{false, false, true,  false},
                        new boolean[]{false, false, true,  false},
                        new boolean[]{false, false, true,  false},
                        new boolean[]{false, false, true,  false}};
            default:
                return  new boolean[][]{
                        new boolean[]{true,  true,  true,  true},
                        new boolean[]{false, false, false, false}};
        }
    }

    @Override
    public int getInitY() { return 1; }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
