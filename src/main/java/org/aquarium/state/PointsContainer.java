package org.aquarium.state;

public class PointsContainer {
    private static final PointsContainer instance = new PointsContainer();
    public static PointsContainer getInstance() {return instance;}

    private int pointsEarned;

    private PointsContainer() {
        pointsEarned=0;
    }

    public void addPoint() {
        pointsEarned++;
    }

    public void resetPoints() {
        pointsEarned=0;
    }

    public int getGamePoints(){
        return pointsEarned;
    }
}
