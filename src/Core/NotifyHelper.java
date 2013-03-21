package Core;

import java.util.ArrayList;
import java.util.List;

public class NotifyHelper {

    private List<GameObserver> observers;

    public NotifyHelper() {
        observers = new ArrayList<GameObserver>();
    }

    /**
     * Register an observer
     * @param obs the observer to be registered
     */
    public void registerObserver(GameObserver obs) { observers.add(obs); }

    /**
     * Remove an observer
     * @param obs the observer to be removed
     */
    public void removeObserver(GameObserver obs) { observers.remove(obs); }

    // For the UI
    public void notifyMoveLeft()   { for (GameObserver o : observers) o.notifyMoveLeft(); }
    public void notifyMoveRight()  { for (GameObserver o : observers) o.notifyMoveRight(); }
    public void notifyRotate()     { for (GameObserver o : observers) o.notifyRotate(); }
    public void notifyCCRotation() { for (GameObserver o : observers) o.notifyCCRotation(); }

    // Failures - For the UI
    public void notifyMoveRightFail()     { for (GameObserver o : observers) o.notifyMoveRightFail(); }
    public void notifyMoveLeftFail()      { for (GameObserver o : observers) o.notifyMoveLeftFail(); }
    public void notifyRotateFail()        { for (GameObserver o : observers) o.notifyRotateFail(); }
    public void notifyCCRotateFail()      { for (GameObserver o : observers) o.notifyCCRotateFail(); }
    public void notifyHoldTetriminoFail() { for (GameObserver o : observers) o.notifyHoldTetriminoFail(); }
    public void notifyFloorKickFail()     { for (GameObserver o : observers) o.notifyFloorKickFail(); }

    // Mostly for testing
    public void notifyWallKick()           { for (GameObserver o : observers) o.notifyWallKick(); }
    public void notifyFloorKick()          { for (GameObserver o : observers) o.notifyFloorKick(); }
    public void notifyHoldTetrimino()      { for (GameObserver o : observers) o.notifyHoldTetrimino(); }
    public void notifyHardDrop(int length) { for (GameObserver o : observers) o.notifyHardDrop(length); }
    public void notifySoftDrop()           { for (GameObserver o : observers) o.notifySoftDrop(); }
    public void notifyLockdown()           { for (GameObserver o : observers) o.notifyLockdown(); }

    // Special moves
    public void notifyTetris(boolean back2back)      { for (GameObserver o : observers) o.notifyTetris(back2back); }
    public void notifyTspin(boolean back2back)       { for (GameObserver o : observers) o.notifyTspin(back2back); }
    public void notifyTspinDouble(boolean back2back) { for (GameObserver o : observers) o.notifyTspinDouble(back2back); }
    public void notifyMatrixClean()                  { for (GameObserver o : observers) o.notifyMatrixClean(); }

    // Major game events
    public void notifyGameOver() { for (GameObserver o : observers) o.notifyGameOver(); }
    public void notifyLineClear(int lines, int combo, int points) {
        for (GameObserver o : observers) o.notifyLineClear(lines, combo, points);
    }

}
