package org.aquarium.state;

public class PointsService {
    private static final PointsService instance = new PointsService();

    public static PointsService getInstance() {
        return instance;
    }

    private int pointsEarned;

    private PointsService() {
        pointsEarned = 0;
    }

    public void addPoint() {
        pointsEarned++;
    }

    public void resetPoints() {
        pointsEarned = 0;
    }

    public int getPoints() {
        return pointsEarned;
    }
}
