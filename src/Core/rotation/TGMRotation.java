package Core.rotation;

import Core.Engine;
import Core.tetriminos.I;
import Core.tetriminos.T;
import Core.tetriminos.Tetrimino;

import java.awt.*;

public class TGMRotation implements RotationStrategy {

    @Override
    // TODO check special cases http://tetris.wikia.com/wiki/TGM_Rotation
    public boolean rotate(Engine game, int xPos, int yPos, Tetrimino.Rotation rotation, Tetrimino mino, Color[][] matrix) {
        boolean[][] rotatedArray = mino.getArray(rotation);

        if (collision(xPos, yPos, mino.getArray(rotation), matrix)) {
            // Try to wall kick right
            if (!collision(xPos+1, yPos, rotatedArray, matrix)) {
                game.wallkick(1, 0);
                mino.rotate(rotation);
                return true;
            }
            // try to wall kick left
            else if(!collision(xPos-1, yPos, rotatedArray, matrix)) {
                game.wallkick(-1, 0);
                mino.rotate(rotation);
                return true;
            }
            // Try to wall kick right twice if I-mino
            else if (mino instanceof I && !collision(xPos+2, yPos, rotatedArray, matrix)) {
                game.wallkick(2, 0);
                mino.rotate(rotation);
                return true;
            }
            // Try to floor kick T and I minos 1 slot
            else if ((mino instanceof T || mino instanceof I) && !collision(xPos, yPos-1, rotatedArray, matrix)) {
                boolean possible = game.wallkick(0, 1);
                if (possible) mino.rotate(rotation);
                return possible;
            }
            // Try to floor kick I minos 2 slots
            else if (mino instanceof I && !collision(xPos, yPos-2, rotatedArray, matrix)) {
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

    private boolean collision(int xPos, int yPos, boolean[][] mino, Color[][] matrix) {
        for (int y=0; y < mino.length; y++) {
            for (int x=0; x < mino[y].length; x++) {
                boolean outOfBounds = xPos + x < 0 || xPos + x >= Engine.WIDTH || yPos + y >= Engine.HEIGHT;
                if (outOfBounds && mino[y][x]) return true; // check matrix bounds
                if (!outOfBounds && yPos+y >= 0 && matrix[yPos+y][xPos+x]!=null && mino[y][x]) return true; // check for collisions
            }
        }

        return false;
    }

}
