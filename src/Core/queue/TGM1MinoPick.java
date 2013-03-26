package Core.queue;

import Core.tetriminos.*;

public class TGM1MinoPick implements MinoPickStrategy {

    private int[] memory;
    private int head = 0;
    private boolean first = true;

    public TGM1MinoPick() {
        memory = new int[4];
        memory[0] = 5; // Z mino
        memory[1] = 5; // Z mino
        memory[2] = 5; // Z mino
        memory[3] = 5; // Z mino
    }

    @Override
    public Tetrimino getTetrimino() {
        int random = 0;
        for (int tries = 0; tries < 4; tries++) {
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
