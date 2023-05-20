package org.aquarium.state;

import org.aquarium.gui.BestResults;
import org.aquarium.gui.GameBoard;
import org.aquarium.gui.Menu;
import org.aquarium.utils.music.MusicPlayer;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;


public class CoreService {
    private enum GameState {
        PAUSED, RUNNING, INACTIVE
    }

    private int currentLevelNumber;
    private GameBoard gameBoard;
    private Menu sideMenu;
    private ArrayList<MusicPlayer> musicHandles = new ArrayList<>(10);
    private GameState gameState;

    private static final CoreService instance = new CoreService();

    public static CoreService getInstance() {
        return instance;
    }

    private TimeService timeService;
    private PointsService pointsService;

    private CoreService() {
        currentLevelNumber = 0;
        gameState = GameState.INACTIVE;
    }

    public void connectToGUI(GameBoard gameBoard, Menu sideMenu) {
        this.gameBoard = gameBoard;
        this.sideMenu = sideMenu;

        this.timeService = TimeService.getInstance();
        this.pointsService = PointsService.getInstance();

        ParametersService ps = ParametersService.getInstance();
        int numberOfLevels = ps.getNumberOfLevels();
        for (int i = 0; i < numberOfLevels; i++)
            musicHandles.add(new MusicPlayer(i));
    }

    public void increaseLevelNumber() {
        ParametersService ps = ParametersService.getInstance();
        if (ps.getNumberOfLevels() > currentLevelNumber) {
            currentLevelNumber++;
        }
    }

    public void resetCurrentLevelNumber() {
        currentLevelNumber = 0;
    }

    public void setCurrentLevelNumber(int i) {
        currentLevelNumber = i;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }


    public void endLevel() {
        if (gameBoard != null && timeService != null) {
            musicHandles.get(currentLevelNumber - 1).stop();
            gameBoard.stop();
            gameBoard.repaint();
            timeService.stopTimer();
            JOptionPane.showMessageDialog(null, "level completed");
        }
    }

    public void goToNextLevel() {
        if (timeService != null && sideMenu != null && gameBoard != null) {
            gameState = GameState.RUNNING;
            increaseLevelNumber();
            timeService.resetAndStartTimer();
            musicHandles.get(currentLevelNumber - 1).play();
            sideMenu.setLevel();
            gameBoard.setGameObjectsSpeed(currentLevelNumber);
            gameBoard.setGameObjectsNumber(20);
            gameBoard.start();
        }
    }

    public void togglePause() {
        if (timeService != null && gameBoard != null) {
            if (gameState != GameState.PAUSED) {
                gameBoard.animationPause();
                musicHandles.get(currentLevelNumber - 1).stop();
                gameState = GameState.PAUSED;
            } else {
                gameBoard.animationPause();
                musicHandles.get(currentLevelNumber - 1).play();
                gameState = GameState.RUNNING;
            }
        }
    }

    public void endGame() {
        if (pointsService != null && gameBoard != null) {
            if (gameState == GameState.RUNNING) {
                endLevel();
                BestResults br = BestResults.getInstance();
                GregorianCalendar data = new GregorianCalendar();
                br.save(pointsService.getPoints(), data.getTime().toString());
                br.showScores();
                gameState = GameState.INACTIVE;
                gameBoard.stop();
                gameBoard.repaint();
                pointsService.resetPoints();
                resetCurrentLevelNumber();
            }
        }
    }

    public boolean isGamePaused() {
        return gameState == GameState.PAUSED;
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void addOneGameObjectToGameBoard() {
        if (gameBoard != null) {
            gameBoard.addOneGameObject();
        }
    }

    public void addManyGameObjectsToGameBoard(int numberOfGameObjectsToAdd) {
        if (gameBoard != null) {
            gameBoard.addManyGameObjects(numberOfGameObjectsToAdd);
        }
    }
}