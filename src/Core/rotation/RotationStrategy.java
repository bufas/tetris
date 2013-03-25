package Core.rotation;

import Core.Engine;
import Core.Matrix;
import Core.tetriminos.Tetrimino;

public interface RotationStrategy {

    public boolean rotate(Engine game, int xPos, int yPos, Tetrimino.Rotation rotation, Tetrimino mino, Matrix matrix);

}
