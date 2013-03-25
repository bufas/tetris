package Core.tetriminos;

import java.awt.*;

public class T extends TetriminoAdapter {
    private Offset[] state0 = new Offset[]{getPair(0,1), getPair(1,1), getPair(1,2), getPair(2,1)};
    private Offset[] state1 = new Offset[]{getPair(1,0), getPair(1,1), getPair(1,2), getPair(2,1)};
    private Offset[] state2 = new Offset[]{getPair(0,1), getPair(1,0), getPair(1,1), getPair(2,1)};
    private Offset[] state3 = new Offset[]{getPair(0,1), getPair(1,0), getPair(1,1), getPair(1,2)};

    Offset[] getArray(int orientation) {
        switch (orientation) {
            case 0:  return state0;
            case 1:  return state1;
            case 2:  return state2;
            default: return state3;
        }
    }

    @Override
    public int getInitRow() {
        return 19;
    }

    @Override
    public Color getColor() {
        return new Color(255, 0, 255);
    }
}
