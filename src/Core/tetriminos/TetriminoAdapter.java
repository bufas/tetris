package Core.tetriminos;

public abstract class TetriminoAdapter implements Tetrimino {
    private int orientation = 0;

    @Override
    public Offset[] getArray() {
        return getArray(Rotation.ROTATE0);
    }

    @Override
    public Offset[] getArray(Rotation rotation) {
        switch (rotation) {
            case ROTATE0:   return getArray(orientation);
            case ROTATE90:  return getArray((orientation + 1) % 4);
            case ROTATE180: return getArray((orientation + 2) % 4);
            case ROTATE270: return getArray((orientation + 3) % 4);
            default: return null;
        }
    }

    @Override
    public void rotate(Rotation rotation) {
        switch (rotation) {
            case ROTATE90:  orientation = (orientation + 1) % 4; break;
            case ROTATE180: orientation = (orientation + 2) % 4; break;
            case ROTATE270: orientation = (orientation + 3) % 4; break;
        }
    }

    @Override
    public void resetRotation() {
        orientation = 0;
    }

    @Override
    public int getInitCol() {
        return 4;
    }

    @Override
    public int getInitRow() {
        return 20;
    }

    /**
     * Convenience method that takes two ints and returns an int array containing them
     */
    Offset getPair(int x, int y) {
        return new Offset(x,y);
    }

    abstract Offset[] getArray(int orientation);

}
