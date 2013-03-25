package Core.tetriminos;

import java.awt.*;

public class I extends TetriminoAdapter {

    private Offset[] state0 = new Offset[]{getPair(0,2), getPair(1,2), getPair(2,2), getPair(3,2)};
    private Offset[] state2 = new Offset[]{getPair(2,0), getPair(2,1), getPair(2,2), getPair(2,3)};

    Offset[] getArray(int orientation) {
        switch (orientation) {
            case 0:
            case 2:  return state0;
            default: return state2;
        }
    }

    @Override
    public int getInitRow() {
        return 18;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
