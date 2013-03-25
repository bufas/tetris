package Core.rotation;

import Core.Engine;
import Core.Matrix;
import Core.tetriminos.I;
import Core.tetriminos.T;
import Core.tetriminos.Tetrimino;

public class TGMRotation implements RotationStrategy {

    @Override
    // TODO check special cases http://tetris.wikia.com/wiki/TGM_Rotation
    public boolean rotate(Engine game, int xPos, int yPos, Tetrimino.Rotation rotation, Tetrimino mino, Matrix matrix) {

        if (matrix.isCollision(yPos, xPos, mino, rotation)) {
            // Try to wall kick right
            if (!matrix.isCollision(yPos, xPos+1, mino, rotation)) {
                game.wallkick(1, 0);
                mino.rotate(rotation);
                return true;
            }
            // try to wall kick left
            else if(!matrix.isCollision(yPos, xPos-1, mino, rotation)) {
                game.wallkick(-1, 0);
                mino.rotate(rotation);
                return true;
            }
            // Try to wall kick right twice if I-mino
            else if (mino instanceof I && !matrix.isCollision(yPos, xPos+2, mino, rotation)) {
                game.wallkick(2, 0);
                mino.rotate(rotation);
                return true;
            }
            // Try to floor kick T and I minos 1 slot
            else if ((mino instanceof T || mino instanceof I) && !matrix.isCollision(yPos+1, xPos, mino, rotation)) {
                boolean possible = game.wallkick(0, 1);
                if (possible) mino.rotate(rotation);
                return possible;
            }
            // Try to floor kick I minos 2 slots
            else if (mino instanceof I && !matrix.isCollision(yPos+2, xPos, mino, rotation)) {
                boolean possible = game.wallkick(0, 2);
                if (possible) mino.rotate(rotation);
                return possible;
            }
            return false;
        }

        // Nothing is in the way, just rotate the mino
        mino.rotate(rotation);
        return true;
    }

}
