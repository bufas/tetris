package Core;

import Core.queue.MinoPickStrategy;
import Core.rotation.RotationStrategy;
import Core.scoring.ScoringStrategy;
import Core.tetriminos.T;
import Core.tetriminos.Tetrimino;

import java.awt.*;
import java.util.Arrays;

public class Engine {

    public enum Move {NONE, SINGLE, DOUBLE, TRIPLE, TETRIS, TSPIN, TSPINDOUBLE};

    public static final int HEIGHT = 22;
    public static final int WIDTH  = 10;

    private Color[][] matrix;
    private Tetrimino mino;

    private int xPos;
    private int yPos;

    private NotifyHelper notifyHelper;

    private RotationStrategy rotationStrategy;
    private ScoringStrategy scoringStrategy;
    private MinoPickStrategy minoPickStrategy;

    private boolean holdUsed = false;
    private Tetrimino holdSlot = null;

    private int comboCounter = -1;

    private boolean floorKickUsed = false;
    private Move lastLockdown = Move.NONE;

    public Engine(RotationStrategy rotation, ScoringStrategy scoring, MinoPickStrategy minoPick) {
        rotationStrategy = rotation;
        scoringStrategy = scoring;
        minoPickStrategy = minoPick;

        notifyHelper = new NotifyHelper();

        // Initialize the matrix
        matrix = new Color[HEIGHT][WIDTH];
        for(int y=0; y<HEIGHT; y++) {
            for(int x=0; x<WIDTH; x++) {
                matrix[y][x] = null;
            }
        }

        matrix[HEIGHT-3] = new Color[] {Color.blue,Color.blue,Color.blue,null,null,Color.blue,Color.blue,Color.blue,Color.blue,Color.blue};
        matrix[HEIGHT-2] = new Color[] {Color.blue,Color.blue,null,null,null,Color.blue,Color.blue,Color.blue,Color.blue,Color.blue};
        matrix[HEIGHT-1] = new Color[] {Color.blue,Color.blue,Color.blue,null,Color.blue,Color.blue,Color.blue,Color.blue,Color.blue,Color.blue};

        mino = minoPickStrategy.getTetrimino();
        xPos = mino.getInitX();
        yPos = mino.getInitY();
    }

    /**
     * Register an observer
     * @param obs the observer to be registered
     */
    public void registerObserver(GameObserver obs) { notifyHelper.registerObserver(obs); }

    /**
     * Remove an observer
     * @param obs the observer to be removed
     */
    public void removeObserver(GameObserver obs) { notifyHelper.removeObserver(obs); }

    /**
     * Move the tetrimino one slot down if possible. If it is not possible
     * to move down, the mino will get locked.
     */
    public void softDrop() {
        if (!collision(xPos, yPos+1, mino.getArray(null))) {
            yPos++;
            scoringStrategy.scoreSoftDrop(1);
            notifyHelper.notifySoftDrop();
        } else {
            lockPiece();
        }
    }

    /**
     * Drop the tetrimino and lock it.
     */
    public void hardDrop() {
        int length = 0;
        while(!collision(xPos, yPos+length+1, mino.getArray(null))) length++;

        yPos += length;
        scoringStrategy.scoreHardDrop(length);
        notifyHelper.notifyHardDrop(length);
        lockPiece();
    }

    /**
     * Put the current piece in the hold queue.
     * This can only be done once pr. mino
     */
    public void hold() {
        if (!holdUsed) {
            // Reset rotation so it does not spawn wierdly
            mino.resetRotation();

            // Switch the two minos or grab the next if nothing is in the hold spot
            Tetrimino temp = holdSlot;
            if (holdSlot == null) temp = minoPickStrategy.getTetrimino();
            holdSlot = mino;
            mino = temp;
            holdUsed = true;

            // Reset the height
            xPos = mino.getInitX();
            yPos = mino.getInitY();

            // Notify observers
            notifyHelper.notifyHoldTetrimino();
        }
        else notifyHelper.notifyHoldTetriminoFail();
    }

