package Core;

public class Matrix {

    public enum MatrixSlot {EMPTY, GHOST,
                            I_OPEN, O_OPEN, J_OPEN, L_OPEN, Z_OPEN, S_OPEN, T_OPEN,
                            I_LOCKED, O_LOCKED, J_LOCKED, L_LOCKED, Z_LOCKED, S_LOCKED, T_LOCKED};

    public static final int HEIGHT = 22;
    public static final int WIDTH  = 10;

    private MatrixSlot[][] matrix;

    public Matrix() {
        // Initialize the matrix
        matrix = new MatrixSlot[HEIGHT][WIDTH];
        for(int row = 0; row < HEIGHT; row++) matrix[row] = emptyRow();
    }

    /**
     * Returns a specified row of the matrix
     * @param row the intex of the row to return. 1 is the bottom row.
     * @return the row as an array
     */
    public MatrixSlot[] getRow(int row) {
        return matrix[row2index(row)];
    }

    /**
     * Get the value of the slot at (row,col)
     * @param row the row
     * @param col the column
     * @return the value of the slot located at (row,col)
     */
    public MatrixSlot getSlot(int row, int col) {
        return matrix[row2index(row)][col2index(col)];
    }

    /**
     * Removes a row from the matrix
     * @param row the index of the row to remove. 1 is the bottom row.
     */
    public void clearRow(int row) {
        for (int i = row2index(row); i > 0; i++) {
            matrix[i] = matrix[i+1].clone();
        }
        matrix[HEIGHT-1] = emptyRow();
    }

    /**
     * Check if a row is empty.
     * @param row the row to check for emptiness
     * @return true if the row is empty false otherwise
     */
    public boolean isEmpty(int row) {
        for (MatrixSlot slot : matrix[row2index(row)])
            if (slot != MatrixSlot.EMPTY) return false;
        return true;
    }

    /**
     * Check if a row is full.
     * @param row the row to check
     * @return true if the row is full, false otherwise
     */
    public boolean isFull(int row) {
        for (MatrixSlot slot : matrix[row2index(row)])
            if (slot == MatrixSlot.EMPTY) return false;
        return true;
    }

    /**
     * Helper method to get an empty row
     * @return an empty row
     */
    private MatrixSlot[] emptyRow() {
        MatrixSlot[] result = new MatrixSlot[WIDTH];
        for (int i = 0; i < WIDTH; i++) result[i] = MatrixSlot.EMPTY;
        return result;
    }

    /**
     * Converts a row number to the corresponding index in the array
     * @param row row number to convert
     * @return the index of the row in the array
     */
    private int row2index(int row) { return row-1; }

    /**
     * Converts a column number to the corresponding index in the array
     * @param col column number to convert
     * @return the index of the column in the array
     */
    private int col2index(int col) { return col-1; }
}
