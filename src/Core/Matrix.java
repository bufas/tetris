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
        return matrix[row-1];
    }

    public void clearRow(int row) {
        for (int i = row-1; i > 0; i++) {
            matrix[i] = matrix[i+1].clone();
        }
        matrix[HEIGHT-1] = emptyRow();
    }

    /**
     * Get an empty row
     * @return an empty row
     */
    private MatrixSlot[] emptyRow() {
        MatrixSlot[] result = new MatrixSlot[WIDTH];
        for (int i = 0; i < WIDTH; i++) result[i] = MatrixSlot.EMPTY;
        return result;
    }
}
