package Core;

import Core.tetriminos.Tetrimino;

public interface Matrix {

    int HEIGHT = 22;
    int WIDTH  = 10;

    public enum MatrixSlot {EMPTY, GHOST, FILLED, // TODO remove 'FILLED'
        I_OPEN, O_OPEN, J_OPEN, L_OPEN, Z_OPEN, S_OPEN, T_OPEN,
        I_LOCKED, O_LOCKED, J_LOCKED, L_LOCKED, Z_LOCKED, S_LOCKED, T_LOCKED}

    /**
     * Returns a specified row of the matrix
     * @param row the intex of the row to return. 1 is the bottom row.
     * @return the row as an array
     */
    public MatrixSlot[] getRow(int row);

    /**
     * Get the value of the slot at (row,col)
     * @return the value of the slot located at (row,col)
     */
    public MatrixSlot getSlot(int row, int col);

    /**
     * Shorthand for isCollision(row, col, mino, Tetrimino.Rotation.ROTATE0)
     */
    public boolean isCollision(int row, int col, Tetrimino mino);

    /**
     * Checks if it will be a collision to place a given tetrimino at a given
     * point
     * @return true if it produces a collision, false otherwise
     */
    public boolean isCollision(int row, int col, Tetrimino mino, Tetrimino.Rotation rotation);

    /**
     * Check if a slot is empty
     * @return true if the the slot is empty, false otherwise
     */
    public boolean slotIsEmpty(int row, int col);

    /**
     * Removes a row from the matrix
     * @param row the index of the row to remove. 1 is the bottom row.
     */
    public void clearRow(int row);

    /**
     * Check if the matrix is completely empty
     * @return true if the matrix is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Lock a mino in the matrix
     * @param mino the mino to be locked
     * @param row the row of the bottom left corner
     * @param col the column of the bottom left corner
     */
    public void lockMino(Tetrimino mino, int row, int col);

    /**
     * Check if a row is empty.
     * @param row the row to check for emptiness
     * @return true if the row is empty false otherwise
     */
    public boolean isEmpty(int row);

    /**
     * Check if a row is full.
     * @param row the row to check
     * @return true if the row is full, false otherwise
     */
    public boolean isFull(int row);

}
