package org.aquarium.state;

public class LevelInfo {
    private static final LevelInfo instance = new LevelInfo();
    public static LevelInfo getInstance() {return instance;}

    private int currentLevelNumber;

    private LevelInfo() {
        currentLevelNumber=0;
    }

    public void increaseLevelNumber() {
        currentLevelNumber++;
    }

    public void resetLevelNumber() {
        currentLevelNumber=0;
    }

    public void setLevelNumber(int i) {
        currentLevelNumber=i;
    }

    public int getLevelNumber() {
        return currentLevelNumber;
    }
}
