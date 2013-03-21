package Core.tetriminos;

import java.awt.*;

public class T extends TetriminoAdapter {

    boolean[][] getArray(int orientation) {
        switch (orientation) {
            case 0:
                return  new boolean[][]{
                        new boolean[]{false, true,  false},
                        new boolean[]{true,  true,  true },
                        new boolean[]{false, false, false}};
            case 1:
                return  new boolean[][]{
                        new boolean[]{false, true,  false},
                        new boolean[]{false, true,  true },
                        new boolean[]{false, true,  false}};
            case 2:
                return  new boolean[][]{
                        new boolean[]{false, false, false},
                        new boolean[]{true,  true,  true },
                        new boolean[]{false, true,  false}};
            case 3:
                return  new boolean[][]{
                        new boolean[]{false, true,  false},
                        new boolean[]{true,  true,  false},
                        new boolean[]{false, true,  false}};
            default:
                return  new boolean[][]{
                        new boolean[]{true,  true,  true,  false},
                        new boolean[]{false, true,  false, false}};
        }
    }

    @Override
    public int getInitY() { return 1; }

    @Override
    public Color getColor() {
        return new Color(255, 0, 255);
    }
}