    /**
     * Merge a tetrimino into the matrix
     * @param matrix the matrix to merge the mino into
     */
    private void copyMinoToMatrix(Color[][] matrix) {
        // Add the current piece to the matrix
        boolean[][] minoArr = mino.getArray(null);
        for (int y=0; y < minoArr.length; y++) {
            for (int x=0; x < minoArr[0].length; x++) {
                boolean outOfBounds = xPos + x < 0 || xPos + x >= WIDTH || yPos + y < 0 || yPos + y >= HEIGHT;
                if (outOfBounds || !minoArr[y][x]) continue;
                matrix[yPos+y][xPos+x] = mino.getColor();
            }
        }
    }

    /**
     * Lock a tetrimino to the matrix and spawn the next one
     */
    private void lockPiece() {
        copyMinoToMatrix(matrix);

        Move thisMove = Move.NONE;
        boolean possibleMatrixClean = false;

        // Clear lines
        int lines = 0;
        for (int i = 0; i < mino.getArray(null).length; i++) {
            for (boolean cell : mino.getArray(null)[i]) {
                if (cell) {
                    // Line yPos + i is possibly filled, check it
                    int row = yPos+i;
                    boolean clear = true;

                    // Check that there are no empty slots in the row
                    for (Color c : matrix[row]) {
                        if (c == null) {
                            clear = false; // The line is not full
                            continue;
                        }
                    }

                    if (clear) {
                        // Clear the line
                        for (int q = row; q > 0; q--) {
                            matrix[q] = matrix[q-1].clone();
                        }
                        matrix[0] = new Color[] {null,null,null,null,null,null,null,null,null,null};
                        lines++;
                        if (row == HEIGHT-1) possibleMatrixClean = true;
                    }

                    continue;
                }
            }
        }

        // Was it a T-spin?
        thisMove = detectTSpin(lines);

        if (thisMove == Move.NONE && lines > 0) {
            switch (lines) {
                case 1: thisMove = Move.SINGLE; break;
                case 2: thisMove = Move.DOUBLE; break;
                case 3: thisMove = Move.TRIPLE; break;
                case 4: thisMove = Move.TETRIS; notifyHelper.notifyTetris(lastLockdown == Move.TETRIS); break;
            }
        }

        // Update the last move
        lastLockdown = thisMove;

        // Notify observers
        notifyHelper.notifyLockdown();
        if (lines > 0) {
            comboCounter++;
            int score = scoringStrategy.scoreLineClear(lines);
            notifyHelper.notifyLineClear(lines, comboCounter, score);

            // Check for matrix clean
            if (possibleMatrixClean) {
                boolean matrixClean = true;
                for (Color c : matrix[HEIGHT-1]) {
                    if (c == null) { matrixClean = false; continue; }
                }
                if (matrixClean) notifyHelper.notifyMatrixClean();
            }
        } else {
            // No lines cleared, reset comboCounter counter
            comboCounter = -1;
        }

        endOfMino();
    }

    /**
     * Detects if the current piece is in a T-spin position and notifies the
     * observers if it is.
     * @param lines the number of lines cleared in the move
     * @return the type of T-spin or Move.NONE if no T-spin was detected
     */
    private Move detectTSpin(int lines) {
        if (lines > 0 && mino instanceof T) {
            // Check if there are anything on over the brick
            boolean[][] minoArray = mino.getArray(null);
            for (int row = 0; row < minoArray.length; row++) {
                for (int col = 0; col < minoArray[row].length; col++) {
                    if (minoArray[row][col] && matrix[yPos+row-1][xPos+col] != null) {
                        // We have got a T-spin
                        if (lines == 1) {
                            notifyHelper.notifyTspin(lastLockdown == Move.TSPIN);
                            System.out.println("TSPIN");
                            return Move.TSPIN;
                        }
                        if (lines == 2) {
                            notifyHelper.notifyTspinDouble(lastLockdown == Move.TSPINDOUBLE);
                            System.out.println("TSPINDOUBLE");
                            return Move.TSPINDOUBLE;
                        }
                    }
                }
            }
        }
        return Move.NONE;
    }

