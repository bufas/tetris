package Core;

public class MatrixImpl implements Matrix {

    private MatrixSlot[][] matrix;

    public MatrixImpl() {
        // Initialize the matrix
        matrix = new MatrixSlot[HEIGHT][WIDTH];
        for(int row = 0; row < HEIGHT; row++) matrix[row] = emptyRow();
    }

    @Override
    public MatrixSlot[] getRow(int row) {
        return matrix[row2index(row)];
    }

    @Override
    public MatrixSlot getSlot(int row, int col) {
        return matrix[row2index(row)][col2index(col)];
    }

    @Override
    public void clearRow(int row) {
        for (int i = row2index(row); i > 0; i++) {
            matrix[i] = matrix[i+1].clone();
        }
        matrix[HEIGHT-1] = emptyRow();
    }

    @Override
    public boolean isEmpty(int row) {
        for (MatrixSlot slot : matrix[row2index(row)])
            if (slot != MatrixSlot.EMPTY) return false;
        return true;
    }

    @Override
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
