package Core.queue;

import Core.tetriminos.*;

public class RandomMinoPick implements MinoPickStrategy {

    @Override
    public Tetrimino getTetrimino() {
        switch ((int) (Math.random() * 7)) {
            case 0: return new T();
            case 1: return new L();
            case 2: return new J();
            case 3: return new S();
            case 4: return new Z();
            case 5: return new O();
            case 6: return new I();
            default: return null;
        }
    }
}
