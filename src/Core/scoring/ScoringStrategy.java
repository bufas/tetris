package Core.scoring;

public interface ScoringStrategy {

    public int scoreSoftDrop(int units);

    public int scoreHardDrop(int units);

    public int scoreLineClear(int lines);

}
