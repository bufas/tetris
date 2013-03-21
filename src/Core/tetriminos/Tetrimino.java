package Core.tetriminos;

import java.awt.*;

public interface Tetrimino {

    public enum Rotation {ROTATE0, ROTATE90, ROTATE180, ROTATE270};

    /**
     * Shortcut for getArray(Rotation.ROTATE0);
     */
    public int[][] getArray();

    /**
     * Get the array describing the mino
     * @param rotation specifies if the array should be rotated before return
     * @return the array describing the mino, rotated if specified
     */
    public int[][] getArray(Rotation rotation);

    /**
     * Rotate the mino
     * @param rotation specifies how much to rotate
     */
    public void rotate(Rotation rotation);

    /**
     * Reset the rotation to the way the mino spawned
     */
    public void resetRotation();

    /**
     * Get the column in which the mino will spawn
     */
    public int getInitCol();

    /**
     * Get the row in which the mino will spawn
     */
    public int getInitRow();

    /**
     * Get the color of the mino
     */
    public Color getColor();

}
