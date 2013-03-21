package Core;

import Core.gui.MatrixUI;
import Core.queue.RandomMinoPick;
import Core.rotation.TGMRotation;
import Core.scoring.StandardScoring;

public class Driver {
    public static void main(String[] args) {
        final Engine game = new Engine(new TGMRotation(), new StandardScoring(), new RandomMinoPick());
        new MatrixUI(game);
    }

}
