package Core;

public interface Matrix {

    int HEIGHT = 22;
    int WIDTH  = 10;

    public enum MatrixSlot {EMPTY, GHOST,
        I_OPEN, O_OPEN, J_OPEN, L_OPEN, Z_OPEN, S_OPEN, T_OPEN,
        I_LOCKED, O_LOCKED, J_LOCKED, L_LOCKED, Z_LOCKED, S_LOCKED, T_LOCKED}

    /**
     * Returns a specified row of the matrix
     * @param row the intex of the row to return. 1 is the bottom row.
     * @return the row as an array
     */
    MatrixSlot[] getRow(int row);

    /**
     * Get the value of the slot at (row,col)
     * @param row the row
     * @param col the column
     * @return the value of the slot located at (row,col)
     */
    MatrixSlot getSlot(int row, int col);

    /**
     * Removes a row from the matrix
     * @param row the index of the row to remove. 1 is the bottom row.
     */
    void clearRow(int row);

    /**
     * Check if a row is empty.
     * @param row the row to check for emptiness
     * @return true if the row is empty false otherwise
     */
    boolean isEmpty(int row);

    /**
     * Check if a row is full.
     * @param row the row to check
     * @return true if the row is full, false otherwise
     */
    boolean isFull(int row);

}
