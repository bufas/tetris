package Core.tetriminos;

import java.awt.*;

public class J extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{true,  false, false},
                        new boolean[]{true,  true,  true }};
            case 1:
                return  new boolean[][]{
                        new boolean[]{false, true,  true },
                        new boolean[]{false, true,  false},
                        new boolean[]{false, true,  false}};
            case 2:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{true,  true,  true },
                        new boolean[]{false, false, true }};
            case 3:
                return  new boolean[][]{
                        new boolean[]{false, true,  false},
                        new boolean[]{false, true,  false},
                        new boolean[]{true,  true,  false}};
            default:
                return  new boolean[][]{
                        new boolean[]{true,  true,  true,  false},
                        new boolean[]{false, false, true,  false}};
        }
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }
}
