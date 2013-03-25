package Core.tetriminos;

import java.awt.*;

public class Z extends TetriminoAdapter {
    private Offset[] state0 = new Offset[]{getPair(0,1), getPair(1,1), getPair(1,0), getPair(2,0)};
    private Offset[] state1 = new Offset[]{getPair(1,0), getPair(1,1), getPair(2,1), getPair(2,2)};

    Offset[] getArray(int orientation) {
        switch (orientation) {
            case 0:
            case 2:  return state0;
            default: return state1;
        }
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
