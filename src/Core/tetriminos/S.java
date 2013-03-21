package Core.tetriminos;

import java.awt.*;

public class S extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
            case 2:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{false, true,  true },
                        new boolean[]{true,  true,  false}};
            case 1:
            case 3:
                return  new boolean[][]{
                        new boolean[]{true,  false, false},
                        new boolean[]{true,  true,  false},
                        new boolean[]{false, true,  false}};
            default:
                return  new boolean[][]{
                        new boolean[]{false, true,  true },
                        new boolean[]{true,  true,  false}};
        }
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