    /**
     * Takes care of post processing after a mino has been locked down.
     * The purpose of this method is to clean up after each mino has
     * been locked down. The clean up does not depend on the mino just
     * locked down.
     */
    private void endOfMino() {
        // Reset variables
        holdUsed      = false;
        floorKickUsed = false;

        // Start the next mino
        mino = minoPickStrategy.getTetrimino();
        xPos = mino.getInitX();
        yPos = mino.getInitY();
    }

    /**
     * Get a fresh copy of the matrix
     * @return a fresh copy of the matrix
     */
    public Color[][] getMatrix() {
        // Create a copy of the matrix
        Color[][] clone = new Color[HEIGHT][WIDTH];
        for(int h=0; h<HEIGHT; h++) clone[h] = Arrays.copyOf(matrix[h], WIDTH);

        // Add the current mino to the copy
        copyMinoToMatrix(clone);
        return clone;
    }

    /**
     * Kick the mino
     * @param right units to kick right, negative values kicks left
     * @param down units to kick down, negative values kicks up
     */
    public boolean wallkick(int right, int down) {
        if (down == 0 || !floorKickUsed) {
            xPos += right;
            yPos -= down;
        }

        // Notify observers
        if (right != 0) notifyHelper.notifyWallKick();
        if (down != 0) {
            if (floorKickUsed) {
                notifyHelper.notifyFloorKickFail();
                return false;
            }
            else {
                floorKickUsed = true;
                notifyHelper.notifyFloorKick();
            }
        }

        return true;
    }

    /**
     * Move the mino right if possible
     */
    public void moveRight() {
        if (!collision(xPos+1, yPos, mino.getArray(null))) {
            xPos++;
            notifyHelper.notifyMoveRight();
        }
        else notifyHelper.notifyMoveRightFail();
    }

    /**
     * Move the mino left if possible
     */
    public void moveLeft() {
        if (!collision(xPos-1, yPos, mino.getArray(null))) {
            xPos--;
            notifyHelper.notifyMoveLeft();
        }
        else  notifyHelper.notifyMoveLeftFail();
    }

    /**
     * Rotate the mino if possible
     */
    public void rotate() {
        boolean success = rotationStrategy.rotate(this, xPos, yPos, Tetrimino.Rotation.ROTATE90, mino, matrix);
        if (success) notifyHelper.notifyRotate();
        else notifyHelper.notifyRotateFail();
    }

    /**
     * Rotate the mino if possible
     */
    public void ccRotate() {
        boolean success = rotationStrategy.rotate(this, xPos, yPos, Tetrimino.Rotation.ROTATE270, mino, matrix);
        if (success) notifyHelper.notifyCCRotation();
        else notifyHelper.notifyCCRotateFail();
    }

    /**
     * Check if it is possible to move a tetrimino to a given position
     * @param xPos the x coordinate in the matrix
     * @param yPos the y coordinate in the matrix
     * @param mino the mino to check
     * @return true if the move is possible, false otherwise
     */
    private boolean collision(int xPos, int yPos, boolean[][] mino) {
        for (int y=0; y < mino.length; y++) {
            for (int x=0; x < mino[y].length; x++) {
                boolean outOfBounds = xPos + x < 0 || xPos + x >= WIDTH || yPos + y >= HEIGHT;
                if (outOfBounds && mino[y][x]) return true; // check matrix bounds
                if (!outOfBounds && yPos+y >= 0 && matrix[yPos+y][xPos+x]!=null && mino[y][x]) return true; // check for collisions
            }
        }
        return false;
    }

}
