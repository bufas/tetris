package Core.scoring;

public class StandardScoring implements ScoringStrategy {

    @Override
    public int scoreSoftDrop(int units) {
        return 1;
    }

    @Override
    public int scoreHardDrop(int units) {
        return 2;
    }

    @Override
    public int scoreLineClear(int lines) {
        switch (lines) {
            case 1:  return 100;
            case 2:  return 300;
            case 3:  return 500;
            case 4:  return 800;
            default: return 0;
        }
    }
}
