package Core;

import Core.tetriminos.*;

public class MatrixImpl implements Matrix {

    private MatrixSlot[][] matrix;

    public MatrixImpl() {
        // Initialize the matrix
        matrix = new MatrixSlot[HEIGHT][WIDTH];
        for(int row = 0; row < HEIGHT; row++) matrix[row] = emptyRow();
    }

    @Override
    public MatrixSlot[] getRow(int row) {
        return matrix[row2index(row)].clone();
    }

    @Override
    public MatrixSlot getSlot(int row, int col) {
        return matrix[row2index(row)][col2index(col)];
    }

    @Override
    public int getGhostYCoordinate(int row, int col, Tetrimino mino) {
        int result = row;
        while(!isCollision(result-1, col, mino)) {
            result--;
        }
        return result;
    }

    @Override
    public boolean isCollision(int row, int col, Tetrimino mino) {
        return isCollision(row, col, mino, Tetrimino.Rotation.ROTATE0);
    }

    @Override
    public boolean isCollision(int row, int col, Tetrimino mino, Tetrimino.Rotation rotation) {
        for (Offset offset : mino.getArray(rotation))
            if (!slotIsEmpty(row + offset.getY(), col + offset.getX())) return true;
        return false;
    }

    @Override
    public boolean slotIsEmpty(int row, int col) {
        if (outOfBounds(row, col)) return false;
        return matrix[row2index(row)][col2index(col)] == MatrixSlot.EMPTY;
    }

    @Override
    public void clearRow(int row) {
        for (int i = row2index(row); i < HEIGHT-1; i++)
            matrix[i] = matrix[i+1].clone();
        matrix[HEIGHT-1] = emptyRow();
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(1);
    }

    @Override
    public void lockMino(Tetrimino mino, int row, int col) {
        MatrixSlot type = null;
        if      (mino instanceof I) type = MatrixSlot.I_LOCKED;
        else if (mino instanceof J) type = MatrixSlot.J_LOCKED;
        else if (mino instanceof L) type = MatrixSlot.L_LOCKED;
        else if (mino instanceof O) type = MatrixSlot.O_LOCKED;
        else if (mino instanceof S) type = MatrixSlot.S_LOCKED;
        else if (mino instanceof Z) type = MatrixSlot.Z_LOCKED;
        else if (mino instanceof T) type = MatrixSlot.T_LOCKED;

        for (Offset offset : mino.getArray())
            setSlot(row + offset.getY(), col + offset.getX(), type);
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
     * Fills a slot in the matrix with a specified type
     */
    private void setSlot(int row, int col, MatrixSlot type) {
        if (!outOfBounds(row, col))
            matrix[row2index(row)][col2index(col)] = type;
    }

    /**
     * Check if the row or column given is not inside the matrix
     * @return true if the row or column is not inside the matrix, false otherwise
     */
    private boolean outOfBounds(int row, int col) {
        return row > HEIGHT || row < 1 || col < 1 || col > WIDTH;
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
