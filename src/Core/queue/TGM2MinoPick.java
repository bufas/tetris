package Core.queue;

import Core.tetriminos.*;

/**
 * This strategy is similar to TGM1 except for the initial state of the queue and
 * the number of retries to find a piece that is not in the queue is increased
 * from 4 to 6.
 */
public class TGM2MinoPick implements MinoPickStrategy {

    private int[] memory;
    private int head = 0;
    private boolean first = true;

    public TGM2MinoPick() {
        memory = new int[4];
        memory[0] = 5; // Z mino
        memory[1] = 4; // S mino
        memory[2] = 5; // Z mino
        memory[3] = 4; // S mino
    }

    @Override
    public Tetrimino getTetrimino() {
        int random = 0;
        for (int tries = 0; tries < 6; tries++) {
            random = (int) (Math.random() * (first ? 4 : 7)); // Don't pick S, Z or O as first mino
            if (memoryContains(random)) continue;
            break;
        }

        first = false;
        memory[head] = random;
        head = (head + 1) % 4;

        switch (random) {
            case 0:  return new T();
            case 1:  return new L();
            case 2:  return new J();
            case 3:  return new I();
            case 4:  return new S();
            case 5:  return new Z();
            default: return new O();
        }
    }

    private boolean memoryContains(int n) {
        for (int number : memory) if (n == number) return true;
        return false;
    }
}
