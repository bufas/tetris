package Core.tetriminos;

import java.awt.*;

public class O extends TetriminoAdapter {
    private Offset[] state0 = new Offset[]{getPair(0,0), getPair(0,1), getPair(1,0), getPair(1,1)};

    Offset[] getArray(int orientation) {
        return state0;
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public int getInitCol() { return 5; }
}
