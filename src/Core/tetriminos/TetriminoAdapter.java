package Core.tetriminos;

public abstract class TetriminoAdapter implements Tetrimino {
    private int orientation = 0;

    @Override
    public boolean[][] getArray(Rotation rotation) {
        if (rotation == null) return getArray(orientation);

        switch (rotation) {
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
    public int getInitX() {
        return 3;
    }

    @Override
    public int getInitY() {
        return 0;
    }

    abstract boolean[][] getArray(int orientation);

}
