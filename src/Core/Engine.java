package Core;

import Core.queue.MinoPickStrategy;
import Core.rotation.RotationStrategy;
import Core.scoring.ScoringStrategy;
import Core.tetriminos.*;

public class Engine {

    public enum Move {NONE, SINGLE, DOUBLE, TRIPLE, TETRIS, TSPIN, TSPINDOUBLE};

    private Matrix matrix;
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
        matrix = new MatrixImpl();

        mino = minoPickStrategy.getTetrimino();
        xPos = mino.getInitCol();
        yPos = mino.getInitRow();
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
        if (!matrix.isCollision(yPos-1, xPos, mino)) {
            yPos--;
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
        while(!matrix.isCollision(yPos-length-1, xPos, mino)) length++;

        yPos -= length;
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
            xPos = mino.getInitCol();
            yPos = mino.getInitRow();

            // Notify observers
            notifyHelper.notifyHoldTetrimino();
        }
        else notifyHelper.notifyHoldTetriminoFail();
    }

    /**
     * Lock a tetrimino to the matrix and spawn the next one
     */
    private void lockPiece() {
        matrix.lockMino(mino, yPos, xPos);

        Move thisMove;
        boolean possibleMatrixClean = false;

        // Clear lines TODO optimize this to check only possible lines
        int lines = 0;
        for (int row = yPos + 3; row >= Math.max(yPos, 1); row--) {
            if(matrix.isFull(row)) {
                matrix.clearRow(row);
                lines++;
                if (row == 1) possibleMatrixClean = true;
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
                if (matrix.isEmpty()) notifyHelper.notifyMatrixClean();
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
            for (Offset offset : mino.getArray()) {
                if (!matrix.slotIsEmpty(yPos + offset.getY() + 1, xPos + offset.getX())) {
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
        xPos = mino.getInitCol();
        yPos = mino.getInitRow();
    }

    /**
     * Find out what is the status of a slot in the matrix
     * @return the status of the queried slot
     */
    public Matrix.MatrixSlot getSlot(int row, int col) {
        int ghostYPos = matrix.getGhostYCoordinate(yPos, xPos, mino);
        if (matrix.slotIsEmpty(row, col)) {
            // Check if the current mino occupies the space
            for (Offset offset : mino.getArray()) {
                if (yPos + offset.getY() == row && xPos + offset.getX() == col) {
                    if      (mino instanceof I) return Matrix.MatrixSlot.I_OPEN;
                    else if (mino instanceof J) return Matrix.MatrixSlot.J_OPEN;
                    else if (mino instanceof L) return Matrix.MatrixSlot.L_OPEN;
                    else if (mino instanceof O) return Matrix.MatrixSlot.O_OPEN;
                    else if (mino instanceof S) return Matrix.MatrixSlot.S_OPEN;
                    else if (mino instanceof Z) return Matrix.MatrixSlot.Z_OPEN;
                    else if (mino instanceof T) return Matrix.MatrixSlot.T_OPEN;
                }
                if (ghostYPos + offset.getY() == row && xPos + offset.getX() == col) return Matrix.MatrixSlot.GHOST;
            }
            return Matrix.MatrixSlot.EMPTY;
        }
        return matrix.getSlot(row, col);
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
        if (!matrix.isCollision(yPos, xPos+1, mino)) {
            xPos++;
            notifyHelper.notifyMoveRight();
        }
        else notifyHelper.notifyMoveRightFail();
    }

    /**
     * Move the mino left if possible
     */
    public void moveLeft() {
        if (!matrix.isCollision(yPos, xPos-1, mino)) {
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

}
