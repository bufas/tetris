package Core.tetriminos;

import java.awt.*;

public interface Tetrimino {

    public enum Rotation {ROTATE90, ROTATE180, ROTATE270};

    public boolean[][] getArray(Rotation rotation);

    public void rotate(Rotation rotation);

    public void resetRotation();

    public int getInitX();

    public int getInitY();

    public Color getColor();

}
