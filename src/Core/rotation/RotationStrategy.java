package Core.rotation;

import Core.Engine;
import Core.tetriminos.Tetrimino;

import java.awt.*;

public interface RotationStrategy {

    public boolean rotate(Engine game, int xPos, int yPos, Tetrimino.Rotation rotation, Tetrimino mino, Color[][] matrix);

}
