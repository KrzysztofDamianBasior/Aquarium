package org.aquarium.state;

import org.aquarium.exceptions.ExceptionHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class GameParameters {
    String parametricFilePathname = "./src/main/resources/configuration/parametricFile.txt";
    String scenarioFilePathname = "./src/main/resources/configuration/scenario.txt";

    private static final GameParameters instance = new GameParameters();

    public static GameParameters getInstance() {
        return instance;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public String getParametricFilePathname() {
        return parametricFilePathname;
    }

    public String getScenarioFilePathname() {
        return scenarioFilePathname;
    }

    public String getLevelMusicFilePathname(int level) {
        return levelsMusicFilesPathnames[level];
    }

    public String getLevelBackgroundFilePathname(int level) {
        return levelsBackgroundFilesPathnames[level];
    }

    public int getLevelDurationTimeValue(int level) {
        return levelsDurationTimeValues[level];
    }

    public String getGameName() {
        return gameName;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public int getNumberOfDifficultyLevels() {
        return numberOfDifficultyLevels;
    }

    public int getAmountOfChangeInDifficulty() {
        return amountOfChangeInDifficulty;
    }

    public int getInitialBoardWidth() {
        return initialBoardWidth;
    }

    public int getInitialBoardHeight() {
        return initialBoardHeight;
    }

    public double getInitialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth() {
        return initialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth;
    }

    public String getMenuBackground() {
        return menuBackground;
    }

    public String getMenuBackgroundFilePathname() {
        return menuBackgroundFilePathname;
    }

    public int getMenuBackgroundColor(int i) {
        return menuBackgroundColor[i];
    }

    public String getGameObjects() {
        return gameObjects;
    }

    private int numberOfLevels = 0;
    private String[] levelsMusicFilesPathnames = {};
    private String[] levelsBackgroundFilesPathnames = {};
    private int[] levelsDurationTimeValues = {};
    private String gameName = "";
    private String levelName = "";
    private String extensionName = "";
    private int numberOfDifficultyLevels = 0;
    private int amountOfChangeInDifficulty = 0;
    private int initialBoardWidth = 0;
    private int initialBoardHeight = 0;
    private double initialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth = 0.0;
    private String menuBackground = "";
    private String menuBackgroundFilePathname = "";
    private String gameObjects = "";
    private int[] menuBackgroundColor = {};

    private GameParameters() {

        Properties props = new Properties();
        try (Reader r = new BufferedReader(new FileReader(parametricFilePathname))) {
            props.load(r);
        } catch (FileNotFoundException fnfe) {
            ExceptionHandler.writeMessagesAndExit("Parametric file not found",
                    parametricFilePathname, fnfe);
        } catch (IOException ioe) {
            ExceptionHandler.writeMessagesAndExit("There was an error reading the parametric file",
                    parametricFilePathname, ioe);
        }
        gameName = props.getProperty("gameName");
        numberOfLevels = Integer.parseInt(props.getProperty("numberOfLevels"));
        levelName = props.getProperty("levelName");
        extensionName = props.getProperty("extensionName");
        numberOfDifficultyLevels = Integer.parseInt(props.getProperty("numberOfDifficultyLevels"));
        amountOfChangeInDifficulty = Integer.parseInt(props.getProperty("amountOfChangeInDifficulty"));
        initialBoardWidth = Integer.parseInt(props.getProperty("initialBoardWidth"));
        initialBoardHeight = Integer.parseInt(props.getProperty("initialBoardHeight"));
        initialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth = Double.parseDouble(props.getProperty("initialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth"));
        menuBackground = props.getProperty("menuBackground");
        numberOfLevels = Integer.parseInt(props.getProperty("numberOfLevels"));

        if (menuBackground.equals("plain")) {
            String[] temp = (props.getProperty("menuBackgroundColor")).split(" ");
            menuBackgroundColor = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                menuBackgroundColor[i] = Integer.parseInt(temp[i]);
            }
        }
        if (menuBackground.equals("graphicFile")) {
            menuBackgroundFilePathname = props.getProperty("menuBackgroundFilePathname");
        }
        gameObjects = props.getProperty("gameObjects");

        Properties props2 = new Properties();
        try (Reader r = new BufferedReader(new FileReader(scenarioFilePathname))) {
            props2.load(r);
        } catch (FileNotFoundException fnfe) {
            ExceptionHandler.writeMessagesAndExit("Parametric file not found",
                    scenarioFilePathname, fnfe);
        } catch (IOException ioe) {
            ExceptionHandler.writeMessagesAndExit("There was an error reading the parametric file",
                    scenarioFilePathname, ioe);
        }
        int numberOfScenarioLevels = Integer.parseInt(props2.getProperty("numberOfLevels"));

        if (numberOfScenarioLevels != numberOfLevels) {
            System.exit(0);
        }

        levelsDurationTimeValues = new int[numberOfScenarioLevels];
        String[] tempLevelsTimeValues = (props2.getProperty("levelsDurationTimeValues")).split(" ");
        for (int i = 0; i < numberOfScenarioLevels; i++) {
            levelsDurationTimeValues[i] = Integer.parseInt(tempLevelsTimeValues[i]);
        }


        levelsBackgroundFilesPathnames = new String[numberOfScenarioLevels];
        String[] tempLevelsBackgroundFiles = (props2.getProperty("levelsBackgroundFilesPathnames")).split(" ");
        for (int i = 0; i < numberOfScenarioLevels; i++) {
            levelsBackgroundFilesPathnames[i] = tempLevelsBackgroundFiles[i];
        }


        levelsMusicFilesPathnames = new String[numberOfScenarioLevels];
        String[] tempLevelsMusicFiles = (props2.getProperty("levelsMusicFilesPathnames")).split(" ");
        for (int i = 0; i < numberOfScenarioLevels; i++) {
            levelsMusicFilesPathnames[i] = tempLevelsMusicFiles[i];
        }
    }

    public static void main(String[] args) {
        System.out.println("ok");
        GameParameters kl = GameParameters.getInstance();
        System.out.print(
                "parametric file pathname: " + kl.getParametricFilePathname() + "\n" +
                        "scenario file pathname: " + kl.getScenarioFilePathname() + "\n" +
                        "\n\n" +
                        "game name: " + kl.getGameName() + "\n" +
                        "number of levels: " + kl.getNumberOfLevels() + "\n" +
                        "number of difficulty levels: " + kl.getNumberOfDifficultyLevels() + "\n" +
                        "level name: " + kl.getLevelName() + "\n" +
                        "initial board height: " + kl.getInitialBoardHeight() + "\n" +
                        "initial board width: " + kl.getInitialBoardWidth() + "\n" +
                        "extension name: " + kl.getExtensionName() + "\n" +
                        "amount of change in difficulty: " + kl.getAmountOfChangeInDifficulty() + "\n" +
                        "initial width of the game object as a percentage of board initial width: " + kl.getInitialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth() + "\n" +
                        "game objects: " + kl.getGameObjects() + "\n" +
                        "menu background: "
        );
        if (kl.getMenuBackground().equals("plain"))
            System.out.print(kl.getMenuBackgroundColor(0) + " " + kl.getMenuBackgroundColor(1) + " " + kl.getMenuBackgroundColor(2));
        if (kl.getMenuBackground().equals("graphicFile"))
            System.out.print(kl.getMenuBackgroundFilePathname());

        System.out.println("\nlevels duration times: ");
        for (int i = 0; i < kl.numberOfLevels; i++)
            System.out.print(kl.getLevelDurationTimeValue(i) + " ");

        System.out.println("\nlevels backgrounds files: ");
        for (int i = 0; i < kl.numberOfLevels; i++)
            System.out.print(kl.getLevelBackgroundFilePathname(i) + " ");

        System.out.println("\nlevels music files: ");
        for (int i = 0; i < kl.numberOfLevels; i++)
            System.out.print(kl.getLevelMusicFilePathname(i) + " ");
    }
}
