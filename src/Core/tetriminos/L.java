package Core.tetriminos;

import java.awt.*;

public class L extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{false, false, true },
                        new boolean[]{true,  true,  true }};
            case 1:
                return  new boolean[][]{
                        new boolean[]{false, true,  false},
                        new boolean[]{false, true,  false},
                        new boolean[]{false, true,  true }};
            case 2:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{true,  true,  true },
                        new boolean[]{true,  false, false}};
            case 3:
                return  new boolean[][]{
                        new boolean[]{true,  true,  false},
                        new boolean[]{false, true,  false},
                        new boolean[]{false, true,  false}};
            default:
                return  new boolean[][]{
                        new boolean[]{true,  true,  true,  false},
                        new boolean[]{true,  false, false, false}};
        }
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
}
