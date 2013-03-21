package Core.tetriminos;

import java.awt.*;

public class Z extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
            case 2:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{true,  true,  false},
                        new boolean[]{false, true,  true }};
            case 1:
            case 3:
                return  new boolean[][]{
                        new boolean[]{false, false, true },
                        new boolean[]{false, true,  true },
                        new boolean[]{false, true,  false}};
            default:
                return  new boolean[][]{
                        new boolean[]{true,  true,  false, false},
                        new boolean[]{false, true,  true,  false}};
        }
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
