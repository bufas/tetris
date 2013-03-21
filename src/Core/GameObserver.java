package Core;

public interface GameObserver {

    // For the UI
    public void notifyMoveLeft();
    public void notifyMoveRight();
    public void notifyRotate();
    public void notifyCCRotation();

    // Failures - For the UI
    public void notifyMoveRightFail();
    public void notifyMoveLeftFail();
    public void notifyRotateFail();
    public void notifyCCRotateFail();
    public void notifyHoldTetriminoFail();
    public void notifyFloorKickFail();

    // Mostly for testing
    public void notifyWallKick();
    public void notifyFloorKick();
    public void notifyHoldTetrimino();
    public void notifyHardDrop(int length);
    public void notifySoftDrop();
    public void notifyLockdown();

    // Special moves
    public void notifyTetris(boolean back2back);
    public void notifyTspin(boolean back2back);
    public void notifyTspinDouble(boolean back2back);
    public void notifyMatrixClean();

    // Major game events
    public void notifyGameOver();
    public void notifyLineClear(int lines, int combo, int points);

}
